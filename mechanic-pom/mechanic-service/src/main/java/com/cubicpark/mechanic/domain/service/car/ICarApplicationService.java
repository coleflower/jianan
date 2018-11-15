package com.cubicpark.mechanic.domain.service.car;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cubicpark.mechanic.domain.dto.car.CarApplicationDTO;

import java.sql.Timestamp;
import java.util.List;

public interface ICarApplicationService extends IService<CarApplicationDTO> {

    /**
     * 根据employeeCode查询
     * @param employeeCode
     * @return
     */
    List<CarApplicationDTO> selectByEmployeeCode(String employeeCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据车辆Id查找
     *
     * @param carId
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.car.CarApplicationDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<CarApplicationDTO> selectByCarId(String carId);

    /**
     * 根据姓名时间查询
     * @param employeeName
     * @param startDate
     * @param endTime
     * @return
     */
    List<CarApplicationDTO> selectByEmployeeNameAndDate(String carId,String employeeName, Timestamp startDate,Timestamp endTime);
}
