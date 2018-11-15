package com.cubicpark.mechanic.dao.customer;

import org.apache.ibatis.annotations.Param;

import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 客户基本信息DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerInfoDAO {

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
    int addCustomerInfo(CustomerInfoDTO customerInfo);

    /**
     * 
     * 功能描述: <br>
     * 修改客户信息
     *
     * @param customerInfo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyCustomerInfo(CustomerInfoDTO customerInfo);

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
    CustomerInfoDTO getCustomerInfoByCustomerNameExceptSelf(@Param("customerName") String customerName,
            @Param("customerCode") String customerCode);

    /**
     * 
     * 功能描述: <br>
     * 删除未提交的客户信息
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int delCustomerInfo(String customerCode);

    /**
     * 
     * 功能描述: <br>
     * 处理公海客户，将公海客户变更成自己的客户
     *
     * @param customerCode
     * @param oldEmployeeCode
     * @param newEmployeeCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int handleCustomer(@Param("customerCode") String customerCode, @Param("oldEmployeeCode") String oldEmployeeCode,
            @Param("newEmployeeCode") String newEmployeeCode);
}
