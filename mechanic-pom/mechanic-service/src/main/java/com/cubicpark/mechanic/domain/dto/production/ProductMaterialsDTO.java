package com.cubicpark.mechanic.domain.dto.production;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ProductMaterialsDTO {

    private Integer id;

    //原料工单
    private String productMaterialsCode;

    //产品编号
    private String productCode;

    //产品原料名字
    private String productMaterialsName;

    //产品原料
    private String productMaterials;

    //产品原料数量
    private Integer productMaterialsCount;

    //产品标准代码
    private String productMaterialsStandardCode;

    //备注
    private String remark;

    //出库日志
    private String stockLog;

    //原料状态 0默认 1缺货 2采购中 3有货
    private Integer procurementStatus;

    //产品价格
    private String supplier;

    //产品价格
    private BigDecimal productPrice;

    //采购备注
    private String procurementRemark;

    //处理人编号
    private String handlerCode;

    //创建时间
    private Timestamp createDate;

    //申请原因
    private String applicationReason;

    //图片
    private String icon;
}
