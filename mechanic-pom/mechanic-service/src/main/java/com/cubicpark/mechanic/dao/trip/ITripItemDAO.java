/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: ITripItemDAO.java
 * Author:   first.ji
 * Date:     2018年9月7日 下午3:30:58
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.dao.trip;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.trip.TripItemDTO;

/**
 * 〈一句话功能简述〉<br> 
 * 出差行程DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ITripItemDAO {

    /**
     * 
     * 功能描述: <br>
     * 批量新增行程明细
     *
     * @param items
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int batchAddTripItem(List<TripItemDTO> tripItems);
    
    /**
     * 
     * 功能描述: <br>
     * 根据计划编码获取行程明细列表
     *
     * @param planCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<TripItemDTO> getTripItemByPlanCode(String planCode);
}
