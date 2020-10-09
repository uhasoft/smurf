package com.uhasoft.smurf.skeleton.request;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ProjectRo {

    private String group;
    private String basePackage;
    private String module;
    private String version;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
