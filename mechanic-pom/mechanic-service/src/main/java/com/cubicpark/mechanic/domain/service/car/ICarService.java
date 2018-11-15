package com.cubicpark.mechanic.domain.service.car;

import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.car.CarDTO;

import java.util.List;

public interface ICarService extends IService<CarDTO> {

    List<CarDTO> selectAll();
}
