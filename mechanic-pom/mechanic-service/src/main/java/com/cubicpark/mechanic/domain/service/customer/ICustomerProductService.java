package com.cubicpark.mechanic.domain.service.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerProductDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户已有产品服务类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerProductService {
    
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
    String addCustomerProduct(CustomerProductDTO customerProduct);
    
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
    String modifyCustomerProduct(CustomerProductDTO customerProduct);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号获取客户产品信息列表
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerProductDTO> getCustomerProducts(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 客户产品详细信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerProductDTO getCustomerProduct(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 删除非提交状态的客户产品信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String delCustomerProduct(int id);

}
