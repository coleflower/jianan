package com.cubicpark.mechanic.controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.base.IPositionService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.CustomerQueryVO;
import com.cubicpark.mechanic.vo.CustomerSeaQueryVO;
import com.cubicpark.mechanic.vo.PageResultVO;
import com.firstji.mentha.common.utils.StringUtil;

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICustomerInfoService customerInfoService;
    @Resource
    private Map<String, String> gradeMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> customerQueryEventMap = new HashMap<String, String>();

    /**
     * 
     * 功能描述: <br>
     * 查询项目列表信息
     *
     * @param request
     * @param model
     * @param query
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, Model model, @ModelAttribute("query") CustomerQueryVO query) {
        List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
        List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
        String canSee = "0";//

        // 获取当前登录的员工信息
        EmployeeDTO currentEmployee = super.getEmployee(request);
        String role = currentEmployee.getRoleCode();
        List<String> roleList = StringUtil.parseString2ListByPattern(role);

        // 判断该角色中是否存在观察者角色 设置部门信息
        if (StringUtil.isEmpty(currentEmployee.getDepartmentCode())) {
            model.addAttribute("err", "无法确认您所属哪个部门，请联系系统管理员！");
            return "customer/manage";
        }

        DepartmentDTO department = departmentService.getDepartmentByCode(currentEmployee.getDepartmentCode());// 所在部门
        if (department == null) {
            model.addAttribute("err", "部门信息不存在，请联系系统管理员！");
            return "customer/manage";
        }
        departments.add(department);

        if (department.getChild() > 0) {
            // 获取所有子部门
            List<DepartmentDTO> allChilds = departmentService.getAllChildsById(department.getId());
            departments.addAll(allChilds);
        }

        // 判断当前员工职位是否存在子分类，代表为管理岗位，有权限查看下属工作
        PositionDTO position = positionService.getPositionByCode(currentEmployee.getPositionCode());
        if (position == null) {
            model.addAttribute("err", "岗位信息不存在，请联系系统管理员！");
            return "customer/manage";
        }
        if (position.getChild() > 0) {
            canSee = "1";
        }

        // 获取当前登录用户部门下所以的员工信息
        employees = employeeService.getEmployeeByDepartmentCode(currentEmployee.getDepartmentCode());

        // 设置当前员工为选中
        query.setDepartmentCode(currentEmployee.getDepartmentCode());
        query.setEmployeeCode(currentEmployee.getEmployeeCode());

        model.addAttribute("departments", departments);
        model.addAttribute("employees", employees);
        model.addAttribute("grades", gradeMap);
        model.addAttribute("events", customerQueryEventMap);
        model.addAttribute("canSee", canSee);

        return "customer/manage";
    }

    @RequestMapping(params = "method=query")
    public String query(HttpServletRequest request, Model model, @ModelAttribute("query") CustomerQueryVO query) {
        List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
        List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
        List<CustomerInfoDTO> resultList = new ArrayList<CustomerInfoDTO>();
        String canSee = "0";//
        PageResultVO pageResult = null;

        // 获取当前登录的员工信息
        EmployeeDTO currentEmployee = super.getEmployee(request);
        String role = currentEmployee.getRoleCode();
        List<String> roleList = StringUtil.parseString2ListByPattern(role);

        // 判断该角色中是否存在观察者角色 设置部门信息
        if (StringUtil.isEmpty(currentEmployee.getDepartmentCode())) {
            model.addAttribute("err", "无法确认您所属哪个部门，请联系系统管理员！");
            return "customer/manage";
        }

        DepartmentDTO department = departmentService.getDepartmentByCode(currentEmployee.getDepartmentCode());// 所在部门
        if (department == null) {
            model.addAttribute("err", "部门信息不存在，请联系系统管理员！");
            return "customer/manage";
        }
        departments.add(department);

        if (department.getChild() > 0) {
            // 获取所有子部门
            List<DepartmentDTO> allChilds = departmentService.getAllChildsById(department.getId());
            departments.addAll(allChilds);
        }

        // 判断当前员工职位是否存在子分类，代表为管理岗位，有权限查看下属工作
        PositionDTO position = positionService.getPositionByCode(currentEmployee.getPositionCode());
        if (position == null) {
            model.addAttribute("err", "岗位信息不存在，请联系系统管理员！");
            return "customer/manage";
        }
        if (position.getChild() > 0) {
            canSee = "1";
        }

        if (query == null) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            // 获取选择部门下员工信息
            if (StringUtils.isNotBlank(query.getDepartmentCode())) {
                employees = employeeService.getEmployeeByDepartmentCode(query.getDepartmentCode());
            } else {
                employees = employeeService.getEmployeeByDepartmentCode(currentEmployee.getDepartmentCode());
            }

            resultList = customerService.queryCustomer(null, query.getCustomerName(), query.getGrade(),
                    query.getStartDate(), query.getEndDate(), query.getEvent(), query.getDepartmentCode(),
                    query.getEmployeeCode(), currentEmployee.getEmployeeCode(), query.getPage());

            // 获取当前查询总数
            int total = customerService.queryCustomerTotal(null, query.getCustomerName(), query.getGrade(),
                    query.getStartDate(), query.getEndDate(), query.getEvent(), query.getDepartmentCode(),
                    query.getEmployeeCode(), currentEmployee.getEmployeeCode());
            // 获取分页总数
            int pageCount = total % Constants.COMMON_PAGESIZE > 0 ? (total / Constants.COMMON_PAGESIZE + 1) : total
                    / Constants.COMMON_PAGESIZE;

            pageResult = new PageResultVO();
            pageResult.setTotal(total);
            pageResult.setPageCount(pageCount);
            pageResult.setPageNumber(query.getPage());
            pageResult.setPageSize(Constants.COMMON_PAGESIZE);
        }

        model.addAttribute("departments", departments);
        model.addAttribute("employees", employees);
        model.addAttribute("grades", gradeMap);
        model.addAttribute("events", customerQueryEventMap);
        model.addAttribute("canSee", canSee);
        model.addAttribute("customers", resultList);
        model.addAttribute("page", pageResult);
        return "customer/manage";
    }

    /**
     * 
     * 功能描述: <br>
     * 删除非提交状态下的项目信息
     *
     * @param request
     * @param response
     * @param projectCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=del")
    public String del(HttpServletRequest request, HttpServletResponse response, String customerCode) {
        String message = null;
        if (StringUtils.isBlank(customerCode)) {
            message = "客户信息不完整，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = customerInfoService.delCustomerInfoByCustomerCode(customerCode);
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

    @RequestMapping(params = "method=getJsonCustomerByEmployee")
    public String getJsonCustomerByEmployee(HttpServletRequest request, HttpServletResponse response,
            String employeeCode) {
        List<CustomerInfoDTO> customers = null;

        if (StringUtils.isBlank(employeeCode)) {
            return null;
        }

        customers = customerService.getCustomerByEmployeeCode(employeeCode);

        AjaxUtil.ajaxCustomJsonMessage(response, "state", "success", "customers", customers);
        return null;
    }
    
    @RequestMapping(params = "method=customerSea")
    public String customerSea(HttpServletRequest request, Model model, @ModelAttribute("query") CustomerSeaQueryVO query) {
        List<CustomerInfoDTO> resultList = new ArrayList<CustomerInfoDTO>();
        PageResultVO pageResult = null;

        if (query == null) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerService.queryCustomerSea(query.getStartDate(), query.getEndDate(), query.getPage());

            // 获取当前查询总数
            int total = customerService.queryCustomerSeaTotal(query.getStartDate(), query.getEndDate());
            // 获取分页总数
            int pageCount = total % Constants.COMMON_PAGESIZE > 0 ? (total / Constants.COMMON_PAGESIZE + 1) : total
                    / Constants.COMMON_PAGESIZE;

            pageResult = new PageResultVO();
            pageResult.setTotal(total);
            pageResult.setPageCount(pageCount);
            pageResult.setPageNumber(query.getPage());
            pageResult.setPageSize(Constants.COMMON_PAGESIZE);
        }

        model.addAttribute("grades", gradeMap);
        model.addAttribute("customers", resultList);
        model.addAttribute("page", pageResult);
        return "customersea/manage";
    }
    
    /**
     * 
     * 功能描述: <br>
     * 将公海客户变成私有客户
     *
     * @param request
     * @param response
     * @param customerCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=handle")
    public String handle(HttpServletRequest request, HttpServletResponse response, String customerCode, String oldEmployeeCode) {
        String message = null;
        if (StringUtils.isBlank(customerCode) || StringUtils.isBlank(oldEmployeeCode)) {
            message = "客户信息不完整，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }
        
        EmployeeDTO employee = super.getEmployee(request);
        if (employee == null) {
            message = "您的状态出现问题，请重新登录！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = customerInfoService.handleCustomer(customerCode, oldEmployeeCode, employee.getEmployeeCode());
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
}
