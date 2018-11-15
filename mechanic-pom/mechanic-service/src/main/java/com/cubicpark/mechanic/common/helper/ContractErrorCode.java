package com.cubicpark.mechanic.common.helper;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 合同及合同款项错误码枚举类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum ContractErrorCode {
    
    SUCCESS("200","成功"),
    ERROR001("contractStateIsNoEffectiveError","合同状态无效！"),
    ERROR002("contractFundInfoIsNoAllCommitError","该合同下存在未提交的款项条目信息！"),
    ERROR003("contractFundInfoStateIsNoClearError","款项信息保存或提交状态不明确！"),
    ERROR004("allPayButUnnotPayError","合同全款时款项状态非未付款！"),
    ERROR005("setainageAndFundIsNotSameOrSettleDateIsNullError","尾款和结算金额不一致或者结算日期为空！"),
    ERROR006("installmentButIsSettlementError","非全款时款项状态为已经结清，无需继续支付！"),
    ERROR007("installmentPayTypeIsNoFirstPayOrFundIsNotSameError","非全款待首付时，款项类型不是首付或者首付款和支付金额不一致！"),
    ERROR008("installmentPayTypeIsNobackPayOrAllPayGTTotalError","非全款已首付待回款时，款项类型不是正常回款或者累计支付金额超过总额！"),
    ERROR009("installmentPayTypeIsFirstPayError","非全款正常回款时，款项类型是首付！"),
    ERROR010("installmentPayTypeIsBackPayAllPayGTTotalError","非全款正常回款时,正常回款累计支付金额超过总额！"),
    ERROR011("installmentPayTypeIsEndPayAllPayGTTotalSettleDateIsNullError","非全款正常回款，尾款时累计支付金额超过总额且结算日期为空！");
    
    private String value;
    private String desc;

    private ContractErrorCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
    
    public static String getDescByValue(String value) {
        ContractErrorCode[] contractErrorCode = ContractErrorCode.values();
        for (int i=0;i<contractErrorCode.length;i++) {
            if (contractErrorCode[i].getValue().equals(value)) {
                return contractErrorCode[i].getDesc();
            }
        }
        return null;
    }

}
