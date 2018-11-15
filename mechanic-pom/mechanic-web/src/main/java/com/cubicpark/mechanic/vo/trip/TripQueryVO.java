package com.cubicpark.mechanic.vo.trip;

import com.cubicpark.mechanic.vo.PageVO;



public class TripQueryVO extends PageVO {
    // 所在部门
    private String departmentCode;
    // 所选员工
    private String employeeCode;
    // 日期范围-起
    private String startDate;
    // 日期范围-止
    private String endDate;
    // 审批状态
    private String state;

    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
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
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
}
