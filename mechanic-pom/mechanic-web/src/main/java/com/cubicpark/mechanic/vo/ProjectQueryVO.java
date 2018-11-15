package com.cubicpark.mechanic.vo;

public class ProjectQueryVO {
    // 项目分类编码
    private String projectCategoryCode;
    // 项目名称
    private String projectName;
    // 项目主导人
    private String owner;
    // 项目状态
    private String projectState;
    public String getProjectCategoryCode() {
        return projectCategoryCode;
    }
    public void setProjectCategoryCode(String projectCategoryCode) {
        this.projectCategoryCode = projectCategoryCode;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getProjectState() {
        return projectState;
    }
    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }
}
