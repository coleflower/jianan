package com.cubicpark.mechanic.domain.dto.storage;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Auther: Surging
 * @Date: 2018/8/31 16:21
 * @Description: 出库/入库表
 */
@Data
@TableName("t_stock")
public class Stock {

    private Integer id;

    /* 规格名称 */
    private String name;

    /* 材料 */
    private String material;

    /* 入库/出库编号 */
    private String stockCode;

    /*采购清单编号*/
    private String purchaseCode;

    /*物品编号*/
    private String goodsCode;

    /*货架编号*/
    private String rackCode;

    /*单价*/
    private String price;

    /*数量*/
    private String number;

    /*0为入库  1为出库*/
    private Integer type;

    /*入库/出库人编号*/
    private String employeeCode;

    /*创建时间*/
    @TableField("createdate")
    private Timestamp createDate;

}