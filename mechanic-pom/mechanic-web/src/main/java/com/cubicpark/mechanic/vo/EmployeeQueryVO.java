package com.cubicpark.mechanic.vo;



public class EmployeeQueryVO {

    // 员工工号
    private String employeeCode;
    // 姓名
    private String name;
    // 帐号状态
    private String accountStatus;
    // 部门编号
    private String departmentCode;
    // 入职时间-起
    private String startEntryDate;
    // 入职时间-止
    private String endEntryDate;
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    public String getStartEntryDate() {
        return startEntryDate;
    }
    public void setStartEntryDate(String startEntryDate) {
        this.startEntryDate = startEntryDate;
    }
    public String getEndEntryDate() {
        return endEntryDate;
    }
    public void setEndEntryDate(String endEntryDate) {
        this.endEntryDate = endEntryDate;
    }
}
