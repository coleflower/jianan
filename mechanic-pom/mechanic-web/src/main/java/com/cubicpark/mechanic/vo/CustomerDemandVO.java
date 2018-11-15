package com.cubicpark.mechanic.vo;

public class CustomerDemandVO {
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
    public String getInfoState() {
        return infoState;
    }
    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }
}
