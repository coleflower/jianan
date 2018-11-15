/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: TripItemServiceImpl.java
 * Author:   first.ji
 * Date:     2018年9月12日 下午2:19:05
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.domain.service.trip.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.dao.trip.ITripItemDAO;
import com.cubicpark.mechanic.domain.dto.trip.TripItemDTO;
import com.cubicpark.mechanic.domain.service.trip.ITripItemService;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class TripItemServiceImpl implements ITripItemService {
    
    @Autowired
    private ITripItemDAO tripItemDAO;

    public String addTripItem(List<TripItemDTO> tripItems) {
         tripItemDAO.batchAddTripItem(tripItems);
         return CommonErrorCode.SUCCESS.getValue();
    }

    public List<TripItemDTO> getTripItemByPlanCode(String planCode) {
        return tripItemDAO.getTripItemByPlanCode(planCode);
    }

}
