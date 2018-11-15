package com.cubicpark.mechanic.domain.dto.daily;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class DailyDTO {

    private Integer id;

    private String summary;

    private String plan;

    private String felling;

    private String employeeCode;

    private String department;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
