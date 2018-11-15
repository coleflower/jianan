package com.cubicpark.mechanic.dao.contract;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractQueryDTO;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 合同DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IContractDAO {

    /**
     * 
     * 功能描述: <br>
     * 新增合同
     *
     * @param contract
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int addContract(ContractDTO contract);

    /**
     * 
     * 功能描述: <br>
     * 修改合同
     *
     * @param contract
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyContract(ContractDTO contract);

    /**
     * 
     * 功能描述: <br>
     * 查询合同信息列表
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<ContractDTO> queryContract(ContractQueryDTO query);

    /**
     * 
     * 功能描述: <br>
     * 根据合同编号查询合同信息
     *
     * @param contractCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ContractDTO getContractByContractCode(String contractCode);

    /**
     * 
     * 功能描述: <br>
     * 设置合同状态
     *
     * @param contractCode
     * @param state
     * @param currentState
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int setContractState(@Param("contractCode") String contractCode, @Param("state") String state,
            @Param("currentState") String currentState);

    /**
     * 
     * 功能描述: <br>
     * 设置款项状态
     *
     * @param contractCode
     * @param state
     * @param currentState
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int setFundState(@Param("contractCode") String contractCode, @Param("state") String state,
            @Param("currentState") String currentState, @Param("settleDate") String settleDate);

    /**
     * 
     * 功能描述: <br>
     * 根据项目编号和合同状态获取对应的合同信息
     *
     * @param projectCode
     * @param state
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<ContractDTO> getContractByProjectAndContractState(@Param("projectCode") String projectCode,
            @Param("state") String state);
    
    /**
     * 
     * 功能描述: <br>
     * 根据合同备案号获取合同信息
     *
     * @param recordNumber
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ContractDTO getContractByRecordNumber(String recordNumber);

    /**
     * 〈一句话功能简述〉<br>
     * 根据员工编号查询属于他负责的合同编号
     *
     * @param employeeCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.contract.ContractDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    String[] selectContractCodeByEmployeeCode(String employeeCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据客户编号查询
     *
     * @param customerCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.contract.ContractDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ContractDTO> selectContractByCustomerCode(@Param(value = "customerCode") String customerCode);

    /**
     * 〈一句话功能简述〉<br> 
     * 
     *
     * @param contractCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.contract.ContractDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ContractDTO> getAllContractByContractCode(String contractCode);
    
    /**
     * 〈一句话功能简述〉<br> 
     * 查询所有的补发配件合同
     *
     * @param contractCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.contract.ContractDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ContractDTO> getReissueContractByContractCode(String contractCode);

    /**
     * 〈一句话功能简述〉<br>
     * 根据合同明名字模糊查询
     *
     * @param contractName
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.contract.ContractDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ContractDTO> selectContractByContractNameLike(@Param(value = "contractName") String contractName);
    
}
