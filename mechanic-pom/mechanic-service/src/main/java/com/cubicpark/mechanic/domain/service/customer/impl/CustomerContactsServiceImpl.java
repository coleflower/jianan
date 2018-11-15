package com.cubicpark.mechanic.domain.service.customer.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.customer.ICustomerContactsDAO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerContactsDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerContactsService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("customerContactsService")
public class CustomerContactsServiceImpl implements ICustomerContactsService {
    
    @Autowired
    private ICustomerContactsDAO customerContactsDAO;

    public String addCustomerContacts(CustomerContactsDTO customerContacts) {
        if (customerContacts == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerContactsDAO.addCustomerContacts(customerContacts);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyCustomerContacts(CustomerContactsDTO customerContacts) {
        if (customerContacts == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerContactsDAO.modifyCustomerContacts(customerContacts);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public List<CustomerContactsDTO> getCustomerContacts(String customerCode) {
        if (StringUtils.isBlank(customerCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerContactsDAO.getCustomerContactsByCustomerCode(customerCode);
    }

    public CustomerContactsDTO getContacts(String contactsCode) {
        if (StringUtils.isBlank(contactsCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerContactsDAO.getCustomerContactsByContactsCode(contactsCode);
    }

    public String delCustomerContacts(int id) {
        if (id == 0)
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        CustomerContactsDTO contacts = customerContactsDAO.getCustomerContactsById(id);
        
        if (contacts == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        String currentInfoState = contacts.getInfoState();
        // 只有在非提交状态才能操作删除
        if (Constants.COMMIT.equals(currentInfoState))
            return CommonErrorCode.DELCONDITIONISACCORD.getValue();
        
        customerContactsDAO.delCustomerContactsById(id);
        return CommonErrorCode.SUCCESS.getValue();
    }

}
