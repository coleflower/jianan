package com.cubicpark.mechanic.domain.service.storage.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.dao.storage.IRackDAO;
import com.cubicpark.mechanic.dao.storage.IStorageDAO;
import com.cubicpark.mechanic.domain.dto.storage.Rack;
import com.cubicpark.mechanic.domain.dto.storage.Storage;
import com.cubicpark.mechanic.domain.service.storage.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/7
 * @since 1.0.0
 */
@Service
public class StorageServiceImpl extends ServiceImpl<IStorageDAO, Storage> implements IStorageService {

    @Autowired
    private IRackDAO rackDAO;

    @Override
    public boolean isEmpty(String storageCode) {
        Rack rack = new Rack();
        rack.setStorageCode(storageCode);
        List<Rack> rackList = rackDAO.selectList(new EntityWrapper<>(rack));
        if (CollectionUtils.isEmpty(rackList)){
            return true;
        }
        return false;
    }
}
