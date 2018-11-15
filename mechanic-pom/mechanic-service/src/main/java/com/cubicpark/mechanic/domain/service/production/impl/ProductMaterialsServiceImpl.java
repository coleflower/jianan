package com.cubicpark.mechanic.domain.service.production.impl;

import com.cubicpark.mechanic.dao.production.IProductMaterialsDAO;
import com.cubicpark.mechanic.dao.production.IProductionDAO;
import com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO;
import com.cubicpark.mechanic.domain.service.production.IProductMaterialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductMaterialsServiceImpl implements IProductMaterialsService {

    @Autowired
    private IProductMaterialsDAO productMaterialsDAO;

    @Autowired
    private IProductionDAO productionDAO;

    @Override
    public int insert(ProductMaterialsDTO productMaterialsDTO) {
        productMaterialsDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        int row = productMaterialsDAO.insert(productMaterialsDTO);
        return row;
    }

    @Override
    public int updateById(ProductMaterialsDTO productMaterialsDTO) {
        return productMaterialsDAO.updateById(productMaterialsDTO);
    }

    @Override
    public List<ProductMaterialsDTO> selectByProductCode(String productMaterialsCode) {
        return productMaterialsDAO.selectByProductCode(productMaterialsCode);
    }

    @Override
    public void deleteById(Integer id) {
        productMaterialsDAO.deleteById(id);
    }

    @Override
    public ProductMaterialsDTO selectById(Integer id) {
        return productMaterialsDAO.selectById(id);
    }

    @Override
    public List<ProductMaterialsDTO> selectByStatus(List<Integer> outStorageStatus) {
        return productMaterialsDAO.selectByStatus(outStorageStatus);
    }

    @Override
    public List<ProductMaterialsDTO> selectAllMaterials() {
        return productMaterialsDAO.selectAllMaterials();
    }

    @Override
    public ProductMaterialsDTO selectByProductMaterialsCode(String productMaterialsCode) {
        return productMaterialsDAO.selectByProductMaterialsCode(productMaterialsCode);
    }


}
