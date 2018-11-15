package com.cubicpark.mechanic.vo;

public class CustomerProductVO {
    // ID
    private int id;
    // 客户编号
    private String customerCode;
    // 产品名称
    private String productName;
    // 产品描述
    private String description;
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
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getInfoState() {
        return infoState;
    }
    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }
}
