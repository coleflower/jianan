package com.cubicpark.mechanic.domain.service.evaluate.impl;

import com.cubicpark.mechanic.dao.evaluate.IEvaluateDetailDAO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO;
import com.cubicpark.mechanic.domain.service.evaluate.IEvaluateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 评价详情
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class EvaluateDetailServiceImpl implements IEvaluateDetailService {

    @Autowired
    private IEvaluateDetailDAO productEvaluateDetailDAO;

    @Override
    public int insert(EvaluateDetailDTO evaluateDetailDTO, EmployeeDTO employeeDTO) {
        evaluateDetailDTO.setDepartmentCode(employeeDTO.getDepartmentCode());
        evaluateDetailDTO.setCreateDate(new Timestamp(System.currentTimeMillis()));
        evaluateDetailDTO.setEmployeeCode(employeeDTO.getEmployeeCode());
        int reult = productEvaluateDetailDAO.insert(evaluateDetailDTO);
        return reult;
    }

    @Override
    public int update(EvaluateDetailDTO evaluateDetailDTO) {
        EvaluateDetailDTO evaluateDetailDTO1 = productEvaluateDetailDAO.selectByPrimaryKey(evaluateDetailDTO.getId());
        evaluateDetailDTO.setEmployeeCode(evaluateDetailDTO1.getEmployeeCode());
        evaluateDetailDTO.setDepartmentCode(evaluateDetailDTO1.getDepartmentCode());
        evaluateDetailDTO.setCreateDate(evaluateDetailDTO1.getCreateDate());
        int result = productEvaluateDetailDAO.updateByPrimaryKey(evaluateDetailDTO);
        return result;
    }

    @Override
    public List<EvaluateDetailDTO> selectByProductEvaluateCode(String productEvaluateCode) {
        return productEvaluateDetailDAO.selectByProductEvaluateCode(productEvaluateCode);
    }
}
