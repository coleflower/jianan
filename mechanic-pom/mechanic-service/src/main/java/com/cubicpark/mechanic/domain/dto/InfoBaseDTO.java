package com.cubicpark.mechanic.domain.dto;

public class InfoBaseDTO {
    // 信息状态标记
    private String infoState;
    // 是否删除标记
    private int isDel;
    
    public String getInfoState() {
        return infoState;
    }
    public void setInfoState(String infoState) {
        this.infoState = infoState;
    }
    public int getIsDel() {
        return isDel;
    }
    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
    
    
}
