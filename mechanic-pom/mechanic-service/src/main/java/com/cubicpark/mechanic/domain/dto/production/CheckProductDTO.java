package com.cubicpark.mechanic.domain.dto.production;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("t_check_product")
public class CheckProductDTO {

    //id
    private Integer id;

    //关联的产品编号
    private String productCode;

    //检验项名称
    private String itemName;

    //分数
    private Integer score;

    //备注
    private String remark;

    //创建时间
    private Timestamp createTime;
}
