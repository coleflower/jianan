package com.cubicpark.mechanic.domain.service.assessor;

import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.assessor.Assessor;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 审核service
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IAssessorService extends IService<Assessor> {

    /**
     * 根据采购单id查找
     * @param procurementId
     * @return
     */
    List<Assessor> selectByProcurementId(Integer procurementId);

    /**
     * 根据采购id和序号查询
     * @param procurementId
     * @param sort
     * @return
     */
    Assessor selectByProcurementIdAndSort(Integer procurementId,Integer sort);

    /**
     * 根据采购id和序号查询
     * @param employeeCode
     * @param sort
     * @return
     */
    List<Assessor> selectByEmployeeCodeAndSort(String employeeCode,Integer sort);

    /**
     * 根据采购id和序号查询
     * @param procurementId
     * @param status
     * @return
     */
    Assessor selectByProcurementIdAndStatus(Integer procurementId, int status);

    /**
     * 根据员工编号和客户查询
     * @param employeeCode
     * @param status
     * @return
     */
    List<Assessor> selectByEmployeeCodeAndStatus(String employeeCode, int status);
}
