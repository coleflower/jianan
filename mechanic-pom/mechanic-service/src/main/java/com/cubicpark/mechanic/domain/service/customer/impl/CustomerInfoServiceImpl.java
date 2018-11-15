package com.cubicpark.mechanic.domain.service.customer.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.customer.ICustomerInfoDAO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("customerInfoService")
public class CustomerInfoServiceImpl implements ICustomerInfoService {
    
    @Autowired
    private ICustomerInfoDAO customerInfoDAO;

    public String addCustomerInfo(CustomerInfoDTO customerInfo) {
        if (customerInfo == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
            
        customerInfo.setCustomerType(Constants.CUSTOMER_TYPE_NORMAL);
        customerInfoDAO.addCustomerInfo(customerInfo);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyCustomerInfo(CustomerInfoDTO customerInfo) {
        if (customerInfo == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerInfoDAO.modifyCustomerInfo(customerInfo);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public CustomerInfoDTO getCustomerInfoByCustomerCode(String customerCode) {
        if (StringUtils.isBlank(customerCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerInfoDAO.getCustomerInfoByCustomerCode(customerCode);
    }
    
    public CustomerInfoDTO getCustomerInfoByCustomerName(String customerName) {
        Assert.notNull(customerName, "customerName must be not null");
        return customerInfoDAO.getCustomerInfoByCustomerName(customerName);
    }

    public CustomerInfoDTO getCustomerInfoByCustomerNameExceptSelf(String customerName, String customerCode) {
        Assert.notNull(customerName, "customerName must be not null");
        Assert.notNull(customerCode, "customerCode must be not null");
        return customerInfoDAO.getCustomerInfoByCustomerNameExceptSelf(customerName, customerCode);
    }

    public String delCustomerInfoByCustomerCode(String customerCode) {
        CustomerInfoDTO customerInfo = getCustomerInfoByCustomerCode(customerCode);
        
        String currentInfoState = customerInfo.getInfoState();
        // 只有在非提交状态才能操作删除
        if (Constants.COMMIT.equals(currentInfoState))
            return CommonErrorCode.DELCONDITIONISACCORD.getValue();
        
        customerInfoDAO.delCustomerInfo(customerCode);
        return CommonErrorCode.SUCCESS.getValue();
    }

    // TODO AOP history
    public String handleCustomer(String customerCode, String oldEmployeeCode, String newEmployeeCode) {
        Assert.notNull(newEmployeeCode, "newEmployeeCode must be not null");
        Assert.notNull(customerCode, "customerCode must be not null");
        Assert.notNull(oldEmployeeCode, "oldEmployeeCode must be not null");
        
        customerInfoDAO.handleCustomer(customerCode, oldEmployeeCode, newEmployeeCode);
        return CommonErrorCode.SUCCESS.getValue();
    }
    
}
