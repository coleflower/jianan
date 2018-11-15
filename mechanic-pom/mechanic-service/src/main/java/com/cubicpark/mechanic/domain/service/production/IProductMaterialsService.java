package com.cubicpark.mechanic.domain.service.production;

import com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO;

import java.util.List;

public interface IProductMaterialsService {

    int insert(ProductMaterialsDTO productMaterialsDTO);

    int updateById(ProductMaterialsDTO productMaterialsDTO);

    /**
     * 〈一句话功能简述〉<br>
     * 根据产品原料编号查询
     *
     * @param productCode
     * @return com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ProductMaterialsDTO> selectByProductCode(String productCode);

    void deleteById(Integer id);

    ProductMaterialsDTO selectById(Integer id);

    /**
     * 〈一句话功能简述〉<br>
     * 根据状态查询
     *
     * @param outStorageStatus
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ProductMaterialsDTO> selectByStatus(List<Integer> outStorageStatus);

    /**
     * 〈一句话功能简述〉<br>
     * 查询所有原料
     *
     * @param
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ProductMaterialsDTO> selectAllMaterials();

    /**
     * 〈一句话功能简述〉<br>
     * 根据原料编号查询
     *
     * @param productMaterialsCode
     * @return com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    ProductMaterialsDTO selectByProductMaterialsCode(String productMaterialsCode);

}
