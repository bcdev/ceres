package com.bc.ceres.core.runtime.internal;

import junit.framework.TestCase;

public class DependencyImplTest extends TestCase {

    public void testModuleEqual() {
        assertEquals(new ModuleDependencyMock("Module-a"),
                     new ModuleDependencyMock("Module-a"));
        assertEquals(new ModuleDependencyMock("Module-a", "1.6.4"),
                     new ModuleDependencyMock("Module-a", "1.6.4"));
        assertEquals(new ModuleDependencyMock("Module-a", "3.4.1-SNAPSHOT"),
                     new ModuleDependencyMock("Module-a", "3.4.1-SNAPSHOT"));
        assertEquals(new ModuleDependencyMock("module-b", "1.0"),
                     new ModuleDependencyMock("module-b", "1.0.0"));
        assertEquals(new ModuleDependencyMock("module-b", "1.0.0"),
                     new ModuleDependencyMock("module-b", "1.0"));

        assertEquals(new ModuleDependencyMock("Module-a", "2.0"),
                     new ModuleDependencyMock("Module-a"));
        assertEquals(new ModuleDependencyMock("Module-a"),
                     new ModuleDependencyMock("Module-a", "2.0"));
    }

    public void testModuleNotEqual() {
        assertNotEquals(new ModuleDependencyMock("Module-a"),
                        new ModuleDependencyMock("Module-A"));
        assertNotEquals(new ModuleDependencyMock("Module-A"),
                        new ModuleDependencyMock("Module-a"));
    }

    public void testLibEqual() {
        assertEquals(new LibDependencyMock("Lib-a"),
                     new LibDependencyMock("Lib-a"));
        assertEquals(new LibDependencyMock("Lib-a", "1.6.4"),
                     new LibDependencyMock("Lib-a", "1.6.4"));
        assertEquals(new LibDependencyMock("Lib-a", "3.4.1-SNAPSHOT"),
                     new LibDependencyMock("Lib-a", "3.4.1-SNAPSHOT"));
        assertEquals(new LibDependencyMock("lib-b", "1.0"),
                     new LibDependencyMock("lib-b", "1.0.0"));
        assertEquals(new LibDependencyMock("lib-b", "1.0.0"),
                     new LibDependencyMock("lib-b", "1.0"));

        assertEquals(new LibDependencyMock("Lib-a", "2.0"),
                     new LibDependencyMock("Lib-a"));
        assertEquals(new LibDependencyMock("Lib-a"),
                     new LibDependencyMock("Lib-a", "2.0"));
    }

    public void testLibNotEqual() {
        assertNotEquals(new LibDependencyMock("Lib-a"),
                        new LibDependencyMock("Lib-A"));
        assertNotEquals(new LibDependencyMock("Lib-A"),
                        new LibDependencyMock("Lib-a"));
    }

    public void testLibHashCode() {
        LibDependencyMock lib1 = new LibDependencyMock("Lib-a");
        LibDependencyMock lib2 = new LibDependencyMock("Lib-a");
        assertEquals(lib1.hashCode(), lib1.hashCode());
        assertEquals(lib1.hashCode(), lib2.hashCode());

        LibDependencyMock lib3 = new LibDependencyMock("Lib-b");
        assertNotEquals(lib1.hashCode(), lib3.hashCode());
    }

    public void testModuleHashCode() {
        ModuleDependencyMock mod1 = new ModuleDependencyMock("Module-a");
        ModuleDependencyMock mod2 = new ModuleDependencyMock("Module-a");
        assertEquals(mod1.hashCode(), mod1.hashCode());
        assertEquals(mod1.hashCode(), mod2.hashCode());

        ModuleDependencyMock mod3 = new ModuleDependencyMock("Module-b");
        assertNotEquals(mod1.hashCode(), mod3.hashCode());
    }

    private static void assertNotEquals(Object expected, Object actual) {
        if (expected.equals(actual)) {
            fail("expected not equals");
        }
    }

    private class LibDependencyMock extends DependencyImpl {

        String libName;
        String version;

        public LibDependencyMock(String name) {
            this(name, null);
        }

        public LibDependencyMock(String name, String version) {
            this.libName = name;
            this.version = version;
        }

        @Override
        public String getLibName() {
            return libName;
        }

        @Override
        public String getVersion() {
            return version;
        }
    }

    private class ModuleDependencyMock extends DependencyImpl {

        String moduleName;
        String version;

        public ModuleDependencyMock(String name) {
            this(name, null);
        }

        public ModuleDependencyMock(String name, String version) {
            this.moduleName = name;
            this.version = version;
        }

        @Override
        public String getModuleSymbolicName() {
            return moduleName;
        }

        @Override
        public String getVersion() {
            return version;
        }
    }
}
