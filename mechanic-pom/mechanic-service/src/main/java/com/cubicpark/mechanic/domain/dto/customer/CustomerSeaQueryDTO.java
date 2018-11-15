package com.cubicpark.mechanic.domain.dto.customer;

import com.cubicpark.mechanic.domain.dto.PageDTO;

public class CustomerSeaQueryDTO extends PageDTO {
    private String startDate;// 时间起
    private String endDate;// 时间止

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
