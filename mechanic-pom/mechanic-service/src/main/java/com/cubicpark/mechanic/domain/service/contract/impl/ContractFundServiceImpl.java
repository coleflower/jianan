package com.cubicpark.mechanic.domain.service.contract.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.contract.IContractFundDAO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;
import com.cubicpark.mechanic.domain.ruleengine.engine.RuleEngine;
import com.cubicpark.mechanic.domain.service.contract.IContractFundService;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("contractFundService")
public class ContractFundServiceImpl implements IContractFundService {
    
    @Autowired
    private IContractFundDAO contractFundDAO;
    @Autowired
    private IContractService contractService;
    @Resource
    private RuleEngine addContractFundEngine;
    @Resource
    private RuleEngine modifyContractFundEngine;

    public void addContractFund(ContractFundCheckDTO contractFundCheck) throws Exception {
        addContractFundEngine.processRequest(contractFundCheck);
    }

    public void modifyContractFund(ContractFundCheckDTO contractFundCheck) throws Exception {
        modifyContractFundEngine.processRequest(contractFundCheck);
    }
    
    public String add(ContractFundDTO contractFund) {
        if (contractFund == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        contractFundDAO.addContractFund(contractFund);
        return CommonErrorCode.SUCCESS.getValue();
    }
    
    public String modify(ContractFundDTO contractFund) {
        if (contractFund == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        contractFundDAO.modifyContractFund(contractFund);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String delContractFund(int id) {
        ContractFundDTO currentContractFund = getContractFundByID(id);
        
        if (currentContractFund == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        String currentInfoState = currentContractFund.getInfoState();

        // 只有在非提交状态才能操作 且是逻辑删除
        if (Constants.COMMIT.equals(currentInfoState))
            return CommonErrorCode.DELCONDITIONISACCORD.getValue();
        
        contractFundDAO.delContractFundById(id);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public List<ContractFundDTO> getContractFundByContractCode(String contractCode) {
        if (StringUtils.isBlank(contractCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc()); 
            
        return contractFundDAO.getContractFundByContractCode(contractCode);
    }

    public ContractFundDTO getContractFundByID(int id) {
        if (id == 0)
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return contractFundDAO.getContractFundById(id);
    }

    public List<ContractFundDTO> getSaveInfoByContractCode(String contractCode) {
        if (StringUtils.isBlank(contractCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc()); 
        
        return contractFundDAO.getSaveContractFundByContractCode(contractCode);
    }

    public BigDecimal getContractSumPaidFund(String contractCode) {
        List<ContractFundDTO> fundList= getContractFundByContractCode(contractCode);// 获取合同下所有款项信息
        
        BigDecimal totalPay = new BigDecimal(0);
        for(ContractFundDTO fund : fundList) {
            // 只合计已提交的款项
            if (Constants.COMMIT.equals(fund.getInfoState())) {
                totalPay = totalPay.add(new BigDecimal(fund.getFund()));
            }
        }
        return totalPay;
    }

}
