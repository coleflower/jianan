package com.cubicpark.mechanic.domain.service.production.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.dao.production.ICheckProductDAO;
import com.cubicpark.mechanic.domain.dto.production.CheckProductDTO;
import com.cubicpark.mechanic.domain.service.production.ICheckProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckProductServiceImpl extends ServiceImpl<ICheckProductDAO,CheckProductDTO> implements ICheckProductService {

    //查询该产品下的检验项
    @Override
    public List<CheckProductDTO> selectByproductCode(String productCode) {
        EntityWrapper<CheckProductDTO> wrapper = new EntityWrapper<>();
        wrapper.eq("product_code",productCode);
        return super.selectList(wrapper);
    }
}
