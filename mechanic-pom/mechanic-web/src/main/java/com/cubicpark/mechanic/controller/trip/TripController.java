/*
 * Copyright (C), 2016-2018, 南京园立方信息科技有限公司
 * FileName: TripController.java
 * Author:   first.ji
 * Date:     2018年9月12日 下午4:55:21
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cubicpark.mechanic.controller.trip;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripItemDTO;
import com.cubicpark.mechanic.domain.dto.trip.TripPlanDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.base.IPositionService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.domain.service.trip.ITripItemService;
import com.cubicpark.mechanic.domain.service.trip.ITripPlanService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.CustomerQueryVO;
import com.cubicpark.mechanic.vo.PageResultVO;
import com.cubicpark.mechanic.vo.trip.TripItemVO;
import com.cubicpark.mechanic.vo.trip.TripQueryVO;
import com.cubicpark.mechanic.vo.trip.TripVO;
import com.firstji.mentha.common.utils.StringUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author first.ji
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("trip")
public class TripController extends BaseController {
    
    //static Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITripPlanService tripPlanService;
    @Autowired
    private ITripItemService tripItemService;
    @Resource
    private Map<String, String> tripStateMap = new HashMap<String, String>();
    
    /**
     * 
     * 功能描述: <br>
     * 新增合同第一步：查询客户信息
     *
     * @param request
     * @param model
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=addStep1")
    public String step1(HttpServletRequest request, Model model, @ModelAttribute("query") CustomerQueryVO query) {
        return "trip/addone";
    }

    @RequestMapping(params = "method=queryCustomer")
    public String queryCustomer(HttpServletRequest request, Model model, @ModelAttribute("query") CustomerQueryVO query) throws IOException {
        List<CustomerInfoDTO> resultList = new ArrayList<CustomerInfoDTO>(); 
        String querystr = null;
        if (query == null) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            if (StringUtils.isBlank(query.getCustomerName())) {
                model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
            } else {
                StringBuffer st = new StringBuffer();
                st.append("customerName").append("=").append(URLEncoder.encode(query.getCustomerName(), "UTF-8"));
                querystr = st.toString();
                
                // 获取当前登录的员工信息 查询当前员工的客户信息
                EmployeeDTO currentEmployee = super.getEmployee(request);
                
                resultList = customerService.queryCustomer(null, query.getCustomerName(), null, null, null, null, null, null, currentEmployee.getEmployeeCode(), 0);
            }
        }

        model.addAttribute("customers", resultList);
        model.addAttribute("querystr", querystr);
        return "trip/addone";
    }

    @RequestMapping(params = "method=initAdd")
    public String initAdd(HttpServletRequest request, Model model) {
        return "trip/add";
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     *spring mvc下提交JSON的MVC处理需要注意的几点：
     *1.ajax提交时一定要contentType : "application/json; charset=utf-8"；
     *2.无论通过哪种转化类gson/fastjson,提交的一定是JSON字符串，不是对象；
     *3.ajax中data : JSON字符串；
     *
     *处理方式2：
     *1.前端直接提交JSON字符串，在MVC层使用@RequestBody 注解进行自动转换；
     *2.使用GSON对象
     *static Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
     *TripVO tripVO = gson.fromJson(trips, new TypeToken<TripVO>() {}.getType());
     *
     *注意：以上所有JSON均为字符串
     * @param request
     * @param response
     * @param tripVO
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=add")
    public String add(HttpServletRequest request, HttpServletResponse response, @RequestBody TripVO tripVO) {
        
        //TripVO tripVO = gson.fromJson(trips, new TypeToken<TripVO>() {}.getType());
        List<TripItemDTO> tripItems = new ArrayList<TripItemDTO>();
        TripDTO trip = new TripDTO();
        String message = null;
        // 获取当前登录的员工信息
        EmployeeDTO currentEmployee = super.getEmployee(request);
        
        // 生成出差计划CODE
        String tripCode = MenthaCodeUtil.generateMenthaCode("trip", 18);
        
        // 完善出差计划
        TripPlanDTO tripPlan = new TripPlanDTO();
        tripPlan.setDays(tripVO.getTripPlan().getDays());
        tripPlan.setCause(tripVO.getTripPlan().getCause());
        tripPlan.setCost(tripVO.getTripPlan().getCost());
        tripPlan.setPlanCode(tripCode);
        tripPlan.setEmployeeCode(currentEmployee.getEmployeeCode());
        tripPlan.setState(Constants.TRIP_STATE_TO);
        tripPlan.setCreateDate(new Timestamp(System.currentTimeMillis()));
        tripPlan.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        trip.setTripPlan(tripPlan);
       
        // 完善行程明细
        for(TripItemVO tv : tripVO.getTripItems()) {
            TripItemDTO ti = new TripItemDTO();
            
            ti.setPlanCode(tripCode);
            ti.setDestination(tv.getDestination());
            ti.setStartTime(tv.getStartTime());
            ti.setEndTime(tv.getEndTime());
            ti.setRemarks(tv.getRemarks());
            ti.setCreateDate(new Timestamp(System.currentTimeMillis()));
            ti.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            
            tripItems.add(ti);
        }
        trip.setTripItems(tripItems);
        
        String result = tripPlanService.addTrip(trip);
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

    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, Model model, @ModelAttribute("query") TripQueryVO query) {
        List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
        List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
        String canSee = "0";//

        // 获取当前登录的员工信息
        EmployeeDTO currentEmployee = super.getEmployee(request);

        // 判断该角色中是否存在观察者角色 设置部门信息
        if (StringUtil.isEmpty(currentEmployee.getDepartmentCode())) {
            model.addAttribute("err", "无法确认您所属哪个部门，请联系系统管理员！");
            return "trip/manage";
        }

        DepartmentDTO department = departmentService.getDepartmentByCode(currentEmployee.getDepartmentCode());// 所在部门
        if (department == null) {
            model.addAttribute("err", "部门信息不存在，请联系系统管理员！");
            return "trip/manage";
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
            return "trip/manage";
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
        model.addAttribute("canSee", canSee);
        model.addAttribute("tripState", tripStateMap);
        return "trip/manage";
    }

    @RequestMapping(params = "method=query")
    public String query(HttpServletRequest request, Model model, @ModelAttribute("query") TripQueryVO query) {
        List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
        List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
        String canSee = "0";//

        List<TripPlanDTO> resultList = new ArrayList<TripPlanDTO>();
        PageResultVO pageResult = null;

        // 获取当前登录的员工信息
        EmployeeDTO currentEmployee = super.getEmployee(request);

        // 判断该角色中是否存在观察者角色 设置部门信息
        if (StringUtil.isEmpty(currentEmployee.getDepartmentCode())) {
            model.addAttribute("err", "无法确认您所属哪个部门，请联系系统管理员！");
            return "trip/manage";
        }

        DepartmentDTO department = departmentService.getDepartmentByCode(currentEmployee.getDepartmentCode());// 所在部门
        if (department == null) {
            model.addAttribute("err", "部门信息不存在，请联系系统管理员！");
            return "trip/manage";
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
            return "trip/manage";
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

            resultList = tripPlanService.queryTripPlan(query.getStartDate(), query.getEndDate(), query.getState(),
                    query.getDepartmentCode(), query.getEmployeeCode(), query.getPage());

            // 获取当前查询总数
            int total = tripPlanService.queryTripPlanTotal(query.getStartDate(), query.getEndDate(), query.getState(),
                    query.getDepartmentCode(), query.getEmployeeCode());
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
        model.addAttribute("canSee", canSee);
        model.addAttribute("tripState", tripStateMap);
        model.addAttribute("tripPlans", resultList);
        model.addAttribute("page", pageResult);
        return "trip/manage";
    }
    
    @RequestMapping(params = "method=getTripItems")
    public String getTripItems(HttpServletRequest request, Model model, String planCode) {
        List<TripItemDTO> resultList = new ArrayList<TripItemDTO>();

        if (StringUtils.isBlank(planCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = tripItemService.getTripItemByPlanCode(planCode);
        }

        model.addAttribute("planCode", planCode);
        model.addAttribute("items", resultList);
        return "trip/item/list";
    }

    /**
     * 
     * 功能描述: <br>
     * 取消出差计划
     *
     * @param request
     * @param response
     * @param planCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=cancel")
    public String cancel(HttpServletRequest request, HttpServletResponse response, String planCode) {
        String message = null;

        // 获取当前登录用户，此用户为提交出差计划本人
        EmployeeDTO currentEmployee = super.getEmployee(request);
        String result = tripPlanService.cancelTripPlan(planCode, currentEmployee.getEmployeeCode());

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
     * 获取待审批的出差计划
     *
     * @param request
     * @param model
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=toApproval")
    public String toApproval(HttpServletRequest request, Model model) {
        List<TripDTO> trips = tripPlanService.getToApprovalTripPlan();

        model.addAttribute("trips", trips);
        return "trip/approval";
    }

    /**
     * 
     * 功能描述: <br>
     * 出差审批
     *
     * @param request
     * @param response
     * @param planCode
     * @param state
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=approval")
    public String approval(HttpServletRequest request, HttpServletResponse response, String planCode, String state) {
        String message = null;

        // 获取当前登录用户，此用户为审批用户
        EmployeeDTO currentEmployee = super.getEmployee(request);
        String result = tripPlanService.changeTripState(planCode, Constants.TRIP_STATE_TO, state,
                currentEmployee.getEmployeeCode());

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
