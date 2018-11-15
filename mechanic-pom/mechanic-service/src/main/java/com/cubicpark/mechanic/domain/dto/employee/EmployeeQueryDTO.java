package com.cubicpark.mechanic.domain.dto.employee;

import java.sql.Timestamp;
import java.util.List;

public class EmployeeQueryDTO {
    // 员工工号
    private String employeeCode;
    // 姓名
    private String name;
    // 帐号状态
    private String accountStatus;
    // 部门编号
    private List<String> departmentCode;
    // 入职时间-起
    private Timestamp startEntryDate;
    // 入职时间-止
    private Timestamp endEntryDate;
    
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
    public List<String> getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(List<String> departmentCode) {
        this.departmentCode = departmentCode;
    }
    public Timestamp getStartEntryDate() {
        return startEntryDate;
    }
    public void setStartEntryDate(Timestamp startEntryDate) {
        this.startEntryDate = startEntryDate;
    }
    public Timestamp getEndEntryDate() {
        return endEntryDate;
    }
    public void setEndEntryDate(Timestamp endEntryDate) {
        this.endEntryDate = endEntryDate;
    }
}
