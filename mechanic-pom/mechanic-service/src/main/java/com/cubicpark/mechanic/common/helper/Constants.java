package com.cubicpark.mechanic.common.helper;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 业务常量
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface Constants {
    // 回复状态
    public static final String NOREPLY = "0";// 未回复
    public static final String REPLY = "1";// 回复
    
    // 信息状态
    public static final String SAVE = "0";// 保存
    public static final String COMMIT ="1";// 提交
    
    // 预付款比例
    public static final String ADVANCE_RATIO_100 = "100";
    
    // 账号状态
    public static final String ACCOUNT_NO_ACTIVE ="101";// 未激活
    public static final String ACCOUNT_ACTIVE ="102";// 激活
    public static final String ACCOUNT_FORBIDDEN ="103";// 禁止
    public static final String ACCOUNT_DISABLE ="104";// 无效
    
    // 项目状态
    public static final String PROJECT_NO_ACTIVE = "101";// 未生效
    public static final String PROJECT_ACTIVE = "102";// 生效
    public static final String PROJECT_FINISH = "103";// 终止
    
    // 客户类型
    public static final String CUSTOMER_TYPE_NORMAL = "normal";// 客户类型-普通客户
    public static final String CUSTOMER_TYPE_HANDLE = "handle";// 客户类型-公海客户
    
    // 联系途径
    public static final String CONNECTION_WAY_PHONE = "phone";// 电话联系
    public static final String CONNECTION_WAY_QQ = "qq";// QQ
    public static final String CONNECTION_WAY_EMAIL = "email";// 电子邮件
    public static final String CONNECTION_WAY_WEIXIN = "weixin";// 微信
    public static final String CONNECTION_WAY_VISIT = "visit";// 拜访
    
    // 沟通类型
    public static final String COMMUNICATE_TYPE_ACTIVE = "active";// 主动
    public static final String COMMUNICATE_TYPE_PASSIVE = "passive";// 被动
    public static final String COMMUNICATE_TYPE_MEDIUM = "medium";// 第三方告知
    
    // 需求状态
    public static final String DEMAND_TYPE_SUSPECTED = "suspected";// 疑似
    public static final String DEMAND_TYPE_NEW = "new";// 新增 
    public static final String DEMAND_TYPE_EFFECTIVE = "effective";// 有效
    public static final String DEMAND_TYPE_REALIZATION  = "realization ";// 实现
    public static final String DEMAND_TYPE_CANCEL = "cancel";// 作废
    
    // 出差计划审批状态
    public static final String TRIP_STATE_TO = "to";// 待审批
    public static final String TRIP_STATE_PASS = "pass";// 审批通过
    public static final String TRIP_STATE_REFUSE = "refuse";// 审批拒绝
    public static final String TRIP_STATE_CANCEL = "cancel";// 作废
    
    // 合同状态
    public static final String CONTRACT_STATE_TOBEEFFECTIVE = "tobeeffective";// 待生效
    public static final String CONTRACT_STATE_EFFECTIVE = "effective";// 有效
    public static final String CONTRACT_STATE_CANCEL = "cancel";// 作废
    public static final String CONTRACT_STATE_EXPIRED = "expired ";// 已过期
    
    // 款项状态
    public static final String FUND_STATE_NOPAY = "nopay";// 未付款
    public static final String FUND_STATE_NOFIRSTPAY = "nofirstpay";// 未首付
    public static final String FUND_STATE_FIRSTPAY = "firstpay";// 已首付待回款
    public static final String FUND_STATE_BACKPAY = "backpay";// 正常回款未结清
    public static final String FUND_STATE_ENDPAY = "endpay";// 已尾款已结清
    public static final String FUND_STATE_ALLPAY = "allpay";// 全部结清
    
    // 款项类型
    public static final String FUND_TYPE_FIRSTPAY = "firstpay";// 首期款
    public static final String FUND_TYPE_BACKPAY = "backpay";// 正常回款
    public static final String FUND_TYPE_ENDPAY = "endpay";// 尾款
    
    // 分页每页数量
    public final static int COMMON_PAGESIZE = 10;

    // 审核状态
    public static final Integer CHECK_NO = 0;    // 未提交审核
    public static final Integer check_ing = 2;  // 审核中
    public static final Integer PASS = 1;       // 审核通过
    public static final Integer NO_PASS = -1;   // 审核不通过

    //生产状态
    public static  final Integer UNPRODUCT = 0;  //未生产
    public static  final Integer STARTPRODUCT = 1;  //生产中
    public static  final Integer FINISHPRODUCT = 2;  //生产完成
}
