package com.cubicpark.mechanic.vo;


public class ContractInfoVO {
    // ID
    private int id;

    // 补发合同编号
    private String parentContractCode;
    // 合同编号
    private String contractCode;
    // 所在部门
    private String departmentCode;
    // 所属员工
    private String employeeCode;
    // 客户编号
    private String customerCode;
    // 客户名称
    private String customerName;
    // 合同名称
    private String contractName;
    // 备案号
    private String recordNumber;
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
    // 产品型号
    private String productType;
    // 产品名称
    private String productName;
    // 产品数量
    private String productCount;


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
    public String getContractCode() {
        return contractCode;
    }
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    public String getEmployeeCode() {
        return employeeCode;
    }
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
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
    public String getContractName() {
        return contractName;
    }
    public void setContractName(String contractName) {
        this.contractName = contractName;
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
}
