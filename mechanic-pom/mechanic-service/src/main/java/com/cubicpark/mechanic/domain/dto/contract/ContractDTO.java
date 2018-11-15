package com.cubicpark.mechanic.domain.dto.contract;

import java.sql.Timestamp;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 合同DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ContractDTO {
    // ID
    private int id;
    // 客户编号
    private String customerCode;
    //项目编号
    private String projectCode;
    // 配件补发合同编号
    private String parentContractCode;
    // 合同编号
    private String contractCode;
    // 所属员工
    private String employeeCode;
    // 备案号
    private String recordNumber;
    // 合同名称
    private String contractName;
    // 成交日期
    private String dealDate;
    // 交货日期
    private String deliveryDate;
    // 交货内容
    private String deliveryInfo;
    // 联系人
    private String contacts;
    // 收货地址
    private String deliveryAdr;
    // 付款方式
    private String payMethod;
    // 合同标的
    private String total;
    // 预付比例
    private String advanceRatio;
    // 合同状态
    private String contractState;
    // 款项状态
    private String fundState;
    // 结清日期
    private String settleDate;
    // 备注
    private String remarks;
    //产品型号
    private String productType;
    //产品名称
    private String productName;
    //产品数量
    private String productCount;
    // 创建日期
    private Timestamp createDate;
    // 更新日期
    private Timestamp updateDate;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getParentContractCode() {
        return parentContractCode;
    }

    public void setParentContractCode(String parentContractCode) {
        this.parentContractCode = parentContractCode;
    }



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
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getContractCode() {
        return contractCode;
    }
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    public String getRecordNumber() {
        return recordNumber;
    }
    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }
    public String getDealDate() {
        return dealDate;
    }
    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }
    /**
     * @return the deliveryDate
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }
    /**
     * @param deliveryDate the deliveryDate to set
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    /**
     * @return the deliveryInfo
     */
    public String getDeliveryInfo() {
        return deliveryInfo;
    }
    /**
     * @param deliveryInfo the deliveryInfo to set
     */
    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
    /**
     * @return the contacts
     */
    public String getContacts() {
        return contacts;
    }
    /**
     * @param contacts the contacts to set
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
    /**
     * @return the deliveryAdr
     */
    public String getDeliveryAdr() {
        return deliveryAdr;
    }
    /**
     * @param deliveryAdr the deliveryAdr to set
     */
    public void setDeliveryAdr(String deliveryAdr) {
        this.deliveryAdr = deliveryAdr;
    }
    /**
     * @return the payMethod
     */
    public String getPayMethod() {
        return payMethod;
    }
    /**
     * @param payMethod the payMethod to set
     */
    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
    public String getSettleDate() {
        return settleDate;
    }
    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }
    public String getContractName() {
        return contractName;
    }
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getAdvanceRatio() {
        return advanceRatio;
    }
    public void setAdvanceRatio(String advanceRatio) {
        this.advanceRatio = advanceRatio;
    }
    public String getContractState() {
        return contractState;
    }
    public void setContractState(String contractState) {
        this.contractState = contractState;
    }
    public String getFundState() {
        return fundState;
    }
    public void setFundState(String fundState) {
        this.fundState = fundState;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }
}
