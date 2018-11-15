/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: ITripPlanDAO.java
 * Author:   first.ji
 * Date:     2018年9月7日 下午3:30:24
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.dao.trip;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cubicpark.mechanic.domain.dto.trip.TripPlanDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripPlanQueryDTO;

/**
 * 〈一句话功能简述〉<br>
 * 出差计划DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ITripPlanDAO {

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
    int addTripPlan(TripPlanDTO tripPlan);

    /**
     * 
     * 功能描述: <br>
     * 修改计划状态
     *
     * @param planCode
     * @param oldState
     * @param newState
     * @param approver
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyTripPlanState(@Param("planCode") String planCode, @Param("employeeCode") String employeeCode, @Param("oldState") String oldState,
            @Param("newState") String newState, @Param("approver") String approver);

    /**
     * 
     * 功能描述: <br>
     * 查询出差计划数量
     *
     * @param query
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int queryTripPlanTotal(TripPlanQueryDTO query);
    
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
    List<TripPlanDTO> queryTripPlan(TripPlanQueryDTO query);

    /**
     * 
     * 功能描述: <br>
     * 获取出差计划信息
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
     * 根据状态获取出差计划列表
     *
     * @param state
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<TripPlanDTO> getTripPlanByState(String state);
}
