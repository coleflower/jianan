package com.cubicpark.mechanic.dao.contract;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 合同款项DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IContractFundDAO {
    
    /**
     * 
     * 功能描述: <br>
     * 新增合同款项
     *
     * @param contractFund
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int addContractFund(ContractFundDTO contractFund);
    
    /**
     * 
     * 功能描述: <br>
     * 修改合同款项
     *
     * @param contractFund
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyContractFund(ContractFundDTO contractFund);
    
    /**
     * 
     * 功能描述: <br>
     * 删除未提交的合同款项
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int delContractFundById(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 根据合同编号获取合同款项列表
     *
     * @param contractCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<ContractFundDTO> getContractFundByContractCode(String contractCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据ID获取合同款项信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    ContractFundDTO getContractFundById(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 根据合同编码获取信息状态为保存的信息
     *
     * @param contractCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<ContractFundDTO> getSaveContractFundByContractCode(String contractCode);
}
