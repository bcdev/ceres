package com.bc.ceres.launcher;

import com.bc.ceres.core.runtime.RuntimeConfig;
import com.bc.ceres.core.runtime.RuntimeConfigException;
import com.bc.ceres.core.runtime.internal.DefaultRuntimeConfig;
import com.bc.ceres.launcher.internal.BootstrapClasspathFactory;
import com.bc.ceres.launcher.internal.BruteForceClasspathFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * A launcher for applications based on the Ceres runtime.
 *
 * @see RuntimeConfig
 * @see ClasspathFactory
 */
public final class Launcher {
    private RuntimeConfig runtimeConfig;
    private ClasspathFactory classpathFactory;

    /**
     * Launches the application with a default {@link RuntimeConfig} and a {@link ClasspathFactory}
     * based on the <code>${ceres.context}.mainClass</code> property. If the main class is
     * {@code "com.bc.ceres.core.runtime.RuntimeLauncher"}, then a minimal classpath which at least includes the
     * <code>ceres-core</code> library is used. Otherwise all directories, JARs and ZIPs found in
     * the home directory will be added to the classpath.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        try {
            Launcher launcher = createDefaultLauncher();
            launcher.launch(args);
        } catch (Throwable e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    /**
     * Creates a default launcher.
     *
     * @return a default launcher.
     * @throws RuntimeConfigException if the default configuration is invalid
     */
    public static Launcher createDefaultLauncher() throws RuntimeConfigException {
        RuntimeConfig runtimeConfig = new DefaultRuntimeConfig();
        ClasspathFactory classpathFactory;
        if (runtimeConfig.isUsingModuleRuntime()) {
            classpathFactory = new BootstrapClasspathFactory(runtimeConfig);
        } else {
            classpathFactory = new BruteForceClasspathFactory(runtimeConfig);
        }
        return new Launcher(runtimeConfig, classpathFactory);
    }

    /**
     * Constructs a new launcher.
     *
     * @param runtimeConfig    the runtime configuration
     * @param classpathFactory the classpath factory
     */
    public Launcher(RuntimeConfig runtimeConfig, ClasspathFactory classpathFactory) {

        this.runtimeConfig = runtimeConfig;
        this.classpathFactory = classpathFactory;

        trace("Configuration type: " + this.runtimeConfig.getClass().getName());
        trace("Classpath type:     " + this.classpathFactory.getClass().getName());
    }

    public RuntimeConfig getRuntimeConfig() {
        return runtimeConfig;
    }

    public ClasspathFactory getClasspathFactory() {
        return classpathFactory;
    }

    public ClassLoader createClassLoader() throws RuntimeConfigException {
        ClassLoader classLoader = getClass().getClassLoader();

        URL[] defaultClasspath = createDefaultClasspath();
        if (defaultClasspath.length > 0) {
            classLoader = new URLClassLoader(defaultClasspath, classLoader);
        }

        URL[] mainClasspath = createMainClasspath();
        if (defaultClasspath.length > 0) {
            classLoader = new URLClassLoader(mainClasspath, classLoader);
        }

        traceClassLoader("classLoader", classLoader);
        return classLoader;
    }

    URL[] createDefaultClasspath() throws RuntimeConfigException {
        return classpathFactory.createClasspath();
    }

    URL[] createMainClasspath() {
        ArrayList<URL> urlList = new ArrayList<URL>(16);
        String paths = runtimeConfig.getMainClassPath();
        if (paths != null) {
            StringTokenizer st = new StringTokenizer(paths, File.pathSeparator);
            while (st.hasMoreTokens()) {
                String path = st.nextToken().trim();
                try {
                    URL url = new File(path).toURI().toURL();
                    urlList.add(url);
                } catch (MalformedURLException e) {
                    trace(MessageFormat.format("Invalid classpath entry: {0}", path));
                }
            }
        }
        return urlList.toArray(new URL[urlList.size()]);
    }

    public void launch(String[] args) throws Exception {
        ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader classLoader = createClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            Class<?> mainClass = classLoader.loadClass(runtimeConfig.getMainClassName());
            if (runtimeConfig.isUsingModuleRuntime()) {
                Method mainMethod = mainClass.getMethod("launch", new Class[]{RuntimeConfig.class, ClassLoader.class, String[].class});
                mainMethod.invoke(null, new Object[]{runtimeConfig, classLoader, args});
            } else {
                Method mainMethod = mainClass.getMethod("main", new Class[]{String[].class});
                mainMethod.invoke(null, new Object[]{args});
            }
        } finally {
            Thread.currentThread().setContextClassLoader(oldContextClassLoader);
        }
    }

    private void trace(String msg) {
        if (runtimeConfig.isDebug()) {
            System.out.println(String.format("[DEBUG] ceres-launcher: %s", msg));
        }
    }

    // do not delete, useful for debugging

    private void traceClassLoader(String name, ClassLoader classLoader) {
        System.out.println("=============================================================================");
        System.out.println(name + ".class = " + classLoader.getClass());
        if (classLoader instanceof URLClassLoader) {
            URL[] classpath = ((URLClassLoader) classLoader).getURLs();
            for (int i = 0; i < classpath.length; i++) {
                System.out.println(name + ".url[" + i + "] = " + classpath[i]);
            }
        }
        if (classLoader.getParent() != null) {
            traceClassLoader(name + ".parent", classLoader.getParent());
        } else {
            System.out.println(name + ".parent = null");
        }
    }

}