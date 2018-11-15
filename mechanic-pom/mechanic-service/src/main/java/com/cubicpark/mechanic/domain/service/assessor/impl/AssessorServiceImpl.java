package com.cubicpark.mechanic.domain.service.assessor.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.dao.assessor.IAssessorDAO;
import com.cubicpark.mechanic.domain.dto.assessor.Assessor;
import com.cubicpark.mechanic.domain.service.assessor.IAssessorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 审核serviceImpl
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class AssessorServiceImpl extends ServiceImpl<IAssessorDAO,Assessor> implements IAssessorService {

    @Override
    public List<Assessor> selectByProcurementId(Integer procurementId) {
        EntityWrapper<Assessor> wrapper = new EntityWrapper<>();
        wrapper.eq("procurement_id",procurementId);
        return super.selectList(wrapper);
    }

    @Override
    public Assessor selectByProcurementIdAndSort(Integer procurementId, Integer sort) {
        EntityWrapper<Assessor> wrapper = new EntityWrapper<>();
        wrapper.eq("procurement_id",procurementId).eq("sort",sort);
        return super.selectOne(wrapper);
    }

    @Override
    public List<Assessor> selectByEmployeeCodeAndSort(String employeeCode, Integer sort) {
        EntityWrapper<Assessor> wrapper = new EntityWrapper<>();
        wrapper.eq("employee_code",employeeCode).eq("sort",sort);
        return super.selectList(wrapper);
    }

    @Override
    public Assessor selectByProcurementIdAndStatus(Integer procurementId, int status) {
        EntityWrapper<Assessor> wrapper = new EntityWrapper<>();
        wrapper.eq("procurement_id",procurementId).eq("status",status);
        return super.selectOne(wrapper);
    }

    @Override
    public List<Assessor> selectByEmployeeCodeAndStatus(String employeeCode, int status) {
        EntityWrapper<Assessor> wrapper = new EntityWrapper<>();
        wrapper.eq("employee_code",employeeCode).eq("status",status);
        return super.selectList(wrapper);
    }
}
