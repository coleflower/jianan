package com.cubicpark.mechanic.domain.dto.contract;

public class ContractFundCheckDTO {
    
    // 合同编号
    private String contractCode;
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
    // 错误码
    private String errorCode;
    // 是否提交 提交true 保存false
    private boolean isCommit;
    // 款项条目
    private ContractFundDTO contractFund;
    
    public String getContractCode() {
        return contractCode;
    }
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
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
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public boolean isCommit() {
        return isCommit;
    }
    public void setCommit(boolean isCommit) {
        this.isCommit = isCommit;
    }
    public ContractFundDTO getContractFund() {
        return contractFund;
    }
    public void setContractFund(ContractFundDTO contractFund) {
        this.contractFund = contractFund;
    }
}
