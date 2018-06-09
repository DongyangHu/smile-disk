package com.sd.baseData.bean;

import java.io.Serializable;

public class SdModuleInfoBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String moduleId = null;
    private String moduleName = null;
    private String moduleStatus = null;
    private String modulePathId= null;
    private String moduleUrl = null;
    private String moduleParentId = null;
    private String moduleOrderby = null;
    
    
    public String getModuleId() {
        return moduleId;
    }
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    public String getModuleName() {
        return moduleName;
    }
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    public String getModuleStatus() {
        return moduleStatus;
    }
    public void setModuleStatus(String moduleStatus) {
        this.moduleStatus = moduleStatus;
    }
    public String getModulePathId() {
        return modulePathId;
    }
    public void setModulePathId(String modulePathId) {
        this.modulePathId = modulePathId;
    }
    public String getModuleUrl() {
        return moduleUrl;
    }
    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }
    public String getModuleParentId() {
        return moduleParentId;
    }
    public void setModuleParentId(String moduleParentId) {
        this.moduleParentId = moduleParentId;
    }
    public String getModuleOrderby() {
        return moduleOrderby;
    }
    public void setModuleOrderby(String moduleOrderby) {
        this.moduleOrderby = moduleOrderby;
    }
    
}
