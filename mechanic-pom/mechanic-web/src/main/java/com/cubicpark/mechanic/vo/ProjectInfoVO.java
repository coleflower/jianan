package com.cubicpark.mechanic.vo;

import java.sql.Timestamp;

public class ProjectInfoVO {
    // ID
    private int id;
    // 项目分类编码
    private String projectCategoryCode;
    // 项目分类编码
    private String projectCategoryName;
    // 项目编码
    private String projectCode;
    // 项目名称
    private String projectName;
    // 项目主导人
    private String owner;
    // 最低限价
    private String floorPrice;
    // 项目状态
    private String projectState;
    // 目标行业（客群）
    private String targetIndustry;
    // 项目描述
    private String projectDescription;
    // 信息状态标记
    private String infoState;
    // 创建日期
    private Timestamp createDate;
    // 更新日期
    private Timestamp updateDate;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getProjectCategoryCode() {
        return projectCategoryCode;
    }
    public void setProjectCategoryCode(String projectCategoryCode) {
        this.projectCategoryCode = projectCategoryCode;
    }
    public String getProjectCategoryName() {
        return projectCategoryName;
    }
    public void setProjectCategoryName(String projectCategoryName) {
        this.projectCategoryName = projectCategoryName;
    }
    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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
    public String getFloorPrice() {
        return floorPrice;
    }
    public void setFloorPrice(String floorPrice) {
        this.floorPrice = floorPrice;
    }
    public String getProjectState() {
        return projectState;
    }
    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }
    public String getTargetIndustry() {
        return targetIndustry;
    }
    public void setTargetIndustry(String targetIndustry) {
        this.targetIndustry = targetIndustry;
    }
    public String getProjectDescription() {
        return projectDescription;
    }
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }
    public String getInfoState() {
        return infoState;
    }
    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }
    public Timestamp getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    public Timestamp getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
}
