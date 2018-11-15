package com.cubicpark.mechanic.domain.dto.design;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Auther: Surging
 * @Date: 2018/8/31 16:21
 * @Description: 设计工单表
 */
@TableName("t_design")
@Data
public class Design implements Serializable {

    private static final long serialVersionUID = -6830512861456311922L;

    private Integer id;

    /*设计工单编号*/
    private String designCode;

    /*文件名称*/
    // 这样可以注入 LIKE 查询 @TableField(condition = SqlCondition.LIKE)
    private String fileName;

    /*图号*/
    private String drawingNumber;

   /* 材料*/
    private String material;

    /*技术要求*/
    private String technicalRequirement;

    /*设计图ID*/
    private Integer picId;

    /** 合同编号 **/
    private String contractCode;

    /** 工单状态 0为未审核 1审核中 -1未通过  2已通过**/
    private Integer status;

    //@TableField(fill = FieldFill.INSERT)
    @TableField("createdate")
    private Timestamp createDate;

    //@TableField(fill = FieldFill.INSERT_UPDATE)
    @TableField("updatedate")
    private Timestamp updateDate;
}