package com.cubicpark.mechanic.domain.service.design.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.design.IDesignDAO;
import com.cubicpark.mechanic.domain.dto.design.Design;
import com.cubicpark.mechanic.domain.service.design.IDesignService;
import com.cubicpark.mechanic.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Surging
 * @Date: 2018/8/31 16:21
 * @Description:
 */
@Service
public class DesignServiceImpl extends ServiceImpl<IDesignDAO, Design> implements IDesignService {

    @Autowired
    private IDesignDAO designDAO;

    @Override
    public Page<Design> selectUserPage(Page page, String designCode) {
        Design design = new Design();
        if (!StringUtils.isEmpty(designCode)){
            design.setDesignCode(designCode);
        }
        EntityWrapper<Design> eWrapper = new EntityWrapper<>(design);
        Page<Design> designPage = selectPage(page, eWrapper);
        return designPage;
    }

    @Override
    public Design selectByDesignCode(String designCode) {
        Design design = new Design();
        design.setDesignCode(designCode);
        List<Design> designList = designDAO.selectList(new EntityWrapper<>(design));
        if (CollectionUtils.isEmpty(designList)){
            throw new ServiceException(CommonErrorCode.DESIGN_NOT_EXIST);
        }
        return designList.get(0);
    }

    @Override
    public Design selectByContractCode(String contractCode) {
        EntityWrapper<Design> eWrapper = new EntityWrapper<>();
        eWrapper.eq("contract_code",contractCode);
        return selectOne(eWrapper);
    }


    @Override
    public boolean updateById(Design entity) {
        designCheck(entity.getId());
        return super.updateById(entity);
    }

    @Override
    public boolean deleteById(Serializable id) {
        designCheck(id);
        return super.deleteById(id);
    }

    private void designCheck(Serializable id) {
        Design design = designDAO.selectById(id);
        if (design == null){
            throw new ServiceException(CommonErrorCode.DESIGN_NOT_EXIST);
        }
        if (design.getStatus() == Constants.PASS) {
            throw new ServiceException(CommonErrorCode.DESIGN_STATUS_REVIEWED);
        }
    }
}
