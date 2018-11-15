package com.cubicpark.mechanic.domain.service.employee;

import java.sql.Timestamp;
import java.util.List;

import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;

/**
 * 
 * 〈一句话功能简述〉<br> 
 * 员工服务
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IEmployeeService {

    /**
     * 
     * 功能描述: <br>
     * 根据员工编号获取员工信息
     *
     * @param employeeCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public EmployeeDTO getEmployeeByCode(String employeeCode);

    /**
     * 
     * 功能描述: <br>
     * 员工登陆
     *
     * @param employeeCode
     * @param passWords
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public EmployeeDTO login(String employeeCode, String passWords);

    /**
     * 
     * 功能描述: <br>
     * 查询员工信息
     *
     * @param employeeCode 工号
     * @param name 姓名
     * @param accountStatus 帐号状态
     * @param departmentCode 部门编号
     * @param startEntryDate 入职日期开始时间
     * @param endEntryDate 入职日期结束时间
     * @param startLeavedate 离职日期开始时间
     * @param endLeavedate 离职日期结束时间
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public List<EmployeeDTO> queryEmployee(String employeeCode, String name, String departmentCode, String accountStatus,
            Timestamp startEntryDate, Timestamp endEntryDate);

    /**
     * 
     * 功能描述: <br>
     * 新增员工信息
     *
     * @param employee
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String addEmployeeBaseInfo(EmployeeDTO employee);

    /**
     * 
     * 功能描述: <br>
     * 修改员工信息
     *
     * @param employee
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String modifyEmployeeBaseInfo(EmployeeDTO employee);

    /**
     * 
     * 功能描述: <br>
     * 新增员工 角色信息
     *
     * @param employeeCode
     * @param roleCode
     * @param permission
     * @param salesAreas
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String addEmployeeRoleInfo(String employeeCode, String roleCode, String permission, String salesAreas);

    /**
     * 
     * 功能描述: <br>
     * 修改员工帐号状态
     *
     * @param employeeCode
     * @param status
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String modifyEmployeeStatus(String employeeCode, String status);

    /**
     * 
     * 功能描述: <br>
     * 员工离职处理
     *
     * @param employeeCode 离职员工工号
     * @param handoverPerson 交接人工号
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String enabledEmployeeQuit(String employeeCode, String handoverPerson, Timestamp levelDate) throws Exception;

    /**
     * 
     * 功能描述: <br>
     * 修改密码
     *
     * @param employeeCode
     * @param oldPassWords
     * @param newPassWords
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String modifyPassWords(String employeeCode, String oldPassWords, String newPassWords);
    
    /**
     * 
     * 功能描述: <br>
     * 获取部门所以员工
     *
     * @param departmentCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public List<EmployeeDTO> getEmployeeByDepartmentCode(String departmentCode);
    
    /**
     * 
     * 功能描述: <br>
     * 重置密码
     *
     * @param employeeCode
     * @param newPassWords
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public String resetPassword(String employeeCode, String newPassWords);

    /**
     * 〈一句话功能简述〉<br>
     * 根据部门编号查询
     *
     * @param departmentCode
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    List<EmployeeDTO> selectByDepartmentCode(String departmentCode);
}
