package com.cubicpark.mechanic.dao.evaluate;

import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IEvaluateDetailDAO {

    /**
     * 〈一句话功能简述〉<br>
     * 插入新的工单
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insert(EvaluateDetailDTO record);

    /**
     * 〈一句话功能简述〉<br>
     * 插入新的工单(为空的值不插入)
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insertSelective(EvaluateDetailDTO record);

    /**
     * 〈一句话功能简述〉<br>
     * 根据ID查询工单
     *
     * @param [id]
     * @return com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    EvaluateDetailDTO selectByPrimaryKey(Long id);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id更新工单(空值不进行插入)
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByPrimaryKeySelective(EvaluateDetailDTO record);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id跟新操作
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByPrimaryKey(EvaluateDetailDTO record);

    /**
     * 〈一句话功能简述〉<br>
     * 根据产品编号查询
     *
     * @param [productEvaluateCode]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<EvaluateDetailDTO> selectByProductEvaluateCode(String productEvaluateCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据评价单号和员工编号查询
     *
     * @param [productEvaluateCode, employeeCode]
     * @return com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    EvaluateDetailDTO selectByProductEvaluateCodeAndEmployeeCode(@Param(value = "productEvaluateCode") String productEvaluateCode,
                                                                 @Param(value = "employeeCode")String employeeCode);
}
