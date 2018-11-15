package com.cubicpark.mechanic.dao.car;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cubicpark.mechanic.domain.dto.car.CarApplicationDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ICarApplicationDAO extends BaseMapper<CarApplicationDTO> {

    /**
     * 根据名字时间查询
     * @param employeeName
     * @param startDate
     * @param endDate
     * @return
     */
    List<CarApplicationDTO> selectByEmployeeNameAndDate(@Param(value = "carId") String carId,
                                                        @Param(value = "employeeName") String employeeName,
                                                        @Param(value = "startDate")Timestamp startDate,
                                                        @Param(value = "endDate")Timestamp endDate);

}
