package com.cubicpark.mechanic.domain.service.customer.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.customer.ICustomerSummaryDAO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerSummaryDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerSummaryService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("customerSummaryService")
public class CustomerSummaryServiceImpl implements ICustomerSummaryService {

    @Autowired
    private ICustomerSummaryDAO customerSummaryDAO;
    
    public String addCustomerSummary(CustomerSummaryDTO customerSummary) {
        if (customerSummary == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerSummary.setIsReply(Constants.NOREPLY);
        
        customerSummaryDAO.addSummary(customerSummary);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyCustomerSummary(CustomerSummaryDTO customerSummary) {
        if (customerSummary == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerSummaryDAO.modifySummary(customerSummary);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public List<CustomerSummaryDTO> getCustomerSummarys(String customerCode) {
        if (StringUtils.isBlank(customerCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerSummaryDAO.getSummaryByCustomerCode(customerCode);
    }

    public CustomerSummaryDTO getCustomerSummary(int id) {
        if (id == 0)
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerSummaryDAO.getSummaryById(id);
    }

    public String customerSummary(int id, String employeeCode, String replyInfo) {
        if (id == 0 || StringUtils.isBlank(employeeCode) || StringUtils.isBlank(replyInfo))
            return CommonErrorCode.CONDITIONISNULL.getValue();
        
        customerSummaryDAO.replySummary(id, employeeCode, replyInfo, Constants.REPLY);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String delCustomerSummary(int id) {
        CustomerSummaryDTO summary = getCustomerSummary(id);
        
        String currentInfoState = summary.getInfoState();
        // 只有在非提交状态才能操作删除
        if (Constants.SAVE.equals(currentInfoState))
            return CommonErrorCode.DELCONDITIONISACCORD.getValue();
        
        customerSummaryDAO.delSummary(id);
        return CommonErrorCode.SUCCESS.getValue();
    }

}
