package com.cubicpark.mechanic.domain.service.procurement;

import com.cubicpark.mechanic.domain.dto.procurement.ProcurementDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 采购单service
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IProcurementService {

    /**
     * 插入采购单
     * @param count,productMaterialsDTO
     * @return
     */
    int insert(Integer count, ProductMaterialsDTO productMaterialsDTO);

    /**
     * 根据状态查询
     * @param status
     * @return
     */
    List<ProcurementDTO> selectByStatusAndEmployeeCode(Integer status,String employeeCode);

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    ProcurementDTO selectById(Integer id);

    /**
     * 根据Id更新
     * @param procurementDTO
     * @return
     */
    int updateById(ProcurementDTO procurementDTO);

    /**
     * 查询所有
     * @return
     */
    List<ProcurementDTO> selectAll();

    /**
     * 根据原料Id查询
     * @param id
     * @return
     */
    ProcurementDTO selectByProductMaterialsId(Integer id);

    /**
     * 根据ID批量查询
     * @param procurementId
     * @return
     */
    List<ProcurementDTO> selectByProcurementIdArray(List<Integer> procurementId);

    /**
     * 根据状态查询
     * @param status
     * @return
     */
    List<ProcurementDTO> selectByStatus(Integer status);
}
