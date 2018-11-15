package com.cubicpark.mechanic.common.helper;


/**
 * 
 * 〈一句话功能简述〉<br> 
 * 公共错误码，按顺序进行错误代码的新增
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public enum CommonErrorCode {
    
    SUCCESS("200","成功"),
    OBJECTISNULL("001","对象为空或不存在"),
    CONDITIONISNULL("002","必要条件为空或不存在"),
    MODIFYCONDITIONISACCORD ("003","修改条件不符合"),
    DELCONDITIONISACCORD ("004","删除条件不符合"),
    COMPAREKEYISNOSAME("005","比较双方值不相同"),
    CATEGORYHAVESAME("006","该分类下已存在相同名称的信息，请确认！"),
    EXITSEFFECTCONTRACT("007","存在有效的合同信息！"),
    DESIGN_NOT_EXIST("008","设计工单不存在！"),
    DESIGN_STATUS_REVIEWED("009","设计工单已经审核或者正在审核！不能修改/删除"),
    RACK_NOT_EXIST("010","货架/堆货区不存在"),
    RACK_SIZE_OUT("011","货架/堆货区库存超出"),
    STORAGE_NOT_EXIST("012","仓库不存在"),
    STORAGE_NOT_EMPTY("013","删除失败 请先删除仓库内的堆货区/货架"),
    RACK_NOT_EMPTY("014","删除失败 请先删除堆货区/货架内的商品");
    private String value;
    private String desc;

    private CommonErrorCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
    
    public static String getDescByValue(String value) {
        CommonErrorCode[] commonErrorCode = CommonErrorCode.values();
        for (int i=0;i<commonErrorCode.length;i++) {
            if (commonErrorCode[i].getValue().equals(value)) {
                return commonErrorCode[i].getDesc();
            }
        }
        return null;
    }

}
