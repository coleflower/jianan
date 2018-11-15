/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: TripVo.java
 * Author:   first.ji
 * Date:     2018年9月13日 下午1:40:48
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.vo.trip;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TripVO {
    /**JSON格式
     * {
     * "tripPlan":{"days":"","cause":"","cost":""},
     * "tripItems":[{"destination":"",..,"remarks":""},{...}]
     * }
     */
    // 出差计划
    private TripPlanVO tripPlan;
    // 行程明细
    private List<TripItemVO> tripItems;
    /**
     * @return the tripPlan
     */
    public TripPlanVO getTripPlan() {
        return tripPlan;
    }
    /**
     * @param tripPlan the tripPlan to set
     */
    public void setTripPlan(TripPlanVO tripPlan) {
        this.tripPlan = tripPlan;
    }
    /**
     * @return the tripItems
     */
    public List<TripItemVO> getTripItems() {
        return tripItems;
    }
    /**
     * @param tripItems the tripItems to set
     */
    public void setTripItems(List<TripItemVO> tripItems) {
        this.tripItems = tripItems;
    }

}
