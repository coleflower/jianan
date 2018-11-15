/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: TripPlanVO.java
 * Author:   first.ji
 * Date:     2018年9月13日 下午2:04:07
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.vo.trip;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TripPlanVO {
    // 出差天数
    private String days;
    // 出差事由
    private String cause;
    // 出差预估费
    private String cost;
    /**
     * @return the days
     */
    public String getDays() {
        return days;
    }
    /**
     * @param days the days to set
     */
    public void setDays(String days) {
        this.days = days;
    }
    /**
     * @return the cause
     */
    public String getCause() {
        return cause;
    }
    /**
     * @param cause the cause to set
     */
    public void setCause(String cause) {
        this.cause = cause;
    }
    /**
     * @return the cost
     */
    public String getCost() {
        return cost;
    }
    /**
     * @param cost the cost to set
     */
    public void setCost(String cost) {
        this.cost = cost;
    }
}
