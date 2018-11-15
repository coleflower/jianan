package com.cubicpark.mechanic.dao.procurement;

import com.cubicpark.mechanic.domain.dto.procurement.ProcurementDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 生产采购单
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IProcurementDAO {

    /**
     * 添加采购单
     * @param procurementDTO
     * @return
     */
    int insert(ProcurementDTO procurementDTO);

    /**
     * 根据状态查询
     * @param status
     * @return
     */
    List<ProcurementDTO> selectByStatusAndEmployeeCode(@Param(value = "status") Integer status, @Param(value = "employeeCode") String employeeCode);

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
