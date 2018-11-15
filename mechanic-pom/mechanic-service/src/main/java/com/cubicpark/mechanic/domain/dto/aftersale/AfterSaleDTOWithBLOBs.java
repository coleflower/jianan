package com.cubicpark.mechanic.domain.dto.aftersale;

import lombok.Data;

@Data
public class AfterSaleDTOWithBLOBs extends AfterSaleDTO {

    //用户投诉
    private String customerComplaint;

    //解决方案
    private String solution;
}