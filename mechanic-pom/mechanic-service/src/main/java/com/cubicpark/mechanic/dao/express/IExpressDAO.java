package com.cubicpark.mechanic.dao.express;


import com.cubicpark.mechanic.domain.dto.express.ExpressDTO;
import org.apache.ibatis.annotations.Param;

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
public interface IExpressDAO {

    /**
     * 〈一句话功能简述〉<br>
     * 插入新的工单
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insertSelective(ExpressDTO record);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id查询工单
     *
     * @param [id]
     * @return com.cubicpark.mechanic.domain.dto.express.ExpressDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    ExpressDTO selectByPrimaryKey(Integer id);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id更新非空的值
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByPrimaryKeySelective(ExpressDTO record);

    /**
     * 〈一句话功能简述〉<br>
     * 获取所有的物流工单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.express.ExpressDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ExpressDTO> getAll();

    //查找一个月内的工单
    /**
     * 〈一句话功能简述〉<br>
     * 获取一个月内的物流工单
     *
     * @param [oneMouseBefore]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.express.ExpressDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ExpressDTO> findOneMouseAfterService(@Param("oneMouseBefore") Timestamp oneMouseBefore);

    //根据条件查询
    /**
     * 〈一句话功能简述〉<br>
     * 根据编号,状态,时间查询工单
     *
     * @param [expressCode, status, createTime, endTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.express.ExpressDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ExpressDTO> selectByCodeOrStatusOrDate(@Param(value = "expressCode") String expressCode,
                                                @Param(value = "status")Integer status,
                                                @Param(value = "createTime")Timestamp createTime,
                                                @Param(value = "endTime")Timestamp endTime);

    //根据输入的值查询相似的expressCode
    /**
     * 〈一句话功能简述〉<br>
     *查询相似的物流工单编号
     *
     * @param [code]
     * @return java.util.List<java.lang.String>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<String> searchExpressCodeLike(String code);

    //用户选择调试工单
    /**
     * 〈一句话功能简述〉<br>
     * 用户选择物流工单
     *
     * @param [employeeCode, id, version, now]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int choiceDebugOrder(@Param("employeeCode") String employeeCode,@Param("employeeName") String employeeName, @Param("id")Integer id,
                         @Param("version")int version,@Param("now")Timestamp now);
}