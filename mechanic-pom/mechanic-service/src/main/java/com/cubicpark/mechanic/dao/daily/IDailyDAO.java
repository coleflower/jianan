package com.cubicpark.mechanic.dao.daily;

import com.cubicpark.mechanic.domain.dto.daily.DailyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 日报
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IDailyDAO {

    /**
     * 插入日报
     * @param dailyDTO
     * @return
     */
    int insert(DailyDTO dailyDTO);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    DailyDTO selectById(Integer id);

    /**
     * 查询今天所有的日报
     * @param date
     * @return
     */
    List<DailyDTO> selectToday(String date);

    /**
     * 〈一句话功能简述〉<br>
     * 查询自己当天的日报
     *
     * @param employeeCode, dateNowStr]
     * @return com.cubicpark.mechanic.domain.dto.daily.DailyDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    DailyDTO selectTodayByEmployeeCode(@Param(value = "employeeCode") String employeeCode, @Param(value = "dateNowStr") String dateNowStr);

    /**
     * 〈一句话功能简述〉<br>
     * 查询自己一个月内的日报
     *
     * @param oneMouthBefore, employeeCode]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.daily.DailyDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<DailyDTO> selectAllOneMouthBeforeOnMyself(@Param(value = "oneMouthBefore")Date oneMouthBefore,@Param(value = "employeeCode")String employeeCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据时间查询自己的日报
     *
     * @param employeeCode, startTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.daily.DailyDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<DailyDTO> selectMyselfByDate(@Param(value = "employeeCode")String employeeCode,@Param(value = "startTime")String startTime);

    /**
     * 〈一句话功能简述〉<br>
     * 查询相似的employeeCode
     *
     * @param code
     * @return java.lang.String[]
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<String> selectCodeLike(String code);

    /**
     * 〈一句话功能简述〉<br>
     * 根据时间和相似的employeeCode查询
     *
     * @param time, code]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.daily.DailyDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<DailyDTO> selectByDateAndCodeLike(@Param(value = "time") String time,@Param(value = "code") String code);
}
