package com.cubicpark.mechanic.dao.customer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cubicpark.mechanic.domain.dto.customer.CustomerSummaryDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 沟通总结DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerSummaryDAO {
    
    /**
     * 
     * 功能描述: <br>
     * 新增沟通心得信息
     *
     * @param customerSummary
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int addSummary(CustomerSummaryDTO customerSummary);
    
    /**
     * 
     * 功能描述: <br>
     * 修改沟通心得信息
     *
     * @param customerSummary
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifySummary(CustomerSummaryDTO customerSummary);
    
    /**
     * 
     * 功能描述: <br>
     * 根据客户编号获取沟通心得信息列表
     *
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerSummaryDTO> getSummaryByCustomerCode(String customerCode);
    
    /**
     * 
     * 功能描述: <br>
     * 根据ID获取沟通心得信息
     *
     * @param id
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    CustomerSummaryDTO getSummaryById(int id);
    
    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param id
     * @param employeeCode
     * @param replyInfo
     * @param isreply
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int replySummary(@Param("id") int id, @Param("employeeCode") String employeeCode, @Param("replyInfo") String replyInfo, @Param("isreply") String isreply);
    
    /**
     * 
     * 功能描述: <br>
     * 删除未提交的心得
     *
     * @param id
     * @param currentInfoState
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int delSummary(int id);
}
