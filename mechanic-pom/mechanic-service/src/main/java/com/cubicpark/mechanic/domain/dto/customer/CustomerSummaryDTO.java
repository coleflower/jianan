package com.cubicpark.mechanic.domain.dto.customer;

import java.sql.Timestamp;

import com.cubicpark.mechanic.domain.dto.InfoBaseDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户沟通总结DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CustomerSummaryDTO extends InfoBaseDTO {

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
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getIsReply() {
        return isReply;
    }
    public void setIsReply(String isReply) {
        this.isReply = isReply;
    }
    public String getReplyInfo() {
        return replyInfo;
    }
    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo;
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
