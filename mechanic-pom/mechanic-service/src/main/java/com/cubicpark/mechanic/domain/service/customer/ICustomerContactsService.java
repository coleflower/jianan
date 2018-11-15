package com.cubicpark.mechanic.domain.service.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerContactsDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户联系人服务类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerContactsService {
    
    /**
     * 
     * 功能描述: <br>
     * 新增客户联系人
     *
     * @param customerContacts
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String addCustomerContacts(CustomerContactsDTO customerContacts);
    
    /**
     * 
     * 功能描述: <br>
     * 修改客户联系人
     *
     * @param customerContacts
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String modifyCustomerContacts(CustomerContactsDTO customerContacts);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号获取客户信息列表
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerContactsDTO> getCustomerContacts(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据联系人编码获取联系人信息
     *
     * @param contactsCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerContactsDTO getContacts(String contactsCode);
    
    /**
     * 
     * 功能描述: <br>
     * 非提交时删除联系人信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String delCustomerContacts(int id);
}
