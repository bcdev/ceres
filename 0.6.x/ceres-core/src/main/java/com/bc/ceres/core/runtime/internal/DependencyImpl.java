package com.bc.ceres.core.runtime.internal;

import com.bc.ceres.core.runtime.Dependency;
import com.bc.ceres.core.runtime.Module;

public class DependencyImpl implements Dependency {

    public static final Dependency[] EMPTY_ARRAY = new Dependency[0];

    private transient Module declaringModule;

    private String moduleSymbolicName; // IDE warning "private field never assigned" is ok
    private String libName; // IDE warning "private field never assigned" is ok
    private String version; // IDE warning "private field never assigned" is ok
    private boolean optional; // IDE warning "private field never assigned" is ok


    public Module getDeclaringModule() {
        return declaringModule;
    }

    public String getLibName() {
        return libName;
    }

    public String getModuleSymbolicName() {
        return moduleSymbolicName;
    }

    public String getVersion() {
        return version;
    }

    public boolean isOptional() {
        return optional;
    }

    void setDeclaringModule(Module declaringModule) {
        this.declaringModule = declaringModule;
    }

    @Override
    public int hashCode() {
        if (getLibName() != null) {
            return getLibName().hashCode();
        } else if (getModuleSymbolicName() != null) {
            return getModuleSymbolicName().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (obj instanceof Dependency) {
            DependencyImpl other = (DependencyImpl) obj;
            if (getLibName() != null) {
                return getLibName().equals(other.getLibName());
            } else if (getModuleSymbolicName() != null) {
                return getModuleSymbolicName().equals(other.getModuleSymbolicName());
            }
        }
        return false;
    }
}
