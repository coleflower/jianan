package com.cubicpark.mechanic.dao.customer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cubicpark.mechanic.domain.dto.customer.CustomerDemandDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 客户需求DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerDemandDAO {
    
    /**
     * 
     * 功能描述: <br>
     * 新增客户需求信息
     *
     * @param customerDemand
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int addCustomerDemand(CustomerDemandDTO customerDemand);
    
    /**
     * 
     * 功能描述: <br>
     * 修改客户需求信息
     *
     * @param customerDemand
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyCustomerDemand(CustomerDemandDTO customerDemand);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号获取客户需求列表
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerDemandDTO> getCustomerDemandByCustomerCode(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据ID获取客户需求信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerDemandDTO getCustomerDemandById(int id);

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param id
     * @param newState
     * @param currentState
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int setDemandState(@Param("id") int id, @Param("newState") String newState, @Param("currentState") String currentState);
    
    /**
     * 
     * 功能描述: <br>
     * 删除未提交的客户需求信息
     *
     * @param id
     * @param currentInfoState
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int delCustomerDemand(int id);

}
