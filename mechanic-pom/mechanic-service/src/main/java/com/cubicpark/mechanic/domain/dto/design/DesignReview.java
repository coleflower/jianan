package com.cubicpark.mechanic.domain.dto.design;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Auther: Surging
 * @Date: 2018/8/31 16:21
 * @Description: 设计工单审核表
 */
@Data
@TableName("t_design_review")
public class DesignReview {

    private Integer id;

    private String designCode;

    /*1通过 -1为审核不通过*/
    private Integer review;

    /*备注*/
    private String remarks;

    private String employeeCode;

    @TableField("createdate")
    private Timestamp createDate;
}