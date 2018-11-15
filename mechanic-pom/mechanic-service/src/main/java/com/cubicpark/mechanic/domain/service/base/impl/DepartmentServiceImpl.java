package com.cubicpark.mechanic.domain.service.base.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.dao.base.IDepartmentDAO;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.firstji.mentha.domain.service.assortment.impl.AssortmentBseSupport;

@Service
public class DepartmentServiceImpl extends AssortmentBseSupport<DepartmentDTO, IDepartmentDAO> implements IDepartmentService {

    @Autowired
    private IDepartmentDAO departmentDAO;

    @Override
    public IDepartmentDAO getBaseDao() {
        return departmentDAO;
    }

    public DepartmentDTO getDepartmentByCode(String departmentCode) {
        if (StringUtils.isBlank(departmentCode))
            return null;
        
        return departmentDAO.getDepartmentByCode(departmentCode);
    }
}
