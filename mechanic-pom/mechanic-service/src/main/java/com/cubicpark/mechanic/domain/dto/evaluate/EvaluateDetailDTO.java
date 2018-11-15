package com.cubicpark.mechanic.domain.dto.evaluate;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EvaluateDetailDTO {

    //id
    private Long id;

    //产品评价编号
    private String productEvlauateCode;

    //部门编号
    private String departmentCode;

    //员工编号
    private String employeeCode;

    //分数
    private BigDecimal score;

    //评价
    private String evaluate;

    //创建时间
    private Date createDate;

}