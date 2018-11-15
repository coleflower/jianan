package com.cubicpark.mechanic.domain.dto.base;

import com.firstji.mentha.domain.dto.assortment.AssortmentBaseDTO;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 部门DTO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DepartmentDTO extends AssortmentBaseDTO {

    // 部门名称
    private String departmentName;
    // 部门编码
    private String departmentCode;
    // 是否需要客户管理
    private int isCRM;
    
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
        super.setAssortmentName(departmentName);
    }
    public String getDepartmentCode() {
        return departmentCode;
    }
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    public int getIsCRM() {
        return isCRM;
    }
    public void setIsCRM(int isCRM) {
        this.isCRM = isCRM;
    }
}
