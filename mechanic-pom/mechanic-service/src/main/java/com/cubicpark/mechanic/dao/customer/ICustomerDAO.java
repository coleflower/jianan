package com.cubicpark.mechanic.dao.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerQueryDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerSeaQueryDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户主DAO,主要用于查询客户信息
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerDAO {
    
    /**
     * 
     * 功能描述: <br>
     * 客户信息查询
     * 根据各种条件查询客户信息列表
     *
     * @param query
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerInfoDTO> queryCustomer(CustomerQueryDTO query);
    
    int queryCustomerTotal(CustomerQueryDTO query);
    
    List<CustomerInfoDTO> queryCustomerSea(CustomerSeaQueryDTO query);
    
    int queryCustomerSeaTotal(CustomerSeaQueryDTO query);

    /**
     * 〈一句话功能简述〉<br>
     * 根据条件查询客户
     *
     * @param customerName, employeeCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<CustomerInfoDTO> selectCustomer(@Param(value = "customerName") String customerName, @Param(value = "employeeCode")String employeeCode);
}
