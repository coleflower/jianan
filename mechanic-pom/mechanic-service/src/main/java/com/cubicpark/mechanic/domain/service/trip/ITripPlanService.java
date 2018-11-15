/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: ITripPlanService.java
 * Author:   first.ji
 * Date:     2018年9月10日 下午2:45:59
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.service.trip;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.trip.TripDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripPlanDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripPlanQueryDTO;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ITripPlanService {
    
    /**
     * 
     * 功能描述: <br>
     * 新增出差计划及行程
     *
     * @param trip
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String addTrip(TripDTO trip);
    
    /**
     * 
     * 功能描述: <br>
     * 新增出差计划
     *
     * @param tripPlan
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String addTripPlan(TripPlanDTO tripPlan);
    
    /**
     * 
     * 功能描述: <br>
     * 作废出差计划
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String cancelTripPlan(String planCode, String employeeCode);
    
    /**
     * 
     * 功能描述: <br>
     * 修改出差计划状态，审批通过 审批拒绝
     * 出差计划状态为：待审批 -> 审批通过/待审批 -> 审批拒绝
     * 不存在审批拒绝经过修改后重新提交为待审批状态，至少目前这个阶段暂时不考虑
     * 后期如果需求修改，要加上审批历史记录表，并记录拒绝理由
     *
     * @param planCode
     * @param newState
     * @param approver
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String changeTripState(String planCode, String oldState, String newState, String approver);
    
    /**
     * 
     * 功能描述: <br>
     * 查询出差计划数量
     *
     * @param startDate
     * @param endDate
     * @param state
     * @param departmentCode
     * @param employeeCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int queryTripPlanTotal(String startDate, String endDate, String state, String departmentCode,
            String employeeCode);
    
    /**
     * 
     * 功能描述: <br>
     * 查询出差计划
     *
     * @param query
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<TripPlanDTO> queryTripPlan(String startDate, String endDate, String state, String departmentCode,
            String employeeCode, int page);
    
    /**
     * 
     * 功能描述: <br>
     * 获取出差计划
     *
     * @param planCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    TripPlanDTO getTripPlan(String planCode);
    
    /**
     * 
     * 功能描述: <br>
     * 获取待审批的出差计划及行程明细
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<TripDTO> getToApprovalTripPlan();

}
