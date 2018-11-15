/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: Tripdto.java
 * Author:   first.ji
 * Date:     2018年9月10日 下午2:48:16
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.dto.trip;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.EmployeeBaseInfoDTO;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TripDTO extends EmployeeBaseInfoDTO {
    // 出差计划
    private TripPlanDTO tripPlan;
    // 行程明细
    private List<TripItemDTO> tripItems;

    /**
     * @return the tripPlan
     */
    public TripPlanDTO getTripPlan() {
        return tripPlan;
    }

    /**
     * @param tripPlan the tripPlan to set
     */
    public void setTripPlan(TripPlanDTO tripPlan) {
        this.tripPlan = tripPlan;
    }

    /**
     * @return the tripItems
     */
    public List<TripItemDTO> getTripItems() {
        return tripItems;
    }

    /**
     * @param tripItems the tripItems to set
     */
    public void setTripItems(List<TripItemDTO> tripItems) {
        this.tripItems = tripItems;
    }

}
