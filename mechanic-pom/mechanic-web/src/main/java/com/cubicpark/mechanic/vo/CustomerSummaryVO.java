package com.cubicpark.mechanic.vo;


public class CustomerSummaryVO {
    // ID
    private int id;
    // 客户编号
    private String customerCode;
    // 开始时间
    private String startTime;
    // 结束时间
    private String endTime;
    // 心得描述
    private String description;
    // 是否评价
    private String isReply;
    // 评价人编号
    private String employeeCode;
    // 评价内容
    private String replyInfo;
    // 信息状态标记
    private String infoState;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCustomerCode() {
        return customerCode;
    }
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIsReply() {
        return isReply;
    }
    public void setIsReply(String isReply) {
        this.isReply = isReply;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getReplyInfo() {
        return replyInfo;
    }
    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo;
    }
    public String getInfoState() {
        return infoState;
    }
    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }
}
