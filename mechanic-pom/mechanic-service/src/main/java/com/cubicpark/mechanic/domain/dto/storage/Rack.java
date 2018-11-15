package com.cubicpark.mechanic.domain.dto.storage;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Auther: Surging
 * @Date: 2018/8/31 16:21
 * @Description: 货架/堆货区表
 */
@Data
@TableName("t_rack")
public class Rack {

    private Integer id;

    /*货架编号*/
    private String rackCode;

    /*所属仓库编号*/
    private String storageCode;

    /*类型: 0为堆货区 1为货架*/
    private Integer type;

    /*所在区域*/
    private String area;

    /*描述*/
    private String description;

    /* 总库存*/
    private String size;

    /* 已使用库存*/
    private String stock;

    @TableField("createdate")
    private Timestamp createDate;

    @TableField("updatedate")
    private Timestamp updateDate;
}