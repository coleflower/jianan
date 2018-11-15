package com.cubicpark.mechanic.domain.dto.customer;

import java.sql.Timestamp;

import com.cubicpark.mechanic.domain.dto.InfoBaseDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户联系记录DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CustomerContactRecordDTO extends InfoBaseDTO {
    
    // ID
    private int id;
    // 客户编号
    private String customerCode;
    // 联系人编号
    private String contactsCode;
    // 联系人姓名
    private String contactsName;
    // 联系途径
    private String connectionWay;
    // 沟通类型
    private String communicateType;
    // 沟通内容
    private String contents;
    // 联系日期
    private String contactDate;
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
    public String getContactsCode() {
        return contactsCode;
    }
    public void setContactsCode(String contactsCode) {
        this.contactsCode = contactsCode;
    }
    public String getContactsName() {
        return contactsName;
    }
    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
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
