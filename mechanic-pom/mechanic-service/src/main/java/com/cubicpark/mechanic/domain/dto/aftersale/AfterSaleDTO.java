package com.cubicpark.mechanic.domain.dto.aftersale;

import lombok.Data;

import java.util.Date;
/**
 * 〈一句话功能简述〉<br>
 * 售后服务DTO
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class AfterSaleDTO {
    // id
    private Integer id;

    // 售后工单编号
    private String afterServiceCode;

    // 合同编号
    private String contractCode;

    // 负责人编号
    private String employeeCode;

    // 客户编号
    private String customerCode;

    // 分数
    private Integer servicesCore;

    // 备注
    private String remark;

    // 状态
    private Integer status;

    //乐观锁版本号
    private int version;

    //创建时间
    private Date createDate;

    //更新时间
    private Date updateDate;

}