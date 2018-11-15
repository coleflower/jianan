package com.cubicpark.mechanic.vo;

public class CustomerContactRecordVO {
    // ID
    private int id;
    // 客户编号
    private String customerCode;
    // 联系人编号
    private String contactsCode;
    // 联系途径
    private String connectionWay;
    // 沟通类型
    private String communicateType;
    // 沟通内容
    private String contents;
    // 联系日期
    private String contactDate;
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
    public String getContactsCode() {
        return contactsCode;
    }
    public void setContactsCode(String contactsCode) {
        this.contactsCode = contactsCode;
    }
    public String getConnectionWay() {
        return connectionWay;
    }
    public void setConnectionWay(String connectionWay) {
        this.connectionWay = connectionWay;
    }
    public String getCommunicateType() {
        return communicateType;
    }
    public void setCommunicateType(String communicateType) {
        this.communicateType = communicateType;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public String getContactDate() {
        return contactDate;
    }
    public void setContactDate(String contactDate) {
        this.contactDate = contactDate;
    }
    public String getInfoState() {
        return infoState;
    }
    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }
}
