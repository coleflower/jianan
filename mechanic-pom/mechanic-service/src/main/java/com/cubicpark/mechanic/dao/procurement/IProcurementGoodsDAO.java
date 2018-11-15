package com.cubicpark.mechanic.dao.procurement;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementGoodsDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface IProcurementGoodsDAO extends BaseMapper<ProcurementGoodsDTO> {

    /**
     * 查询采购物料单编号(不重复的)
     * @return
     */
    List<ProcurementGoodsDTO> selectCodeDistinct();

    /**
     * 查询待处理的采购单
     * @return
     */
    List<ProcurementGoodsDTO> selectCodeDistinctByStatus(Integer status);

    /**
     * 根据状态和时间查询
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    List<ProcurementGoodsDTO> selectByStatusOrDate(@Param(value = "status") Integer status, @Param(value = "startTime")Timestamp startTime,
                                                   @Param(value = "endTime")Timestamp endTime);

}
