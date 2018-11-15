package com.cubicpark.mechanic.dao.evaluate;

import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 评价管理Dao
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IEvaluateDAO {

    /**
     * 〈一句话功能简述〉<br>
     * 插入新的评价工单
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insert(EvaluateDTO record);

    /**
     * 〈一句话功能简述〉<br>
     *插入新的评价工单(空值不插入)
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insertSelective(EvaluateDTO record);

    /**
     * 〈一句话功能简述〉<br>
     *根据主键查询评价工单
     *
     * @param [id]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    EvaluateDTO selectByPrimaryKey(Integer id);

    /**
     * 〈一句话功能简述〉<br>
     *更新评价工单(空值不跟新)
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByPrimaryKeySelective(EvaluateDTO record);

    /**
     * 〈一句话功能简述〉<br>
     *更新评价工单
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByPrimaryKey(EvaluateDTO record);

    /**
     * 〈一句话功能简述〉<br>
     *根据合同编号查询工单
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int selectByContractCode(String contractCode);

    /**
     * 〈一句话功能简述〉<br>
     *查询一个月的内的工单
     *
     * @param [oneMouseBefore]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<EvaluateDTO> selectOneMouthOrder(@Param("oneMouseBefore")Timestamp oneMouseBefore);

    /**
     * 〈一句话功能简述〉<br>
     *查找所的工单
     *
     * @param
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<EvaluateDTO> findAll();

    /**
     * 〈一句话功能简述〉<br>
     *根据评价工单编号,状态,创建时间,更新时间查询工单
     *
     * @param [recdebugOrderCodeord,status,createTime,endTime]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<EvaluateDTO> selectByCodeOrStatusOrDate(@Param(value = "debugOrderCode") String debugOrderCode,
                                                 @Param(value = "status")Integer status,
                                                 @Param(value = "createTime")Timestamp createTime,
                                                 @Param(value = "endTime")Timestamp endTime);

    /**
     * 〈一句话功能简述〉<br>
     *查询相似的评价工单编号
     *
     * @param [code]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<String> searchDebugOrderCodeLike(String code);


}