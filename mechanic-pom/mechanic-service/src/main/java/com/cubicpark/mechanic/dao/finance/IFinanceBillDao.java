package com.cubicpark.mechanic.dao.finance;

import com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 开票单
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IFinanceBillDao {

    /**
     * 〈一句话功能简述〉<br>
     * 插入新的开票单
     *
     * @param financeBillDTO
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    int insert(FinanceBillDTO financeBillDTO);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id更新开票单
     *
     * @param financeBillDTO
     * @return int
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）hjgh
     */
    int updateById(FinanceBillDTO financeBillDTO);

    /**
     * 〈一句话功能简述〉<br>
     * 根据合同编号查询开票单
     *
     * @param contractCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectByContractCode(String contractCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据id查询开票单
     *
     * @param id
     * @return com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    FinanceBillDTO selectById(Integer id);

    /**
     * 〈一句话功能简述〉<br>
     * 查找所有的发票
     *
     * @param
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectAll();

    /**
     * 〈一句话功能简述〉<br>
     * 查找未处理的开票单
     *
     * @param
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectUnprocessed();

    /**
     * 〈一句话功能简述〉<br>
     * 查找一个月内的
     *
     * @param oneMouthBefore, proposer
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> findInOneMouth(@Param(value = "oneMouthBefore") Timestamp oneMouthBefore,
                                        @Param(value = "proposer")String proposer);

    /**
     * 根据申请人查找
     * @param proposer
     * @return
     */
    List<FinanceBillDTO> findByProposer(@Param(value = "proposer")String proposer);

    List<String> searchCodeLike(String code);

    /**
     * 〈一句话功能简述〉<br>
     * 按照选择条件查询售后工单
     *
     * @param code, status, createTime, endTime
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectByCodeOrStatusOrDate(@Param(value = "code") String code,
                                                           @Param(value = "status")Integer status,
                                                           @Param(value = "createTime")Timestamp createTime,
                                                           @Param(value = "endTime")Timestamp endTime);
    /**
     * 〈一句话功能简述〉<br>
     * 根据合同编号和状态查找
     *
     * @param contractCode, status
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectByContractCodeAndStatus(@Param(value = "contractCode") String contractCode,
                                                       @Param(value = "status") String status);


    /**
     * 〈一句话功能简述〉<br>
     * 根据条件查询
     *
     * @param  employeeCode, status, startTime, endTime
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectByDepartmentCodeOrEmployeeCodeOrStatusOrDate(@Param("employeeCode")String employeeCode, @Param("status")String status,
                                                                            @Param("startTime")Timestamp startTime,@Param("endTime")Timestamp endTime);

}
