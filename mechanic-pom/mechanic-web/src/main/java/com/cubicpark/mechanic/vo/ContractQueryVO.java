package com.cubicpark.mechanic.vo;



public class ContractQueryVO {

    // 合同名称
    private String contractName;
    // 成交日期范围-起
    private String startDealDate;
    // 成交日期范围-止
    private String endDealDate;
    // 合同状态
    private String contractState;
    public String getContractName() {
        return contractName;
    }
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public String getStartDealDate() {
        return startDealDate;
    }
    public void setStartDealDate(String startDealDate) {
        this.startDealDate = startDealDate;
    }
    public String getEndDealDate() {
        return endDealDate;
    }
    public void setEndDealDate(String endDealDate) {
        this.endDealDate = endDealDate;
    }
    public String getContractState() {
        return contractState;
    }
    public void setContractState(String contractState) {
        this.contractState = contractState;
    }
}
