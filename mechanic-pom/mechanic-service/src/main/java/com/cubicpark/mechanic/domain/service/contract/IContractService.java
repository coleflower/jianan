package com.cubicpark.mechanic.domain.service.contract;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 合同服务
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IContractService {

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
    String addContract(ContractDTO contract);

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
    String modifyContract(ContractDTO contract);

    /**
     * 
     * 功能描述: <br>
     * 修改合同状态
     *
     * @param code
     * @param state
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String modifyContractState(String code, String state);

    /**
     * 
     * 功能描述: <br>
     * 修改合同款项状态
     *
     * @param code
     * @param state
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String modifyContractFundState(String code, String state, String settleDate);

    /**
     * 
     * 功能描述: <br>
     * 根据合同编码获取合同信息
     *
     * @param code
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ContractDTO getContractByCode(String code);
    
    /**
     * 
     * 功能描述: <br>
     * 根据项目和特定合同状态查询合同列表
     *
     * @param projectCode
     * @param state
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<ContractDTO> getContractByProjectAndContractState(String projectCode, String state);

    /**
     * 
     * 功能描述: <br>
     * 合同列表信息查询
     *
     * @param contractName
     * @param startDealDate
     * @param endDealDate
     * @param contractState
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<ContractDTO> queryContract(String contractName, String startDealDate, String endDealDate,
            String contractState);
    
    /**
     * 
     * 功能描述: <br>
     * 根据合同备案号查询合同信息
     *
     * @param recodeNumber
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ContractDTO getContractByRecordNumber(String recodeNumber);

    /**
     * 〈一句话功能简述〉<br>
     * 根据客户编号查询
     *
     * @param customerCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.contract.ContractDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ContractDTO> selectContractByCustomerCode(String customerCode);

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
     * @param contractNameLike
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.contract.ContractDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<ContractDTO> selectContractByContractNameLike(String contractNameLike);

}
