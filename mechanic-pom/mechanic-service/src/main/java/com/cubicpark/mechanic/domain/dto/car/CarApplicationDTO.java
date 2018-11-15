package com.cubicpark.mechanic.domain.dto.car;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("t_car_application")
public class CarApplicationDTO {

    private Integer id;

    //车辆id
    private String carId;

    //车辆使用流水号
    private String carUseCode;

    //员工编号
    private String employeeCode;

    //员工姓名
    private String employeeName;

    //行驶里程
    private double roadHaul;

    //加油次数
    private Integer refuelingNumber;

    //状态 0待审核 1审核成 2审核拒绝
    private Integer status;

    //备注
    private String remark;

    //使用时间
    private Timestamp useTime;

    //结束时间
    private Timestamp endTime;

    //创建时间
    private Timestamp createTime;
}
