package com.cubicpark.mechanic.domain.service.customer;

import java.util.List;

import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 客户综合查询服务类
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface ICustomerService {

    /**
     * 
     * 功能描述: <br>
     * 客户信息查询
     *
     * @param areaCode 所在区域 
     * @param customerName 客户名称
     * @param grade 客户等级
     * @param startDate 时间起
     * @param endDate 时间止
     * @param event 操作事件 普通查询/查询新增客户/查询联系客户
     * @param departmentCode 所在部门
     * @param employeeCode 雇员编号
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerInfoDTO> queryCustomer(String areaCode, String customerName, String grade, String startDate,
            String endDate, String event, String departmentCode, String employeeCode, String currentEmployeeCode, int page);
    
    int queryCustomerTotal(String areaCode, String customerName, String grade, String startDate,
            String endDate, String event, String departmentCode, String employeeCode, String currentEmployeeCode);
    
    /**
     * 
     * 功能描述: <br>
     * 获取指定员工的客户信息
     *
     * @param employeeCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerInfoDTO> getCustomerByEmployeeCode(String employeeCode);
    
    /**
     * 
     * 功能描述: <br>
     * 分页查询客户公海信息
     *
     * @param startDate
     * @param endDate
     * @param page
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<CustomerInfoDTO> queryCustomerSea(String startDate, String endDate, int page);
    
    int queryCustomerSeaTotal(String startDate, String endDate);

    /**
     * 〈一句话功能简述〉<br>
     * 根据条件查询客户
     *
     * @param customerName, employeeCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<CustomerInfoDTO> selectCustomer(String customerName,String employeeCode);
}
