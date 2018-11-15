package com.cubicpark.mechanic.domain.service.bill;

import com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 开票服务
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IFinanceBillService {

    /**
     * 根据员工编号查找合同
     * @param EmployeeCode
     * @return
     */
    String[] selectContractByEmployeeCode(String EmployeeCode);

    /**
     * 插入
     * @param financeBillDTO
     * @return
     */
    int insert(FinanceBillDTO financeBillDTO);

    List<FinanceBillDTO> selectAll();

    /**
     * 根据合同编号查询
     * @param contractCode
     * @return
     */
    List<FinanceBillDTO> selectByContractCode(String contractCode);

    /**
     * 查找未审核的的
     * @return
     */
    List<FinanceBillDTO> selectUnprocessed();

    FinanceBillDTO selectById(Integer id);

    int updateById(FinanceBillDTO financeBillDTO);

    /**
     * 查找一个月内的
     * @param proposer
     * @return
     */
    List<FinanceBillDTO> selectInOneMouth(String proposer);

    /**
     * 根据申请人查找
     * @param proposer
     * @return
     */
    List<FinanceBillDTO> findByProposer(String proposer);

    List<String> searchCodeLike(String code);

    /**
     * 〈一句话功能简述〉<br>
     * 根据条件查询工单
     *
     * @param code, status, createTime, endTime
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectByCodeOrStatusOrDate(String code, Integer status, Timestamp createTime, Timestamp endTime);

    /**
     * 〈一句话功能简述〉<br>
     * 根据合同编号和状态查找
     *
     * @param contractCode, status
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectByContractCodeAndStatus(String contractCode,String status);

    /**
     * 〈一句话功能简述〉<br>
     * 根据条件查询
     *
     * @param  employeeCode, status, startTime, endTime
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<FinanceBillDTO> selectByDepartmentCodeOrEmployeeCodeOrStatusOrDate(String employeeCode, String status,
                                                                            Timestamp startTime, Timestamp endTime);
}
