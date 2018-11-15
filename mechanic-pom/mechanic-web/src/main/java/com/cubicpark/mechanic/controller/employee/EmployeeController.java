package com.cubicpark.mechanic.controller.employee;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.common.util.DateUtil;
import com.cubicpark.mechanic.common.util.MD5Util;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.base.IPositionService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.EmployeeBaseInfoVO;
import com.cubicpark.mechanic.vo.EmployeeQueryResultVO;
import com.cubicpark.mechanic.vo.EmployeeQueryVO;
import com.firstji.mentha.common.utils.StringUtil;

@Controller
@RequestMapping("employee")
public class EmployeeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IEmployeeService employeeService;
    @Resource
    private Map<String, String> accountStateMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> educationMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> menuMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> roleMap = new HashMap<String, String>();

    /**
     * 
     * 功能描述: <br>
     * 新增雇员基本信息
     *
     * @param request
     * @param model
     * @param parentId
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=initAdd")
    public String initAdd(HttpServletRequest request, Model model) {
        List<DepartmentDTO> departmentList = departmentService.getAll();
        List<PositionDTO> positionList = positionService.getAll();

        model.addAttribute("allDepartment", departmentList);
        model.addAttribute("allPosition", positionList);
        model.addAttribute("educationMap", educationMap);
        return "employee/add";
    }

    @RequestMapping(params = "method=add")
    public String add(HttpServletRequest request, HttpServletResponse response, EmployeeBaseInfoVO employeeBaseInfo) {

        String message = null;
        if (employeeBaseInfo == null) {
            message = "员工信息不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(employeeBaseInfo.getEmployeeCode())
                || StringUtils.isBlank(employeeBaseInfo.getDepartmentCode())
                || StringUtils.isBlank(employeeBaseInfo.getPositionCode())
                || StringUtils.isBlank(employeeBaseInfo.getName()) || StringUtils.isBlank(employeeBaseInfo.getSex())
                || StringUtils.isBlank(employeeBaseInfo.getEducation())
                || StringUtils.isBlank(employeeBaseInfo.getCollege())
                || StringUtils.isBlank(employeeBaseInfo.getTelephone())
                || StringUtils.isBlank(String.valueOf(employeeBaseInfo.getEmployeeEntryDate()))) {
            message = "员工信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        // 查询员工工号是否存在
        EmployeeDTO currentEmployee = employeeService.getEmployeeByCode(employeeBaseInfo.getEmployeeCode());
        if (currentEmployee != null) {
            message = "员工工号已存在，请重新输入！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        EmployeeDTO employee = new EmployeeDTO();
        BeanUtils.copyProperties(employeeBaseInfo, employee);
        employee.setPasswords(MD5Util.md5Hex(employeeBaseInfo.getEmployeeCode()));// 设置默认密码为工号
        employee.setEntryDate(Timestamp.valueOf(DateUtil.INSTANCE.date2String(employeeBaseInfo.getEmployeeEntryDate(),
                "yyyy-MM-dd HH:mm:ss")));
        employee.setAccountStatus(Constants.ACCOUNT_NO_ACTIVE);// 设置新增员工账号状态为未激活
        employee.setCreateDate(new Timestamp(System.currentTimeMillis()));
        employee.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = employeeService.addEmployeeBaseInfo(employee);
        if (CommonErrorCode.SUCCESS.getValue().equals(resultInfo)) {
            // 新增成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 新增失败返回错误信息
            message = CommonErrorCode.getDescByValue(resultInfo);
        }

        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 雇员查询
     *
     * @param request
     * @param model
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, Model model, @ModelAttribute("query") EmployeeQueryVO query) {
        List<DepartmentDTO> departmentList = departmentService.getAll();

        model.addAttribute("allDepartment", departmentList);
        model.addAttribute("accountState", accountStateMap);
        return "employee/manage";
    }

    @RequestMapping(params = "method=query")
    public String query(HttpServletRequest request, Model model, @ModelAttribute("query") EmployeeQueryVO query) {
        List<DepartmentDTO> departmentList = departmentService.getAll();
        List<EmployeeQueryResultVO> resultList = new ArrayList<EmployeeQueryResultVO>();

        if (query == null) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            String employeeCode = query.getEmployeeCode();
            String name = query.getName();
            String departmentCode = query.getDepartmentCode();
            String accountStatus = query.getAccountStatus();
            String startEntryDate = query.getStartEntryDate();
            String endEntryDate = query.getEndEntryDate();

            if ((StringUtils.isNotBlank(startEntryDate) && StringUtils.isBlank(endEntryDate))
                    || (StringUtils.isBlank(startEntryDate) && StringUtils.isNotBlank(endEntryDate))) {
                model.addAttribute("error", "入职日期必须成对出现，请选择入职日期！");
            } else {
                Timestamp sTime = null;
                Timestamp eTime = null;
                if (StringUtils.isNotBlank(startEntryDate)) {
                    sTime = Timestamp
                            .valueOf(DateUtil.INSTANCE.formatDateString(startEntryDate, "yyyy-MM-dd HH:mm:ss"));
                }

                if (StringUtils.isNotBlank(endEntryDate)) {
                    eTime = Timestamp.valueOf(DateUtil.INSTANCE.formatDateString(endEntryDate, "yyyy-MM-dd HH:mm:ss"));
                }
                List<EmployeeDTO> employeeList = employeeService.queryEmployee(employeeCode, name, departmentCode,
                        accountStatus, sTime, eTime);

                for (EmployeeDTO e : employeeList) {
                    EmployeeQueryResultVO result = new EmployeeQueryResultVO();
                    BeanUtils.copyProperties(e, result);

                    // TODO 后期换成从缓存中读取
                    DepartmentDTO department = departmentService.getDepartmentByCode(e.getDepartmentCode());
                    PositionDTO position = positionService.getPositionByCode(e.getPositionCode());
                    if (department != null)
                        result.setDepartmentName(department.getDepartmentName());

                    if (position != null)
                        result.setPositionName(position.getPositionName());

                    resultList.add(result);
                }
            }
        }

        model.addAttribute("allDepartment", departmentList);
        model.addAttribute("accountState", accountStateMap);
        model.addAttribute("employees", resultList);
        model.addAttribute("educationMap", educationMap);
        return "employee/manage";
    }

    /**
     * 
     * 功能描述: <br>
     * 修改雇员基本信息
     *
     * @param request
     * @param model
     * @param id
     * @param rattr
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=initModify")
    public String initModify(HttpServletRequest request, Model model, String employeeCode) {
        List<DepartmentDTO> departmentList = departmentService.getAll();
        List<PositionDTO> positionList = positionService.getAll();
        EmployeeDTO employee = employeeService.getEmployeeByCode(employeeCode);

        if (employee == null) {
            model.addAttribute("error", "选中修改的员工不存在或已被删除！");
        }

        model.addAttribute("allDepartment", departmentList);
        model.addAttribute("allPosition", positionList);
        model.addAttribute("employee", employee);
        model.addAttribute("educationMap", educationMap);
        return "employee/modify";
    }

    @RequestMapping(params = "method=modify")
    public String modify(HttpServletRequest request, HttpServletResponse response, EmployeeBaseInfoVO employeeBaseInfo) {

        String message = null;
        if (employeeBaseInfo == null) {
            message = "员工信息不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(employeeBaseInfo.getEmployeeCode())
                || StringUtils.isBlank(employeeBaseInfo.getDepartmentCode())
                || StringUtils.isBlank(employeeBaseInfo.getPositionCode())
                || StringUtils.isBlank(employeeBaseInfo.getName()) || StringUtils.isBlank(employeeBaseInfo.getSex())
                || StringUtils.isBlank(employeeBaseInfo.getEducation())
                || StringUtils.isBlank(employeeBaseInfo.getCollege())
                || StringUtils.isBlank(employeeBaseInfo.getTelephone())
                || StringUtils.isBlank(String.valueOf(employeeBaseInfo.getEmployeeEntryDate()))) {
            message = "员工信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        EmployeeDTO employee = new EmployeeDTO();
        BeanUtils.copyProperties(employeeBaseInfo, employee);
        employee.setEntryDate(Timestamp.valueOf(DateUtil.INSTANCE.date2String(employeeBaseInfo.getEmployeeEntryDate(),
                "yyyy-MM-dd HH:mm:ss")));
        employee.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = employeeService.modifyEmployeeBaseInfo(employee);
        if (CommonErrorCode.SUCCESS.getValue().equals(resultInfo)) {
            // 修改成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 修改失败返回错误信息
            message = CommonErrorCode.getDescByValue(resultInfo);
        }

        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 编辑雇员角色，菜单权限及销售区域
     *
     * @param request
     * @param model
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=initRole")
    public String initRole(HttpServletRequest request, Model model, String employeeCode) {
        // List<SalesAreaDTO> salesAreaList = salesAreaService.getAll();
        EmployeeDTO employee = employeeService.getEmployeeByCode(employeeCode);
        if (employee == null) {
            model.addAttribute("error", "选中修改的员工不存在或已被删除！");
        }

        String roleCode = employee.getRoleCode();
        String permission = employee.getPermission();
        List<String> roleList = StringUtil.parseString2ListByPattern(roleCode);
        List<String> permissionList = StringUtil.parseString2ListByPattern(permission);

        // model.addAttribute("salesAreas", salesAreaList);
        model.addAttribute("employeeCode", employeeCode);
        model.addAttribute("roleCodes", roleList);
        model.addAttribute("permissions", permissionList);
        model.addAttribute("menu", menuMap);
        model.addAttribute("role", roleMap);
        return "employee/editRole";
    }

    @RequestMapping(params = "method=editRole")
    public String initOrderN(HttpServletRequest request, HttpServletResponse response, String employeeCode,
            String roleCode, String permission) {

        String message = null;
        if (StringUtils.isBlank(employeeCode) || StringUtils.isBlank(roleCode) || StringUtils.isBlank(permission)) {
            message = "权限信息不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = employeeService.addEmployeeRoleInfo(employeeCode, roleCode, permission, null);
        if (CommonErrorCode.SUCCESS.getValue().equals(result)) {
            // 成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 失败返回错误信息
            message = CommonErrorCode.getDescByValue(result);
        }
        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 修改账号状态
     *
     * @param request
     * @param model
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=modifyStatus")
    public String modifyStatus(HttpServletRequest request, HttpServletResponse response, String employeeCode,
            String newStatus) {
        String message = null;
        if (StringUtils.isBlank(employeeCode) || StringUtils.isBlank(newStatus)) {
            message = "账号状态信息不完整，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = employeeService.modifyEmployeeStatus(employeeCode, newStatus);
        if (CommonErrorCode.SUCCESS.getValue().equals(result)) {
            // 成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 失败返回错误信息
            message = CommonErrorCode.getDescByValue(result);
        }
        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 查重员工密码
     *
     * @param request
     * @param response
     * @param employeeCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=resetPassword")
    public String resetPassword(HttpServletRequest request, HttpServletResponse response, String employeeCode) {
        String message = null;
        if (StringUtils.isBlank(employeeCode)) {
            message = "需重置密码的员工工号为空，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        EmployeeDTO employee = employeeService.getEmployeeByCode(employeeCode);
        if (employee == null) {
            message = "需重置密码的员工不存在或已被删除！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = employeeService.resetPassword(employeeCode, employee.getEmployeeCode());
        if (CommonErrorCode.SUCCESS.getValue().equals(result)) {
            // 成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 失败返回错误信息
            message = CommonErrorCode.getDescByValue(result);
        }
        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 雇员离职处理
     *
     * @param request
     * @param model
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=initQuit")
    public String initQuit(HttpServletRequest request, Model model, String employeeCode) {
        EmployeeDTO employee = employeeService.getEmployeeByCode(employeeCode);
        if (employee == null) {
            model.addAttribute("error", "选中的员工不存在或已被删除！");
        }

        List<DepartmentDTO> departmentList = departmentService.getAll();
        List<EmployeeDTO> teamEmployeeList = employeeService.getEmployeeByDepartmentCode(employee.getDepartmentCode());

        model.addAttribute("employee", employee);
        model.addAttribute("allDepartment", departmentList);
        model.addAttribute("teams", teamEmployeeList);
        return "employee/quit";
    }

    @RequestMapping(params = "method=quit")
    public String quit(HttpServletRequest request, HttpServletResponse response, String employeeCode, Date leaveDate,
            String handoverPerson, String remarks) {
        String message = null;
        if (StringUtils.isBlank(employeeCode) && leaveDate != null && StringUtils.isBlank(handoverPerson)) {
            message = "离职要素缺失，请确认离职员工，离职日期和交接人不为空！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        EmployeeDTO employee = employeeService.getEmployeeByCode(employeeCode);
        if (employee == null) {
            message = "离职员工不存在或已被删除！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        Timestamp leaveTime = Timestamp.valueOf(DateUtil.INSTANCE.date2String(leaveDate, "yyyy-MM-dd HH:mm:ss"));

        String result = null;
        try {
            result = employeeService.enabledEmployeeQuit(employeeCode, handoverPerson, leaveTime);
        } catch (Exception e) {
            result = CommonErrorCode.CONDITIONISNULL.getValue();
            logger.error(e.getMessage());
        }
        if (CommonErrorCode.SUCCESS.getValue().equals(result)) {
            // 成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 失败返回错误信息
            message = CommonErrorCode.getDescByValue(result);
        }
        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 修改密码
     *
     * @param request
     * @param model
     * @param employeeCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=initModifyPassword")
    public String initModifyPassword(HttpServletRequest request, Model model, String employeeCode) {
        String initModify = "no";
        EmployeeDTO employee = employeeService.getEmployeeByCode(employeeCode);
        if (employee == null) {
            model.addAttribute("error", "账号不存在或已被删除！");
        }
        
        if (Constants.ACCOUNT_NO_ACTIVE.equals(employee.getAccountStatus())) {
            initModify = "yes";
        }

        model.addAttribute("employee", employee);
        model.addAttribute("modifyTag", initModify);
        return "employee/modifyPassword";
    }

    @RequestMapping(params = "method=modifyPassword")
    public String modifyPassword(HttpServletRequest request, HttpServletResponse response, String employeeCode,
            String oldPassWords, String newPassWords) {
        String message = null;
        if (StringUtils.isBlank(employeeCode) && StringUtils.isBlank(newPassWords) && StringUtils.isBlank(oldPassWords)) {
            message = "修改密码失败，新老密码不得为空！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        EmployeeDTO employee = employeeService.getEmployeeByCode(employeeCode);
        if (employee == null) {
            message = "账号不存在或已被删除！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = null;
        try {
            result = employeeService.modifyPassWords(employeeCode, oldPassWords, newPassWords);
        } catch (Exception e) {
            result = CommonErrorCode.CONDITIONISNULL.getValue();
            logger.error(e.getMessage());
        }
        if (CommonErrorCode.SUCCESS.getValue().equals(result)) {
            // 成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 失败返回错误信息
            message = CommonErrorCode.getDescByValue(result);
        }
        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    @RequestMapping(params = "method=getJsonEmployeeByDepartment")
    public String getJsonEmployeeByDepartment(HttpServletRequest request, HttpServletResponse response,
            String departmentCode) {
        List<EmployeeDTO> employees = null;

        if (StringUtils.isBlank(departmentCode)) {
            return null;
        }

        employees = employeeService.queryEmployee(null, null, departmentCode, Constants.ACCOUNT_ACTIVE, null, null);

        AjaxUtil.ajaxCustomJsonMessage(response, "state", "success", "employees", employees);
        return null;
    }
}
