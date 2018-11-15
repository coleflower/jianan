package com.cubicpark.mechanic.domain.dto.procurement;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("t_procurement_goods")
public class ProcurementGoodsDTO  {

    //id
    private Integer id;

    //采购物料编号
    private String procurementGoodsCode;

    //原料名称
    private String name;

    //原料材料
    private String material;

    //原料标准代号
    private String standardCode;

    //数量
    private Integer count;

    //状态
    private Integer status;

    //处理人编号
    private String employeeCode;

    //处理人姓名
    private String employeeName;

    //创建时间
    private Timestamp createTime;
}
