package com.cubicpark.mechanic.domain.service.procurement.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.dao.procurement.IProcurementGoodsDAO;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementGoodsDTO;
import com.cubicpark.mechanic.domain.service.procurement.IProcurementGoodsService;
import com.cubicpark.mechanic.domain.service.production.IProductMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProcurementGoodsServiceImpl extends ServiceImpl<IProcurementGoodsDAO,ProcurementGoodsDTO> implements IProcurementGoodsService {

    @Autowired
    private IProcurementGoodsDAO procurementGoodsDAO;

    @Override
    public List<ProcurementGoodsDTO> selectCodeDistinct() {
        return procurementGoodsDAO.selectCodeDistinct();
    }

    @Override
    public List<ProcurementGoodsDTO> selectByProcurementGoodsCode(String procurementGoodsCode) {
        EntityWrapper<ProcurementGoodsDTO> wrapper = new EntityWrapper<>();
        wrapper.eq("procurement_goods_code",procurementGoodsCode);
        return super.selectList(wrapper);
    }

    @Override
    public List<ProcurementGoodsDTO> selectCodeDistinctByStatus(Integer status) {
        return procurementGoodsDAO.selectCodeDistinctByStatus(status);
    }

    @Override
    public List<ProcurementGoodsDTO> selectByStatusOrDate(Integer status, Timestamp startTime, Timestamp endTime) {
        return procurementGoodsDAO.selectByStatusOrDate(status,startTime,endTime);
    }
}
