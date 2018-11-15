package com.cubicpark.mechanic.dao.production;

import com.cubicpark.mechanic.domain.dto.production.ProductionDTO;

import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 产品工单管理
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IProductionDAO {

    int insert(ProductionDTO productionDTO);

    int updateById(ProductionDTO productionDTO);

    /**
     * 〈一句话功能简述〉<br>
     * 根据状态查询所有的生产单
     *
     * @param
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.production.ProductionDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ProductionDTO> selectAll();

    ProductionDTO selectById(Integer id);

    ProductionDTO selectByProductCode(String productCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据合同编号查询
     *
     * @param contractCode
     * @return com.cubicpark.mechanic.domain.dto.production.ProductionDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    ProductionDTO selectByContractCode(String contractCode);

}
