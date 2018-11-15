package com.cubicpark.mechanic.domain.dto.evaluate;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EvaluateDTO {

    //id
    private Integer id;

    //产品评价编号
    private String productEvluateCode;

    //合同编号
    private String contractCode;

    //平均分
    private BigDecimal average;

    //创建时间
    private Date createDate;

    //更新时间
    private Date updateDate;

}