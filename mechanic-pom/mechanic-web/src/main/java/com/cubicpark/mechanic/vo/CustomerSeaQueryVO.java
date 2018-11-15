package com.cubicpark.mechanic.vo;



public class CustomerSeaQueryVO extends PageVO {

    // 日期范围-起
    private String startDate;
    // 日期范围-止
    private String endDate;

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
