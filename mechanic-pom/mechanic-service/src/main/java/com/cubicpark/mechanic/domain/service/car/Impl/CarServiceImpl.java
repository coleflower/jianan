package com.cubicpark.mechanic.domain.service.car.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.dao.car.ICarDAO;
import com.cubicpark.mechanic.domain.dto.car.CarDTO;
import com.cubicpark.mechanic.domain.service.car.ICarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl extends ServiceImpl<ICarDAO,CarDTO> implements ICarService {

    @Override
    public List<CarDTO> selectAll() {
        EntityWrapper<CarDTO> wrapper = new EntityWrapper<>();
        return super.selectList(wrapper);
    }
}
