package com.cubicpark.mechanic.domain.service.production.impl;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.dao.production.IProductionDAO;
import com.cubicpark.mechanic.domain.dto.production.ProductionDTO;
import com.cubicpark.mechanic.domain.service.production.IProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
@Service
public class ProductionServiceImpl implements IProductionService {

    @Autowired
    private IProductionDAO productionDAO;

    @Override
    public int insert(ProductionDTO productionDTO) {
        //设置产品原料编号
        String productionCode = MenthaCodeUtil.generateMenthaCode("production", 18);
        productionDTO.setProductCode(productionCode);
        //设置时间戳
        productionDTO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        productionDTO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int row = productionDAO.insert(productionDTO);
        return row;
    }

    @Override
    public int updateById(ProductionDTO productionDTO) {
        int row = productionDAO.updateById(productionDTO);
        return row;
    }

    @Override
    public List<ProductionDTO> selectAll() {
        List<ProductionDTO> productionDTOS = productionDAO.selectAll();
        return productionDTOS;
    }

    @Override
    public ProductionDTO selectByProductCode(String productCode) {
        return productionDAO.selectByProductCode(productCode);
    }

    @Override
    public ProductionDTO selectById(Integer id) {
        return productionDAO.selectById(id);
    }

    @Override
    public ProductionDTO selectByContractCode(String contractCode) {
        return productionDAO.selectByContractCode(contractCode);
    }

}
