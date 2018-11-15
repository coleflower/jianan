package com.cubicpark.mechanic.domain.service.customer.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.dao.customer.ICustomerProductDAO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerProductDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerProductService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("customerProductService")
public class CustomerProductServiceImpl implements ICustomerProductService {

    @Autowired
    private ICustomerProductDAO customerProductDAO;
    
    public String addCustomerProduct(CustomerProductDTO customerProduct) {
        if (customerProduct == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerProductDAO.addCustomerProduct(customerProduct);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyCustomerProduct(CustomerProductDTO customerProduct) {
        if (customerProduct == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        customerProductDAO.modifyCustomerProduct(customerProduct);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public List<CustomerProductDTO> getCustomerProducts(String customerCode) {
        if (StringUtils.isBlank(customerCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerProductDAO.getCustomerProductByCustomerCode(customerCode);
    }

    public CustomerProductDTO getCustomerProduct(int id) {
        if (id == 0)
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
        
        return customerProductDAO.getCustomerProductById(id);
    }

    public String delCustomerProduct(int id) {
        CustomerProductDTO customerProduct = getCustomerProduct(id);
        String currentInfoState = customerProduct.getInfoState();
        // 只有在非提交状态才能操作删除
        if (Constants.COMMIT.equals(currentInfoState))
            return CommonErrorCode.DELCONDITIONISACCORD.getValue();
        
        customerProductDAO.delCustomerProduct(id);
        return CommonErrorCode.SUCCESS.getValue();
    }

}
