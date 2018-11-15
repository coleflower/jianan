package com.cubicpark.mechanic.vo.production;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 产品出库VO
 *
 * @param
 * @return
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class ProductionMaterialVO {

    //类型 标准件 非标件
    private String type;

    //名称规格
    private String name;

    //材料
    private String material;

    //标准代号
    private String codeName;

    //数量
    private Integer count;

    //库存数量
    private Integer storageCount;

    //状态 0未新增新的采购工单 1已发送
    private Integer status;

    //已出库数量
    private Integer hasOutStorageCount;

    //产品原料ID
    private Integer productionMaterialId;
}
