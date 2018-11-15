package com.cubicpark.mechanic.domain.service.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerDemandDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户需求服务类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerDemandService {
    
    /**
     * 
     * 功能描述: <br>
     * 新增客户需求
     *
     * @param customerDemand
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String addCustomerDemand(CustomerDemandDTO customerDemand);
    
    /**
     * 
     * 功能描述: <br>
     * 修改客户需求
     *
     * @param customerDemand
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String modifyCustomerDemand(CustomerDemandDTO customerDemand);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号获取客户需求信息列表
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerDemandDTO> getCustomerDemands(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 需求详细内容
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerDemandDTO getCustomerDemand(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 修改需求状态
     *
     * @param id
     * @param state
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String modifyCustomerDemandState(int id, String state);
    
    /**
     * 
     * 功能描述: <br>
     * 删除非提交状态的需求
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String delCustomerDemand(int id);
}
