/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: EmployeeBaseInfoDTO.java
 * Author:   first.ji
 * Date:     2018年9月17日 下午1:48:14
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.dto;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class EmployeeBaseInfoDTO {

    // 姓名
    private String name;
    // 部门名称
    private String departmentName;
    // 职位名称
    private String positionName;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }
    /**
     * @param departmentName the departmentName to set
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    /**
     * @return the positionName
     */
    public String getPositionName() {
        return positionName;
    }
    /**
     * @param positionName the positionName to set
     */
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
