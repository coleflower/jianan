package com.cubicpark.mechanic.domain.service.customer.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.customer.ICustomerDemandDAO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerDemandDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerDemandService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("customerDemandService")
public class CustomerDemandServiceImpl implements ICustomerDemandService {

    @Autowired
    private ICustomerDemandDAO customerDemandDAO;
    
    public String addCustomerDemand(CustomerDemandDTO customerDemand) {
        if (customerDemand == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerDemandDAO.addCustomerDemand(customerDemand);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyCustomerDemand(CustomerDemandDTO customerDemand) {
        if (customerDemand == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerDemandDAO.modifyCustomerDemand(customerDemand);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public List<CustomerDemandDTO> getCustomerDemands(String customerCode) {
        if (StringUtils.isBlank(customerCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerDemandDAO.getCustomerDemandByCustomerCode(customerCode);
    }

    public CustomerDemandDTO getCustomerDemand(int id) {
        if (id == 0)
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerDemandDAO.getCustomerDemandById(id);
    }

    public String modifyCustomerDemandState(int id, String state) {
        if (StringUtils.isBlank(state))
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        CustomerDemandDTO demand = getCustomerDemand(id);
        String currentState = demand.getDemandState();
        
        customerDemandDAO.setDemandState(id, state, currentState);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String delCustomerDemand(int id) {
        CustomerDemandDTO demand = getCustomerDemand(id);
        String currentInfoState = demand.getInfoState();
        // 只有在非提交状态才能操作删除
        if (Constants.COMMIT.equals(currentInfoState))
            return CommonErrorCode.DELCONDITIONISACCORD.getValue();
        
        customerDemandDAO.delCustomerDemand(id);
        return CommonErrorCode.SUCCESS.getValue();
    }

}
