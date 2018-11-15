package com.cubicpark.mechanic.domain.service.procurement;

import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementGoodsDTO;

import java.sql.Timestamp;
import java.util.List;

public interface IProcurementGoodsService extends IService<ProcurementGoodsDTO> {

    /**
     * 查询采购物料单编号(不重复的)
     * @return
     */
    List<ProcurementGoodsDTO> selectCodeDistinct();

    /**
     * 根据采购清单编号查询
     * @param procurementGoodsCode
     * @return
     */
    List<ProcurementGoodsDTO> selectByProcurementGoodsCode(String procurementGoodsCode);

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
    List<ProcurementGoodsDTO> selectByStatusOrDate(Integer status, Timestamp startTime, Timestamp endTime);
}
