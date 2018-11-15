package com.cubicpark.mechanic.domain.service.customer;

import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户信息服务类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerInfoService {
    
    /**
     * 
     * 功能描述: <br>
     * 新增客户信息
     *
     * @param customerInfo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String addCustomerInfo(CustomerInfoDTO customerInfo);
    
    /**
     * 
     * 功能描述: <br>
     * 修改客户信息，当信息状态为提交时不能修改
     *
     * @param customerInfo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String modifyCustomerInfo(CustomerInfoDTO customerInfo);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号获取客户信息
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerInfoDTO getCustomerInfoByCustomerCode(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户名称查询客户信息
     *
     * @param customerName
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerInfoDTO getCustomerInfoByCustomerName(String customerName);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户名称查询除自身以外的其他客户信息
     *
     * @param customerName
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerInfoDTO getCustomerInfoByCustomerNameExceptSelf(String customerName, String customerCode);
    /**
     * 
     * 功能描述: <br>
     * 删除客户信息
     * 已提交的客户无法删除
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String delCustomerInfoByCustomerCode(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 把公海客户装成私有客户
     *
     * @param customerCode
     * @param oldEmployeeCode
     * @param newEmployeeCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String handleCustomer(String customerCode, String oldEmployeeCode, String newEmployeeCode);
}
