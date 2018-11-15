package com.cubicpark.mechanic.domain.dto.base;

import com.firstji.mentha.domain.dto.assortment.AssortmentBaseDTO;


/**
 * 
 * 〈一句话功能简述〉<br>
 * 职位DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class PositionDTO extends AssortmentBaseDTO {

    // 职位编码
    private String positionCode;
    // 职位名称
    private String positionName;
    
    public String getPositionCode() {
        return positionCode;
    }
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
        super.setAssortmentName(positionName);
    }
}
