package com.cubicpark.mechanic.domain.service.customer.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.customer.ICustomerContactRecordDAO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerContactRecordDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerContactRecordService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("customerContactRecordService")
public class CustomerContactRecordServiceImpl implements ICustomerContactRecordService {

    @Autowired
    private ICustomerContactRecordDAO customerContactRecordDAO;
    
    public String addContactRecord(CustomerContactRecordDTO contactRecord) {
        if (contactRecord == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerContactRecordDAO.addContactRecord(contactRecord);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyContactRecord(CustomerContactRecordDTO contactRecord) {
        if (contactRecord == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerContactRecordDAO.modifyContactRecord(contactRecord);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public List<CustomerContactRecordDTO> queryContactRecord(String customerCode, String contactsCode) {
        if (StringUtils.isBlank(customerCode) || StringUtils.isBlank(contactsCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerContactRecordDAO.getContactRecordByCustomerCodeAndContactsCode(customerCode, contactsCode);
    }

    public CustomerContactRecordDTO getContactRecordById(int id) {
        if (id == 0)
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerContactRecordDAO.getContactRecordById(id);
    }

    public String delContactRecord(int id) {
        
        CustomerContactRecordDTO record = getContactRecordById(id);
        String currentInfoState = record.getInfoState();
        // 只有在非提交状态才能操作删除
        if (Constants.COMMIT.equals(currentInfoState))
            return CommonErrorCode.DELCONDITIONISACCORD.getValue();
        
        customerContactRecordDAO.delCustomerContactRecordById(id);
        return CommonErrorCode.SUCCESS.getValue();
    }

}
