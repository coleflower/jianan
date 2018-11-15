/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: TripPlanQueryDTO.java
 * Author:   first.ji
 * Date:     2018年9月10日 下午3:35:41
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.dto.trip;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.PageDTO;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TripPlanQueryDTO extends PageDTO {
    // 状态
    private String state;
    private String startDate;// 时间起
    private String endDate;// 时间止
    private List<String> employeeList;// 雇员编号
    /**
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }
    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    /**
     * @return the employeeList
     */
    public List<String> getEmployeeList() {
        return employeeList;
    }
    /**
     * @param employeeList the employeeList to set
     */
    public void setEmployeeList(List<String> employeeList) {
        this.employeeList = employeeList;
    }
    
}
