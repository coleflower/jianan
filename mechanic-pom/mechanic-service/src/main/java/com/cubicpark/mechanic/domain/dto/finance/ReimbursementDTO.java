/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: ReimbursementDTO.java
 * Author:   first.ji
 * Date:     2018年9月7日 下午3:11:03
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.dto.finance;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 报销DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class ReimbursementDTO {
    // ID
    private int id;
    // 申请流水号
    private String applyNo;
    // 申请人（雇员编号）
    private String employeeCode;
    // 报销类型
    private String mold;
    // 所属业务编码
    private String code;
    // 费用总额
    private String cost;
    // 审批状态
    private String state;
    // 审批人
    private String approver;
    // 汇路
    private String payWay;
    // 打款凭证
    private String voucher;
    // 完成时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    // 备注
    private String remarks;
    //版本号
    private int version;
    // 创建日期
    private Timestamp createDate;
    // 更新日期
    private Timestamp updateDate;

}
