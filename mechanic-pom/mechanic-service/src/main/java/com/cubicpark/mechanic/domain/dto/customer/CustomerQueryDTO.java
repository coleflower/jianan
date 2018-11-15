package com.cubicpark.mechanic.domain.dto.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.PageDTO;

public class CustomerQueryDTO extends PageDTO {
    private String areaCode;// 所在区域 
    private String customerName;// 客户名称
    private String grade;// 客户等级
    private String startDate;// 时间起
    private String endDate;// 时间止
    private String event;// 操作事件
    private List<String> employeeList;// 雇员编号
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }
    public List<String> getEmployeeList() {
        return employeeList;
    }
    public void setEmployeeList(List<String> employeeList) {
        this.employeeList = employeeList;
    }

}
