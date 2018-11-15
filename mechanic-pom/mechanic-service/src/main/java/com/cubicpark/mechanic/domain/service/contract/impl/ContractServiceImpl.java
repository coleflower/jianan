package com.cubicpark.mechanic.domain.service.contract.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.contract.IContractDAO;
import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractQueryDTO;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("contractService")
public class ContractServiceImpl implements IContractService {

    @Autowired
    private IContractDAO contractDAO;

    public String addContract(ContractDTO contract) {
        if (contract == null)
            return CommonErrorCode.OBJECTISNULL.getValue();

        contractDAO.addContract(contract);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyContract(ContractDTO contract) {
        if (contract == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        ContractDTO currentContract = getContractByCode(contract.getContractCode());
        
        // 当前合同状态在待生效的状态下才能修改
        if (!Constants.CONTRACT_STATE_TOBEEFFECTIVE.equals(currentContract.getContractState()))
            return CommonErrorCode.MODIFYCONDITIONISACCORD.getValue();

        contractDAO.modifyContract(contract);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyContractState(String code, String state) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(state))
            return CommonErrorCode.CONDITIONISNULL.getValue();

        ContractDTO currentContract = getContractByCode(code);
        String currentState = currentContract.getContractState();

        contractDAO.setContractState(code, state, currentState);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyContractFundState(String code, String state, String settleDate) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(state))
            return CommonErrorCode.CONDITIONISNULL.getValue();

        ContractDTO currentContract = getContractByCode(code);
        if (currentContract == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        String currentFundState = currentContract.getFundState();

        contractDAO.setFundState(code, state, currentFundState, settleDate);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public ContractDTO getContractByCode(String code) {
        if (StringUtils.isBlank(code))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());

        return contractDAO.getContractByContractCode(code);
    }

    public List<ContractDTO> queryContract(String contractName, String startDealDate, String endDealDate,
            String contractState) {

        /**if (StringUtils.isBlank(contractName)
                && (StringUtils.isBlank(String.valueOf(startDealDate)) || StringUtils.isBlank(String
                        .valueOf(endDealDate))) && StringUtils.isBlank(contractState))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());*/

        ContractQueryDTO contractQuery = buildContractQuery(contractName, startDealDate, endDealDate, contractState);

        return contractDAO.queryContract(contractQuery);
    }

    private ContractQueryDTO buildContractQuery(String contractName, String startDealDate, String endDealDate,
            String contractState) {
        ContractQueryDTO contractQuery = new ContractQueryDTO();
        contractQuery.setContractName(contractName);
        if (!StringUtils.isBlank(startDealDate)) {
            contractQuery.setStartDealDate(startDealDate+" 00:00:00");
        }
        if (!StringUtils.isBlank(endDealDate)) {
            contractQuery.setEndDealDate(endDealDate+" 23:59:59");
        }
        contractQuery.setContractState(contractState);

        return contractQuery;
    }

    public List<ContractDTO> getContractByProjectAndContractState(String projectCode, String state) {
        return contractDAO.getContractByProjectAndContractState(projectCode, state);
    }

    public ContractDTO getContractByRecordNumber(String recodeNumber) {
        return contractDAO.getContractByRecordNumber(recodeNumber);
    }

    @Override
    public List<ContractDTO> selectContractByCustomerCode(String customerCode) {
        return contractDAO.selectContractByCustomerCode(customerCode);
    }

    @Override
    public List<ContractDTO> getReissueContractByContractCode(String contractCode) {
        return contractDAO.getReissueContractByContractCode(contractCode);
    }

    @Override
    public List<ContractDTO> selectContractByContractNameLike(String contractNameLike) {
        return contractDAO.selectContractByContractNameLike(contractNameLike);
    }

}
