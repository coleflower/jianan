package com.cubicpark.mechanic.domain.service.base.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.dao.base.IPositionDAO;
import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
import com.cubicpark.mechanic.domain.service.base.IPositionService;
import com.firstji.mentha.domain.service.assortment.impl.AssortmentBseSupport;

@Service
public class PositionServiceImpl extends AssortmentBseSupport<PositionDTO, IPositionDAO> implements IPositionService {

    @Autowired
    private IPositionDAO positionDAO;

    @Override
    public IPositionDAO getBaseDao() {
        return positionDAO;
    }

    public PositionDTO getPositionByCode(String positionCode) {
        if (StringUtils.isBlank(positionCode))
            return null;
        
        return positionDAO.getPositionByCode(positionCode);
    }

    
}
