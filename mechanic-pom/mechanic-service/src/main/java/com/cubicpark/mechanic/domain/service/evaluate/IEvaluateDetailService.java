package com.cubicpark.mechanic.domain.service.evaluate;

import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO;

import java.util.List;
/**
 * 〈一句话功能简述〉<br> 
 * 评价详情
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IEvaluateDetailService {

    /**
     * 〈一句话功能简述〉<br> 
     * //添加工单
     *
     * @param [evaluateDetailDTO, employeeDTO]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insert(EvaluateDetailDTO evaluateDetailDTO, EmployeeDTO employeeDTO);

    /**
     * 〈一句话功能简述〉<br> 
     * //更新新的工单
     *
     * @param [evaluateDetailDTO]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int update(EvaluateDetailDTO evaluateDetailDTO);

    /**
     * 〈一句话功能简述〉<br> 
     * //根据工单标号查找
     *
     * @param [productEvaluateCode]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<EvaluateDetailDTO> selectByProductEvaluateCode(String productEvaluateCode);
}
