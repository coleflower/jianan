package com.cubicpark.mechanic.domain.service.storage;

import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.storage.Rack;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/7
 * @since 1.0.0
 */
public interface IRackService extends IService<Rack> {

    /**
     * 根据货架编号查询
     * @param rackCode
     * @return
     */
    Rack findByRackCode(String rackCode);
}
