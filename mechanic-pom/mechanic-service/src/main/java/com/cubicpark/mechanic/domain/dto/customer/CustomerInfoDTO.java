package com.cubicpark.mechanic.domain.dto.customer;

import java.sql.Timestamp;

import com.cubicpark.mechanic.domain.dto.InfoBaseDTO;


/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户基本信息DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CustomerInfoDTO extends InfoBaseDTO {
    // ID
    private int id;
    // 雇员编号（工号）
    private String employeeCode;
    // 区域编号
    private String areaCode;
    // 客户编号
    private String customerCode;
    // 客户名称
    private String customerName;
    // 法人
    private String legalPerson;
    // 总机
    private String switchBoard;
    // 传真
    private String fax;
    //国家
    private String country;
    //省
    private String province;
    //市
    private String city;
    //区县
    private String county;
    // 地址
    private String address;
    // 邮编
    private String postCode;
    // 所属行业
    private String industry;
    // 经营范围
    private String businessScope;
    // 客户等级
    private String grade;
    // 客户类型
    private String customerType;
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
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getCustomerCode() {
        return customerCode;
    }
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getLegalPerson() {
        return legalPerson;
    }
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }
    public String getSwitchBoard() {
        return switchBoard;
    }
    public void setSwitchBoard(String switchBoard) {
        this.switchBoard = switchBoard;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPostCode() {
        return postCode;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getBusinessScope() {
        return businessScope;
    }
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    /**
     * @return the customerType
     */
    public String getCustomerType() {
        return customerType;
    }
    /**
     * @param customerType the customerType to set
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
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
