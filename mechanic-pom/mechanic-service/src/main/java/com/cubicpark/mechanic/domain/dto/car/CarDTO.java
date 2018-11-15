package com.cubicpark.mechanic.domain.dto.car;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("t_car")
public class CarDTO {

    private Integer id;

    //品牌
    private String brank;

    //型号
    private String type;

    //总里程数
    private Double totalRoadHaul;

    //总加油次数
    private Integer totalRefuelingNumber;

    //状态 0未使用 1正在使用
    private Integer status;

    //创建时间
    private Timestamp createTime;
}
