package com.cubicpark.mechanic.domain.service.evaluate;

import com.cubicpark.mechanic.domain.dto.fix.FixDTO;
import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDTO;

import java.sql.Timestamp;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 *
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IEvaluateService {


    /**
     * 〈一句话功能简述〉<br>
     * //自动创建新的工单
     *
     * @param [fixDTO]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int create(FixDTO fixDTO);

    /**
     * 〈一句话功能简述〉<br>
     * //查找一个月内的工单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<EvaluateDTO> selectOneMouthOrder();


    /**
     * 〈一句话功能简述〉<br>
     * //根据id查询
     *
     * @param [id]
     * @return com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    EvaluateDTO selectById(Integer id);


    /**
     * 〈一句话功能简述〉<br>
     * //修改数据
     *
     * @param [evaluateDTO]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateById(EvaluateDTO evaluateDTO);


    /**
     * 〈一句话功能简述〉<br>
     * //根据条件查询信息
     *
     * @param [expressCode, status, createTime, endTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<EvaluateDTO> selectByCodeOrStatusOrDate(String expressCode, Integer status, Timestamp createTime, Timestamp endTime);

    /**
     * 根据输入的值查询相似的Code
     * @param code input框中输入的值
     * @return
     */
    List<String> searchExpressCodeLike(String code);
}
