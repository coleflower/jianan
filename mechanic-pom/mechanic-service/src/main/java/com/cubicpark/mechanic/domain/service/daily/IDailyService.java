package com.cubicpark.mechanic.domain.service.daily;

import com.cubicpark.mechanic.domain.dto.daily.DailyDTO;

import java.util.Date;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 日报管理
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IDailyService {

    /**
     * 插入新的日报
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
     * 查找今天所有的日报
     * @return
     */
    List<DailyDTO> selectToday();

    /**
     * 〈一句话功能简述〉<br>
     * 查询今天工单有没有填写
     *
     * @param employeeCode
     * @return com.cubicpark.mechanic.domain.dto.daily.DailyDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    DailyDTO selectTodayByEmployeeCode(String employeeCode);

    /**
     * 查找自己一个月内所有的日报
     * @param employeeCode
     * @return
     */
    List<DailyDTO> findOneMouthBefore(String employeeCode);

    /**
     * 〈一句话功能简述〉<br>
     * 查找相似的code
     *
     * @param code
     * @return java.lang.String[]
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<String> selectCodeLike(String code);

    /**
     * 根据时间查找字的日报
     * @param employeeCode
     * @param startTime
     * @return
     */
    List<DailyDTO> selectMyselfByDate(String employeeCode,String startTime);

    /**
     * 根据时间和输入的code模糊查询
     * @param time
     * @param code
     * @return
     */
    List<DailyDTO> selectByDateAndCodeLike( String time, String code);
}
