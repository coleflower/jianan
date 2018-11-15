package com.cubicpark.mechanic.domain.service.design.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.design.IDesignReviewDAO;
import com.cubicpark.mechanic.domain.dto.design.Design;
import com.cubicpark.mechanic.domain.dto.design.DesignReview;
import com.cubicpark.mechanic.domain.service.design.IDesignReviewService;
import com.cubicpark.mechanic.domain.service.design.IDesignService;
import com.cubicpark.mechanic.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/6
 * @since 1.0.0
 */
@Service
public class IDesignReviewServiceImpl extends ServiceImpl<IDesignReviewDAO, DesignReview> implements IDesignReviewService {

    @Autowired
    private IDesignService designService;

    @Override
    public boolean insert(DesignReview entity) {
        Design design = designService.selectByDesignCode(entity.getDesignCode());
        if (design == null) {
            throw new ServiceException(CommonErrorCode.DESIGN_NOT_EXIST);
        }
        if (design.getStatus() == Constants.PASS) {
            throw new ServiceException(CommonErrorCode.DESIGN_STATUS_REVIEWED);
        }
         // 设置工单审核结果
        design.setStatus(entity.getReview());
        return super.insert(entity) && designService.updateById(design);
    }
}
