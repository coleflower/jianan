package com.cubicpark.mechanic.dao.aftersale;


import com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 *售后服务Dao
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IAfterSaleDAO {

   /**
    * 〈一句话功能简述〉<br>
    * 根据主键删除售后服务工单
    *
    * @param id
    * @return int
    * @see [相关类/方法]（可选）
    * @since [产品/模块版本] （可选）
    */
    int deleteByPrimaryKey(Integer id);

    /**
     * 〈一句话功能简述〉<br>
     * 插入新的售后服务工单
     *
     * @param record
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insertSelective(AfterSaleDTOWithBLOBs record);

    /**
     * 〈一句话功能简述〉<br>
     * 根据主键查询售后服务工单
     *
     * @param id
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    AfterSaleDTOWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id修改售后工单
     *
     * @param [record]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByPrimaryKeySelective(AfterSaleDTOWithBLOBs record);

    /**
     * 〈一句话功能简述〉<br>
     * 查找一个月内的工单
     *
     * @param [oneMouseBefore]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<AfterSaleDTOWithBLOBs> findOneMouseAfterService(@Param("oneMouseBefore") Timestamp oneMouseBefore);


    /**
     * 〈一句话功能简述〉<br>
     *  查询所有的工单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<AfterSaleDTOWithBLOBs> findAll();


    /**
     * 〈一句话功能简述〉<br>
     * 选择售后工单进行处理
     *
     * @param [employeeCode, id, now, version]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int choiceDebugOrder(@Param(value = "employeeCode") String employeeCode,@Param(value = "id") Integer id,
                         @Param(value = "now")Timestamp now,@Param(value = "version")int version);


    /**
     * 〈一句话功能简述〉<br>
     * 按照选择条件查询售后工单
     *
     * @param [afterServiceCode, status, createTime, endTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<AfterSaleDTOWithBLOBs> selectByCodeOrStatusOrDate(@Param(value = "afterServiceCode") String afterServiceCode,
                                                           @Param(value = "status")Integer status,
                                                           @Param(value = "createTime")Timestamp createTime,
                                                           @Param(value = "endTime")Timestamp endTime);

    /**
     * 〈一句话功能简述〉<br>
     * 根据输入的值查询相似的afterServiceCode
     *
     * @param [code]
     * @return java.util.List<java.lang.String>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<String> searchCodeLike(String code);



}