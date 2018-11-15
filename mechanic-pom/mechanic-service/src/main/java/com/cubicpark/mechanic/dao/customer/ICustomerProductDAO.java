package com.cubicpark.mechanic.dao.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerProductDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户已有产品DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerProductDAO {
    
    /**
     * 
     * 功能描述: <br>
     * 新增客户产品信息
     *
     * @param customerProduct
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int addCustomerProduct(CustomerProductDTO customerProduct);
    
    /**
     * 
     * 功能描述: <br>
     * 修改客户产品信息
     *
     * @param customerProduct
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyCustomerProduct(CustomerProductDTO customerProduct);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号查询客户产品列表
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerProductDTO> getCustomerProductByCustomerCode(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据ID获取客户产品信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerProductDTO getCustomerProductById(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 删除未提交的客户产品信息
     *
     * @param id
     * @param currentInfoState
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int delCustomerProduct(int id);

}
