package com.cubicpark.mechanic.domain.service.production;

import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.production.CheckProductDTO;

import java.util.List;

public interface ICheckProductService extends IService<CheckProductDTO> {

    /**
     * 根据产品编号查询
     * @param productCode
     * @return
     */
    List<CheckProductDTO> selectByproductCode(String productCode);
}
