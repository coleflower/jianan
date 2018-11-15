package com.cubicpark.mechanic.domain.service.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerContactRecordDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 联系记录服务类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerContactRecordService {
    
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
    String addContactRecord(CustomerContactRecordDTO contactRecord);
    
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
    String modifyContactRecord(CustomerContactRecordDTO contactRecord);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号和联系人编号查询联系记录列表
     *
     * @param customerCode
     * @param contactsCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerContactRecordDTO> queryContactRecord(String customerCode, String contactsCode);
    
    /**
     * 
     * 功能描述: <br>
     * 获取联系记录详细信息
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
     * 删除非提交的联系记录
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String delContactRecord(int id);
}
