package com.cubicpark.mechanic.domain.dto.finance;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * 〈一句话功能简述〉<br>
 * 发票
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class FinanceBillDTO {

    //id
    private Integer id;

    //开票工单号
    private String financeBillCode;

    //发票抬头
    private String billTitle;

    //发票类型
    private String billType;

    //合同编号
    private String contractCode;

    //客户编号
    private String customerCode;

    //申请人
    private String proposer;

    //申请人
    private String proposerName;

    //员工编号
    private String employeeCode;

    //员工编号
    private String employeeName;

    //发票金额
    private BigDecimal money;

    //状态
    private String status;

    //发票影像
    private String icon;

    //备注
    private String remark;

    //创建时间
    private Timestamp createDate;
}
