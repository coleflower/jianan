/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: TripPlanDTO.java
 * Author:   first.ji
 * Date:     2018年9月7日 下午3:01:00
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.dto.trip;

import java.sql.Timestamp;

/**
 * 〈一句话功能简述〉<br> 
 * 出差计划DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TripPlanDTO {
    // ID
    private int id;
    // 计划编号
    private String planCode;
    // 雇员编号
    private String employeeCode;
    // 出差天数
    private String days;
    // 出差事由
    private String cause;
    // 出差预估费
    private String cost;
    // 状态
    private String state;
    // 审批人
    private String approver;
    // 创建日期
    private Timestamp createDate;
    // 更新日期
    private Timestamp updateDate;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the planCode
     */
    public String getPlanCode() {
        return planCode;
    }
    /**
     * @param planCode the planCode to set
     */
    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }
    /**
     * @return the employeeCode
     */
    public String getEmployeeCode() {
        return employeeCode;
    }
    /**
     * @param employeeCode the employeeCode to set
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    /**
     * @return the days
     */
    public String getDays() {
        return days;
    }
    /**
     * @param days the days to set
     */
    public void setDays(String days) {
        this.days = days;
    }
    /**
     * @return the cause
     */
    public String getCause() {
        return cause;
    }
    /**
     * @param cause the cause to set
     */
    public void setCause(String cause) {
        this.cause = cause;
    }
    /**
     * @return the cost
     */
    public String getCost() {
        return cost;
    }
    /**
     * @param cost the cost to set
     */
    public void setCost(String cost) {
        this.cost = cost;
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
    /**
     * @return the approver
     */
    public String getApprover() {
        return approver;
    }
    /**
     * @param approver the approver to set
     */
    public void setApprover(String approver) {
        this.approver = approver;
    }
    /**
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
    /**
     * @return the updateDate
     */
    public Timestamp getUpdateDate() {
        return updateDate;
    }
    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }
}
