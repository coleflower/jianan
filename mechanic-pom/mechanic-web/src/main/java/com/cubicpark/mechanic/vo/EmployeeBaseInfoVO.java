package com.cubicpark.mechanic.vo;

import java.util.Date;


public class EmployeeBaseInfoVO {

    // 雇员编号（工号）
    private String employeeCode;
    // 所属部门
    private String departmentCode;
    // 职位
    private String positionCode;
    // 姓名
    private String name;
    // 性别
    private String sex;
    // 最高学历
    private String education;
    // 毕业院校
    private String college;
    // 分机号
    private String extension;
    // 联系电话
    private String telephone;
    // 入职时间
    private Date employeeEntryDate;
    
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    public String getPositionCode() {
        return positionCode;
    }
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getCollege() {
        return college;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public String getExtension() {
        return extension;
    }
    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Date getEmployeeEntryDate() {
        return employeeEntryDate;
    }
    public void setEmployeeEntryDate(Date employeeEntryDate) {
        this.employeeEntryDate = employeeEntryDate;
    }
}
