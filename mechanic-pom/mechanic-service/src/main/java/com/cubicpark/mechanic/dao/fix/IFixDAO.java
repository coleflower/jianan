package com.cubicpark.mechanic.dao.fix;

import com.cubicpark.mechanic.domain.dto.fix.FixDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;


public interface IFixDAO {
    /**
     * 〈一句话功能简述〉<br>
     * 插入新的调试工单
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insertSelective(FixDTO record);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id查询调试工单
     *
     * @param [id]
     * @return com.cubicpark.mechanic.domain.dto.fix.FixDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    FixDTO selectByPrimaryKey(Integer id);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id更新调试工单
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByPrimaryKeySelective(FixDTO record);


    /**
     * 〈一句话功能简述〉<br>
     * 根据合同编号查询调试工单
     *
     * @param [contractCode]
     * @return com.cubicpark.mechanic.domain.dto.fix.FixDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    FixDTO selectByContractCode(@Param("contractCode") String contractCode);


    /**
     * 〈一句话功能简述〉<br>
     * 查询一个月内工单
     *
     * @param [oneMouseBefore]
     * @return com.cubicpark.mechanic.domain.dto.fix.FixDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FixDTO> findOneMouseDebugOrder(@Param("oneMouseBefore")Timestamp oneMouseBefore);


    /**
     * 〈一句话功能简述〉<br>
     * //查询所有的工单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.fix.FixDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FixDTO> findAll();


    /**
     * 〈一句话功能简述〉<br>
     *  //用户选择调试工单
     *
     * @param [employeeCode, id, version, now]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int choiceDebugOrder(@Param("employeeCode") String employeeCode, @Param("id")Integer id,
                         @Param("version")int version,@Param("now")Timestamp now);


    /**
     * 〈一句话功能简述〉<br>
     * //根据条件查询
     *
     * @param [debugOrderCode, status, createTime, endTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.fix.FixDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FixDTO> selectByCodeOrStatusOrDate(@Param(value = "debugOrderCode") String debugOrderCode,
                                            @Param(value = "status")Integer status,
                                            @Param(value = "createTime")Timestamp createTime,
                                            @Param(value = "endTime")Timestamp endTime);


    /**
     * 〈一句话功能简述〉<br>
     * //根据输入的值查询相似的expressCode
     *
     * @param [code]
     * @return java.util.List<java.lang.String>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<String> searchDebugOrderCodeLike(String code);

    /**
     * 〈一句话功能简述〉<br>
     * 查询需要添加调试时间的调试工单
     *
     * @param employeeCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.fix.FixDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FixDTO> selectByEmployeeCodeAndFixTime(String employeeCode);
}