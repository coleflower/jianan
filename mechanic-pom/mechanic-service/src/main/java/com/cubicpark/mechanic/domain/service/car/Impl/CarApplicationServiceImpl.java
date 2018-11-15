package com.cubicpark.mechanic.domain.service.car.Impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cubicpark.mechanic.dao.car.ICarApplicationDAO;
import com.cubicpark.mechanic.domain.dto.car.CarApplicationDTO;
import com.cubicpark.mechanic.domain.service.car.ICarApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CarApplicationServiceImpl extends ServiceImpl<ICarApplicationDAO,CarApplicationDTO> implements ICarApplicationService {

    @Autowired
    private ICarApplicationDAO applicationDAO;

    @Override
    public List<CarApplicationDTO> selectByEmployeeCode(String employeeCode) {
        EntityWrapper<CarApplicationDTO> wrapper = new EntityWrapper<>();
        wrapper.eq("employee_code",employeeCode);
        return super.selectList(wrapper);
    }

    @Override
    public List<CarApplicationDTO> selectByCarId(String carId) {
        EntityWrapper<CarApplicationDTO> wrapper = new EntityWrapper<>();
        wrapper.eq("car_id",carId).eq("status",1);
        return super.selectList(wrapper);
    }

    @Override
    public List<CarApplicationDTO> selectByEmployeeNameAndDate(String carId,String employeeName, Timestamp startDate, Timestamp endTime) {
       return applicationDAO.selectByEmployeeNameAndDate(carId,employeeName,startDate,endTime);
    }


}
