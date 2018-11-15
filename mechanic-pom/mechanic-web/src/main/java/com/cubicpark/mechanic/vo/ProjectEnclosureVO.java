package com.cubicpark.mechanic.vo;

import java.sql.Timestamp;

public class ProjectEnclosureVO {
    // ID
    private int id;
    // 项目编号
    private String projectCode;
    // 附件名称
    private String enclosureName;
    // 附件路径
    private String url;
    // 描述
    private String description;
    // 信息状态标记
    private String infoState;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    public String getEnclosureName() {
        return enclosureName;
    }
    public void setEnclosureName(String enclosureName) {
        this.enclosureName = enclosureName;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getInfoState() {
        return infoState;
    }
    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }    
    
}
