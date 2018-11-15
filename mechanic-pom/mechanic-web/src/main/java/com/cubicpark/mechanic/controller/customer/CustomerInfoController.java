package com.cubicpark.mechanic.controller.customer;

import java.sql.Timestamp;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.CustomerInfoVO;

@Controller
@RequestMapping("customerInfo")
public class CustomerInfoController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerInfoController.class);

    @Autowired
    private ICustomerInfoService customerInfoService;
    @Resource
    private Map<String, String> gradeMap = new HashMap<String, String>();

    /**
     * 
     * 功能描述: <br>
     * 初始化话新增客户信息
     *
     * @param request
     * @param model
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=initAdd")
    public String initAdd(HttpServletRequest request, Model model) {

        model.addAttribute("grade", gradeMap);
        return "customer/info/add";
    }

    /**
     * 
     * 功能描述: <br>
     * 新增客户信息
     *
     * @param request
     * @param response
     * @param project
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=add")
    public String add(HttpServletRequest request, HttpServletResponse response, CustomerInfoVO customerInfo) {

        EmployeeDTO employee = super.getEmployee(request);
        String message = null;
        if (employee == null) {
            message = "您的状态出现问题，请重新登录！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (customerInfo == null) {
            message = "客户信息不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(employee.getEmployeeCode()) || StringUtils.isBlank(customerInfo.getCustomerName())
                || StringUtils.isBlank(customerInfo.getProvince()) || StringUtils.isBlank(customerInfo.getCity())
                || StringUtils.isBlank(customerInfo.getCounty()) || StringUtils.isBlank(customerInfo.getAddress())
                || StringUtils.isBlank(customerInfo.getGrade())) {
            message = "客户信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        // 判断客户是否已存在
        CustomerInfoDTO exitsCustomer = customerInfoService.getCustomerInfoByCustomerName(customerInfo
                .getCustomerName());
        if (exitsCustomer != null) {
            message = "存在相同名称的客户，请确认后重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        // 自动生成CODE
        String customerCode = MenthaCodeUtil.generateMenthaCode("customer", 19);
        customerInfo.setCustomerCode(customerCode);
        customerInfo.setEmployeeCode(employee.getEmployeeCode());

        CustomerInfoDTO newCustomerInfo = new CustomerInfoDTO();
        BeanUtils.copyProperties(customerInfo, newCustomerInfo);
        newCustomerInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newCustomerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerInfoService.addCustomerInfo(newCustomerInfo);
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
     * 查看项目信息
     *
     * @param request
     * @param model
     * @param projectCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=getCusomerInfo")
    public String getCusomerInfo(HttpServletRequest request, Model model, String customerCode) {
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);
        if (customerInfo == null) {
            model.addAttribute("error", "选中客户不存在或已被删除！");
        }

        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("grade", gradeMap);
        return "customer/info/modify";
    }
    
    @RequestMapping(params = "method=getCustomerSeaInfo")
    public String getCustomerSeaInfo(HttpServletRequest request, Model model, String customerCode) {
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);
        if (customerInfo == null) {
            model.addAttribute("error", "选中客户不存在或已被删除！");
        }

        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("grade", gradeMap);
        return "customersea/info/info";
    }

    /**
     * 
     * 功能描述: <br>
     * 修改项目信息
     *
     * @param request
     * @param response
     * @param project
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=modify")
    public String modify(HttpServletRequest request, HttpServletResponse response, CustomerInfoVO customerInfo) {

        EmployeeDTO employee = super.getEmployee(request);
        CustomerInfoDTO currentCustomerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerInfo
                .getCustomerCode());
        String message = null;
        if (employee == null) {
            message = "您的状态出现问题，请重新登录！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (currentCustomerInfo == null) {
            message = "客户信息不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(employee.getEmployeeCode()) || StringUtils.isBlank(customerInfo.getCustomerName())
                || StringUtils.isBlank(customerInfo.getProvince()) || StringUtils.isBlank(customerInfo.getCity())
                || StringUtils.isBlank(customerInfo.getCounty()) || StringUtils.isBlank(customerInfo.getAddress())
                || StringUtils.isBlank(customerInfo.getGrade())) {
            message = "客户信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        // 修改时判断客户名称除了自身外是否已存在
        CustomerInfoDTO exitsCustomer = customerInfoService.getCustomerInfoByCustomerNameExceptSelf(
                customerInfo.getCustomerName(), customerInfo.getCustomerCode());
        if (exitsCustomer != null) {
            message = "存在相同名称的客户，请确认后重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        // 当前不是同一个雇员且客户非无效客户时不能修改客户信息
        if (!currentCustomerInfo.getEmployeeCode().equals(employee.getEmployeeCode())
                && !"G00".equals(currentCustomerInfo.getGrade())) {
            message = "有效且非本人客户不能修改信息或变更所属人!";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        } else if ("G00".equals(currentCustomerInfo.getGrade())) {
            customerInfo.setEmployeeCode(employee.getEmployeeCode());
        }

        CustomerInfoDTO newCustomerInfo = new CustomerInfoDTO();
        BeanUtils.copyProperties(customerInfo, newCustomerInfo);
        newCustomerInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerInfoService.modifyCustomerInfo(newCustomerInfo);
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
}
