package com.cubicpark.mechanic.domain.dto.express;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ExpressDTO {
    //id
    private Integer id;
    //物流编号
    private String expressCode;
    //合同编号
    private String contractCode;
    //员工编号
    private String employeeCode;
    //员工名字
    private String employeeName;
    //目的地
    private String destination;
    //发送时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    //到达时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arriveTime;
    //运费time
    private BigDecimal expressPrice;
    //物流公司
    private String expressCompany;
    //物流状态
    private Integer status;
    //备注
    private String remark;
    //版本号
    private int version;
    //创建时间
    private Timestamp createDate;
    //跟新时间
    private Timestamp updateDate;

}