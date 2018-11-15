package com.cubicpark.mechanic.domain.service.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerSummaryDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 沟通心得服务类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerSummaryService {
    
    /**
     * 
     * 功能描述: <br>
     * 新增心得信息
     *
     * @param customerSummary
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String addCustomerSummary(CustomerSummaryDTO customerSummary);
    
    /**
     * 
     * 功能描述: <br>
     * 修改心得信息
     *
     * @param customerSummary
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String modifyCustomerSummary(CustomerSummaryDTO customerSummary);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号获取心得信息列表
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerSummaryDTO> getCustomerSummarys(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 心得详细信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerSummaryDTO getCustomerSummary(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 评价心得信息
     *
     * @param id
     * @param employeeCode
     * @param replyInfo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String customerSummary(int id, String employeeCode, String replyInfo);
    
    /**
     * 
     * 功能描述: <br>
     * 删除非提交的心得信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    String delCustomerSummary(int id);
}
