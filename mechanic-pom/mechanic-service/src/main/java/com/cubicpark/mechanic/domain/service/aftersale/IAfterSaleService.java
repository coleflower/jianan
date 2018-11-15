package com.cubicpark.mechanic.domain.service.aftersale;

import com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs;

import java.sql.Timestamp;
import java.util.List;
/**
 * 〈一句话功能简述〉<br> 
 * 售后服务
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IAfterSaleService {


    /**
     * 〈一句话功能简述〉<br> 
     * //生成新的售后服务工单
     *
     * @param [afterService]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int create(AfterSaleDTOWithBLOBs afterService);


    /**
     * 〈一句话功能简述〉<br> 
     * //查询所的售后工单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<AfterSaleDTOWithBLOBs> findAll();


    /**
     * 〈一句话功能简述〉<br> 
     * //查新30天内的工单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<AfterSaleDTOWithBLOBs> findOneMouseDebugOrder();

    /**
     * 〈一句话功能简述〉<br> 
     * 
     *
     * @param [id]
     * @return com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    AfterSaleDTOWithBLOBs findById(Integer id);


    /**
     * 〈一句话功能简述〉<br> 
     * //修改工单
     *
     * @param [afterServiceDTOWithBLOBs]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int update(AfterSaleDTOWithBLOBs afterServiceDTOWithBLOBs);


    /**
     * 〈一句话功能简述〉<br> 
     * //员工选择工单处理
     *
     * @param [employeeCode, id]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int choiceDebugOrder(String employeeCode, Integer id);


    /**
     * 〈一句话功能简述〉<br> 
     * //删除工单
     *
     * @param [id]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int deleteById(Integer id);


    /**
     * 〈一句话功能简述〉<br> 
     * 根据条件查询工单
     *
     * @param [afterServiceCode, status, createTime, endTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<AfterSaleDTOWithBLOBs> selectByCodeOrStatusOrDate(String afterServiceCode, Integer status, Timestamp createTime, Timestamp endTime);

    /**
     * 根据输入的值查询相似的expressCode
     * @param code input框中输入的值
     * @return
     */
    List<String> searchCodeLike(String code);
}
