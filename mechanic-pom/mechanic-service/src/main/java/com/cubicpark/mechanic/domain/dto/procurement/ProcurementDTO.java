package com.cubicpark.mechanic.domain.dto.procurement;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ProcurementDTO {

    private Integer id;

    //采购工单编号
    private String procurementCode;

    //产品编号
    private String productCode;

    //产品原料Id
    private Integer productMaterialsId;

    //规格名称
    private String name;

    //原料材料
    private String material;

    //原料数量
    private Integer count;

    //标准代号
    private String codeName;

    //供应商
    private String supplier;

    //处理人
    private String operator;

    //报价
    private BigDecimal quotedPrice;

    //状态
    private Integer status;

    //备注
    private String remark;

    //创建时间
    private Timestamp createTime;
}
