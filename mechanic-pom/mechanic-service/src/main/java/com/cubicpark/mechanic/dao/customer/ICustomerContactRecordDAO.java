package com.cubicpark.mechanic.dao.customer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cubicpark.mechanic.domain.dto.customer.CustomerContactRecordDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户联系记录DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerContactRecordDAO {
    
    /**
     * 
     * 功能描述: <br>
     * 新增联系记录
     *
     * @param contactRecord
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int addContactRecord(CustomerContactRecordDTO contactRecord);
    
    /**
     * 
     * 功能描述: <br>
     * 修改联系记录
     *
     * @param contactRecord
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyContactRecord(CustomerContactRecordDTO contactRecord);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号和联系人编号获取联系记录列表
     *
     * @param customerCode
     * @param contactsCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerContactRecordDTO> getContactRecordByCustomerCodeAndContactsCode(@Param("customerCode") String customerCode, @Param("contactsCode") String contactsCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据ID获取联系记录信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerContactRecordDTO getContactRecordById(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 删除未提交的联系记录信息
     *
     * @param id
     * @param currentInfoState
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int delCustomerContactRecordById(int id);
}
