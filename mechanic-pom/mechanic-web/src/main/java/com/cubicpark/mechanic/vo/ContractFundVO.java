package com.cubicpark.mechanic.vo;

public class ContractFundVO {
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
    // 结清日期
    private String settleDate;
    // 备注
    private String remarks;
    // 信息状态标记
    private String infoState;
    // 是否提交状态
    private String isCommit;
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
    public String getSettleDate() {
        return settleDate;
    }
    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getInfoState() {
        return infoState;
    }
    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }
    public String getIsCommit() {
        return isCommit;
    }
    public void setIsCommit(String isCommit) {
        this.isCommit = isCommit;
    }

}
