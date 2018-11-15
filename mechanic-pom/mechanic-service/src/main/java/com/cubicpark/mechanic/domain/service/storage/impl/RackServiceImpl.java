package com.cubicpark.mechanic.domain.service.storage.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.dao.storage.IRackDAO;
import com.cubicpark.mechanic.domain.dto.storage.Rack;
import com.cubicpark.mechanic.domain.service.storage.IRackService;
import com.cubicpark.mechanic.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/7
 * @since 1.0.0
 */
@Service
public class RackServiceImpl extends ServiceImpl<IRackDAO, Rack> implements IRackService {

    @Autowired
    private IRackDAO designDAO;

    @Override
    public Rack findByRackCode(String rackCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("rack_code", rackCode);
        List<Rack> rackList = designDAO.selectByMap(map);
        if (CollectionUtils.isEmpty(rackList)){
            throw new ServiceException(CommonErrorCode.RACK_NOT_EXIST);
        }
        return rackList.get(0);
    }

    @Override
    public boolean updateById(Rack entity) {
        entity.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        return super.updateById(entity);
    }
}
