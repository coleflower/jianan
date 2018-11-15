package com.cubicpark.mechanic.domain.dto.customer;

import java.sql.Timestamp;

import com.cubicpark.mechanic.domain.dto.InfoBaseDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户需求DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CustomerDemandDTO extends InfoBaseDTO {

    // ID
    private int id;
    // 客户编号
    private String customerCode;
    // 需求类型
    private String demandType;
    // 需求日期
    private String demandDate;
    // 需求内容
    private String demandInfo;
    // 需求状态
    private String demandState;
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
    public String getDemandType() {
        return demandType;
    }
    public void setDemandType(String demandType) {
        this.demandType = demandType;
    }
    public String getDemandDate() {
        return demandDate;
    }
    public void setDemandDate(String demandDate) {
        this.demandDate = demandDate;
    }
    public String getDemandInfo() {
        return demandInfo;
    }
    public void setDemandInfo(String demandInfo) {
        this.demandInfo = demandInfo;
    }
    public String getDemandState() {
        return demandState;
    }
    public void setDemandState(String demandState) {
        this.demandState = demandState;
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
