package com.cubicpark.mechanic.domain.dto.contract;

import java.sql.Timestamp;

import com.cubicpark.mechanic.domain.dto.InfoBaseDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 合同款项DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractFundDTO extends InfoBaseDTO {
    // ID
    private int id;
    // 合同编号
    private String contractCode;
    // 款项类型
    private String fundType;
    // 款项名称
    private String itemName;
    // 付款方式
    private String payWay;
    // 付款金额
    private String fund;
    // 备注
    private String remarks;
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
    public String getContractCode() {
        return contractCode;
    }
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    public String getFundType() {
        return fundType;
    }
    public void setFundType(String fundType) {
        this.fundType = fundType;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getPayWay() {
        return payWay;
    }
    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }
    public String getFund() {
        return fund;
    }
    public void setFund(String fund) {
        this.fund = fund;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
