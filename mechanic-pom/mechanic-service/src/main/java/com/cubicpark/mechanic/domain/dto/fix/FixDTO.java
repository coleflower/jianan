package com.cubicpark.mechanic.domain.dto.fix;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

/**
 *调试工单
 */
@Data
public class FixDTO {

    private Integer id;

    /**
     * 物流编号
     */
    private String expressCode;

    /**
     * 调试编号
     */
    private String debugOrderCode;

    /**
     *合同编号
     */
    private String contractCode;

    /**
     *提交者
     */
    private String employeeCode;

    /**
     *客户编号
     */
    private String customerCode;

    /**
     *调试时间
     */
    private Timestamp fixTime;

    /**
     *处理人编号
     */
    private String handlerCode;

    /**
     *处理人姓名
     */
    private String handlerName;

    /**
     *备注
     */
    private String remark;

    /**
     *状态 0待处理 1已处理
     */
    private Integer status;

    /**
     *版本号
     */
    private int version;

    /**
     *创建时间
     */
    private Date createDate;

    /**
     *更新时间
     */
    private Date updateDate;

}