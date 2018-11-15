package com.cubicpark.mechanic.domain.service.storage;

import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.storage.Storage;

/**
 * 〈一句话功能简述〉<br>
 * 3,993,643
 * 1,217,360
 * @author Surging
 * @create 2018/9/7
 * @since 1.0.0
 */
public interface IStorageService extends IService<Storage> {

    /**
     * 判断仓库是否为空
     * @param storageCode
     * @return true为空
     */
    boolean isEmpty(String storageCode);
}
