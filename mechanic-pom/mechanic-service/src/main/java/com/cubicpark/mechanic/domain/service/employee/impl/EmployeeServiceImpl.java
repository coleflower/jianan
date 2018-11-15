package com.cubicpark.mechanic.domain.service.employee.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.common.util.MD5Util;
import com.cubicpark.mechanic.dao.employee.IEmployeeDAO;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeQueryDTO;
import com.cubicpark.mechanic.domain.ruleengine.engine.RuleEngine;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.exception.ServiceException;

@Service("employeeService")
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IEmployeeDAO employeeDao;
    @Autowired
    private IDepartmentService departmentService;
    @Resource
    private HashMap<String, RuleEngine> employeeQuitEngine = new HashMap<String, RuleEngine>();

    public EmployeeDTO getEmployeeByCode(String employeeCode) {
        if (StringUtils.isBlank(employeeCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());

        return employeeDao.getEmployeeByCode(employeeCode);
    }

    public EmployeeDTO login(String employeeCode, String passWords) {
        if (!StringUtils.isBlank(employeeCode) && !StringUtils.isEmpty(passWords))
            return employeeDao.login(employeeCode, MD5Util.md5Hex(passWords));
        else
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());
    }

    public List<EmployeeDTO> queryEmployee(String employeeCode, String name, String departmentCode,
            String accountStatus, Timestamp startEntryDate, Timestamp endEntryDate) {

        // 所在部门不为空 获取该部门下所以子分类 无子分类时为本部门
        List<String> departmentList = new ArrayList<String>();
        if (StringUtils.isNotBlank(departmentCode)) {
            DepartmentDTO department = departmentService.getDepartmentByCode(departmentCode);

            List<DepartmentDTO> allChilds = null;
            if (department != null) {
                allChilds = departmentService.getAllChildsById(department.getId());
            }

            if (allChilds != null) {
                for (DepartmentDTO d : allChilds) {
                    departmentList.add(d.getDepartmentCode());
                }

                departmentList.add(departmentCode);
            } else {
                departmentList.add(departmentCode);
            }
        }

        EmployeeQueryDTO employeeQuery = buildEmployeeQuery(employeeCode, name, departmentList, accountStatus,
                startEntryDate, endEntryDate);

        return employeeDao.queryEmployee(employeeQuery);
    }

    public String addEmployeeBaseInfo(EmployeeDTO employee) {
        if (employee == null)
            return CommonErrorCode.OBJECTISNULL.getValue();

        employeeDao.addEmployee(employee);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyEmployeeBaseInfo(EmployeeDTO employee) {
        if (employee == null)
            return CommonErrorCode.OBJECTISNULL.getValue();

        employeeDao.modifyEmployee(employee);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String addEmployeeRoleInfo(String employeeCode, String roleCode, String permission, String salesAreas) {
        if (StringUtils.isBlank(employeeCode))
            return CommonErrorCode.CONDITIONISNULL.getValue();

        employeeDao.setEmployeeRole(employeeCode, roleCode, permission, salesAreas);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyEmployeeStatus(String employeeCode, String status) {
        // 获取当前账号信息
        if (StringUtils.isBlank(employeeCode))
            return CommonErrorCode.CONDITIONISNULL.getValue();

        EmployeeDTO employee = getEmployeeByCode(employeeCode);
        String currentStatus = employee.getAccountStatus();// 获取帐号当前状态

        // 判断当前帐号状态是否是创建，如果是创建则不能修改状态，如果是创建以外的状态，则不能修改成创建
        if (Constants.ACCOUNT_NO_ACTIVE.equals(currentStatus) || Constants.ACCOUNT_NO_ACTIVE.equals(status))
            return CommonErrorCode.MODIFYCONDITIONISACCORD.getValue();

        employeeDao.setEmployeeStatus(employeeCode, status);
        return CommonErrorCode.SUCCESS.getValue();
    }

    public String enabledEmployeeQuit(String employeeCode, String handoverPerson, Timestamp leaveDate) throws Exception {
        // 获取当前账号信息
        if (StringUtils.isBlank(employeeCode))
            return CommonErrorCode.CONDITIONISNULL.getValue();

        EmployeeDTO employee = getEmployeeByCode(employeeCode);
        if (employee == null)
            return CommonErrorCode.OBJECTISNULL.getValue();
        
        String currentStatus = employee.getAccountStatus();// 获取帐号当前状态
        String departmentCode = employee.getDepartmentCode();// 获取所在部门编码

        // 判断当前帐号状态，只有激活状态下的帐号可以操作
        if (!Constants.ACCOUNT_ACTIVE.equals(currentStatus))
            return CommonErrorCode.MODIFYCONDITIONISACCORD.getValue();

        // 将离职人员的业务（客户）信息更新至交接人 启动业务交接责任链模式，后期修改时，不需要动该方法，增加责任环节就行
        // 根据部门的不同，启动不同的交接引擎 简单工厂模式 先系统数据设置，后通过配置文件来进行修改 后期通过后台来读取一一匹配
        //RuleEngine ruleEngine = (RuleEngine) employeeQuitEngine.get(departmentCode);
        //ruleEngine.processRequest(employee);

        // 设置交接人 设置离职人员帐号状态为失效
        employeeDao.setEmployeeQuit(employeeCode, Constants.ACCOUNT_DISABLE, leaveDate, handoverPerson);

        return CommonErrorCode.SUCCESS.getValue();
    }

    public String modifyPassWords(String employeeCode, String oldPassWords, String newPassWords) {
        if (StringUtils.isBlank(employeeCode))
            return CommonErrorCode.CONDITIONISNULL.getValue();

        EmployeeDTO employee = getEmployeeByCode(employeeCode);
        String currentOldPassWords = employee.getPasswords();// 获取当前的旧密码
        String currentStatus = employee.getAccountStatus();// 获取帐号当前状态

        // MD5加密
        String oldPassWordsTemp = MD5Util.md5Hex(oldPassWords);
        String passWords = MD5Util.md5Hex(newPassWords);

        // 判断老密码是否一致
        if (!currentOldPassWords.equals(oldPassWordsTemp))
            return CommonErrorCode.COMPAREKEYISNOSAME.getValue();

        // 修改密码
        employeeDao.modifyPassWords(employeeCode, passWords);

        // 判断当前帐号状态是否为创建，如果当前状态为创建，在修改密码后修改帐号状态为激活。
        if (Constants.ACCOUNT_NO_ACTIVE.equals(currentStatus))
            employeeDao.setEmployeeStatus(employeeCode, Constants.ACCOUNT_ACTIVE);

        return CommonErrorCode.SUCCESS.getValue();
    }

    public List<EmployeeDTO> getEmployeeByDepartmentCode(String departmentCode) {
        // 获取当前账号信息
        if (StringUtils.isBlank(departmentCode))
            throw new ServiceException(CommonErrorCode.CONDITIONISNULL.getValue(),
                    CommonErrorCode.CONDITIONISNULL.getDesc());

        return employeeDao.getDepartmentEmployee(departmentCode);
    }
    
    public String resetPassword(String employeeCode, String newPassWords) {
        if (StringUtils.isBlank(employeeCode))
            return CommonErrorCode.CONDITIONISNULL.getValue();

        EmployeeDTO employee = getEmployeeByCode(employeeCode);
        if (employee == null)
            return CommonErrorCode.CONDITIONISNULL.getValue();

        String passWords = MD5Util.md5Hex(newPassWords);

        // 修改密码
        employeeDao.modifyPassWords(employeeCode, passWords);
        return CommonErrorCode.SUCCESS.getValue();
    }

    @Override
    public List<EmployeeDTO> selectByDepartmentCode(String departmentCode) {
        return employeeDao.selectByDepartmentCode(departmentCode);
    }

    private EmployeeQueryDTO buildEmployeeQuery(String employeeCode, String name, List<String> departmentList,
            String accountStatus, Timestamp startEntryDate, Timestamp endEntryDate) {
        EmployeeQueryDTO employeeQuery = new EmployeeQueryDTO();

        employeeQuery.setEmployeeCode(employeeCode);
        employeeQuery.setName(name);
        employeeQuery.setDepartmentCode(departmentList);
        employeeQuery.setAccountStatus(accountStatus);
        employeeQuery.setStartEntryDate(startEntryDate);
        employeeQuery.setEndEntryDate(endEntryDate);

        return employeeQuery;
    }

}
