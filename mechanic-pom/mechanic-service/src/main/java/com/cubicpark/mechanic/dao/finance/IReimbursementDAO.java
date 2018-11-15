/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: IReimbursementDAO.java
 * Author:   first.ji
 * Date:     2018年9月7日 下午3:31:54
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.dao.finance;

import com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IReimbursementDAO {


    /**
     * 〈一句话功能简述〉<br>
     * //查找所有的报销单
     *
     * @param []
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ReimbursementDTO> findAll();


    /**
     * 〈一句话功能简述〉<br>
     * //根据id查找
     *
     * @param [id]
     * @return com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    ReimbursementDTO findById(Integer id);


    /**
     * 〈一句话功能简述〉<br>
     * //根据applyCode查找
     *
     * @param [applyCode]
     * @return com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    ReimbursementDTO selectByApplyNo(String applyCode);


    /**
     * 〈一句话功能简述〉<br>
     *  //根据ID改变金额
     *
     * @param [cost, id]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateCostById(@Param(value = "cost") String cost, @Param(value = "id")Integer id);


    /**
     * 〈一句话功能简述〉<br>
     *  //报销成功
     *
     * @param [id, state, payway, voucher, payTime, remarks, employeeCode]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateById(@Param("id") Integer id,
                   @Param("state")String state,
                   @Param("payway") String payway,
                   @Param("voucher")String voucher,
                   @Param("payTime")Date payTime,
                   @Param("remarks")String remarks,
                   @Param("employeeCode")String employeeCode);


    /**
     * 〈一句话功能简述〉<br>
     *   //报销失败
     *
     * @param [id, state, remarks, employeeCode]
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int updateByIdRefuse(@Param("id") Integer id,
                         @Param("state")String state,
                         @Param("remarks")String remarks,
                         @Param("employeeCode")String employeeCode);


    /**
     * 〈一句话功能简述〉<br>
     *  //根据code,状态,创建时间,更新时间查询
     *
     * @param [code, status, createTime, endTime]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ReimbursementDTO> selectByCodeOrStatusOrDate(@Param(value = "code") String code,
                                                   @Param(value = "status")String status,
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
    List<String> searchExpressCodeLike(String code);


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
}
