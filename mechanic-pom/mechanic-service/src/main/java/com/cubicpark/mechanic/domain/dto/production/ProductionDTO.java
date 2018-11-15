package com.cubicpark.mechanic.domain.dto.production;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class ProductionDTO {

    private Integer id;

    //产品编号
    private String productCode;

    //产品规格
    private String productType;

    //产品名称
    private String productName;

    //产品数量
    private String productCount;

    //合同编号
    private String contractCode;

    //处理人编号
    private String handlerCode;

    //原料审核人
    private String materialsHandler;

    //状态
    private Integer status;

    //工人数量
    private Integer workerCount;

    //预计结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    //备注
    private String remark;

    //原料审核备注
    private String materialsRemark;

    //质检备注
    private String qualityRemark;

    //创建时间
    private Timestamp createTime;

    //跟新时间
    private Timestamp updateTime;
}
