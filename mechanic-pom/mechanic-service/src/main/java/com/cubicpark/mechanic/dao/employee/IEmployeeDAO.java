package com.cubicpark.mechanic.dao.employee;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeQueryDTO;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 雇员DAO
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IEmployeeDAO {

    /**
     * 
     * 功能描述: <br>
     * 新增雇员基本信息
     *
     * @param employee
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int addEmployee(EmployeeDTO employee);

    /**
     * 
     * 功能描述: <br>
     * 修改雇员基本信息
     *
     * @param employee
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyEmployee(EmployeeDTO employee);

    /**
     * 
     * 功能描述: <br>
     * 根据条件查询雇员列表
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<EmployeeDTO> queryEmployee(EmployeeQueryDTO query);

    /**
     * 
     * 功能描述: <br>
     * 根据雇员编号获取雇员信息
     *
     * @param employeeCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    EmployeeDTO getEmployeeByCode(String employeeCode);

    /**
     * 
     * 功能描述: <br>
     * 账号状态修改
     *
     * @param employeeCode
     * @param status
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int setEmployeeStatus(@Param("employeeCode") String employeeCode, @Param("status") String status);

    /**
     * 
     * 功能描述: <br>
     * 离职办理
     *
     * @param employeeCode
     * @param status
     * @param leaveDate
     * @param handoverPerson
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int setEmployeeQuit(@Param("employeeCode") String employeeCode, @Param("status") String status,
            @Param("leaveDate") Timestamp leaveDate, @Param("handoverPerson") String handoverPerson);

    /**
     * 
     * 功能描述: <br>
     * 角色权限设置
     *
     * @param employeeCode
     * @param roleCode
     * @param permission
     * @param salesAreas
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int setEmployeeRole(@Param("employeeCode") String employeeCode, @Param("roleCode") String roleCode,
            @Param("permission") String permission, @Param("salesAreas") String salesAreas);

    /**
     * 
     * 功能描述: <br>
     * 密码修改
     *
     * @param employeeCode
     * @param newPassWords
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    int modifyPassWords(@Param("employeeCode") String employeeCode, @Param("newPassWords") String newPassWords);

    /**
     * 
     * 功能描述: <br>
     * 用户登录，根据工号和密码获取雇员信息
     *
     * @param employeeCode
     * @param passWords
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    EmployeeDTO login(@Param("employeeCode") String employeeCode, @Param("passWords") String passWords);
    
    /**
     * 
     * 功能描述: <br>
     * 获取部门下所以员工信息
     *
     * @param departmentCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    List<EmployeeDTO> getDepartmentEmployee(String departmentCode);

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
