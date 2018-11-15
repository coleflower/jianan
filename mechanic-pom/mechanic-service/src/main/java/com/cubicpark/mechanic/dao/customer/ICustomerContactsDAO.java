package com.cubicpark.mechanic.dao.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerContactsDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户联系人DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerContactsDAO {
    
    /**
     * 
     * 功能描述: <br>
     * 新增联系人
     *
     * @param customerContact
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int addCustomerContacts(CustomerContactsDTO customerContact);
    
    /**
     * 
     * 功能描述: <br>
     * 修改联系人
     *
     * @param customerContact
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyCustomerContacts(CustomerContactsDTO customerContact);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号获取联系人列表
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerContactsDTO> getCustomerContactsByCustomerCode(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据联系人编号获取联系人
     *
     * @param contactsCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerContactsDTO getCustomerContactsByContactsCode(String contactsCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据ID获取联系人信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerContactsDTO getCustomerContactsById(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 删除未提交的联系人信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int delCustomerContactsById(int id);
}
