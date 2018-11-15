package com.cubicpark.mechanic.controller.customer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerSummaryDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerSummaryService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.CustomerSummaryVO;

@Controller
@RequestMapping("customerSummary")
public class CustomerSummaryController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerSummaryController.class);

    @Autowired
    private ICustomerInfoService customerInfoService;
    @Autowired
    private ICustomerSummaryService customerSummaryService;

    /**
     * 
     * 功能描述: <br>
     * 初始化新增页面
     *
     * @param request
     * @param model
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=initAdd")
    public String initAdd(HttpServletRequest request, Model model, String customerCode) {

        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);
        if (customerInfo == null) {
            model.addAttribute("error", "客户不存在或已被删除！");
        }

        model.addAttribute("customerInfo", customerInfo);
        return "customer/summary/add";
    }

    /**
     * 
     * 功能描述: <br>
     * 新增
     *
     * @param request
     * @param response
     * @param project
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=add")
    public String add(HttpServletRequest request, HttpServletResponse response, CustomerSummaryVO customerSummary) {

        String message = null;
        if (customerSummary == null) {
            message = "总结不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(customerSummary.getCustomerCode())
                || StringUtils.isBlank(customerSummary.getStartTime())
                || StringUtils.isBlank(customerSummary.getEndTime())
                || StringUtils.isBlank(customerSummary.getDescription())) {
            message = "总结信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerSummary
                .getCustomerCode());
        if (Constants.SAVE.equals(customerInfo.getInfoState())) {
            message = "客户信息未提交，无法新增产品信息，请先提交客户信息！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(customerSummary.getInfoState())) {
            customerSummary.setInfoState(Constants.COMMIT);
        }
        
        // 设置为评价
        customerSummary.setReplyInfo(Constants.NOREPLY);

        CustomerSummaryDTO newCustomerSummary = new CustomerSummaryDTO();
        BeanUtils.copyProperties(customerSummary, newCustomerSummary);
        newCustomerSummary.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newCustomerSummary.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerSummaryService.addCustomerSummary(newCustomerSummary);
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

    @RequestMapping(params = "method=getCustomerSummary")
    public String getCustomerDemand(HttpServletRequest request, Model model, String customerCode) {
        List<CustomerSummaryDTO> resultList = new ArrayList<CustomerSummaryDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerSummaryService.getCustomerSummarys(customerCode);
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);

        model.addAttribute("customerSummarys", resultList);
        model.addAttribute("customerInfo", customerInfo);
        return "customer/summary/list";
    }
    
    @RequestMapping(params = "method=getCustomerSeaSummary")
    public String getCustomerSeaSummary(HttpServletRequest request, Model model, String customerCode) {
        List<CustomerSummaryDTO> resultList = new ArrayList<CustomerSummaryDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerSummaryService.getCustomerSummarys(customerCode);
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);

        model.addAttribute("customerSummarys", resultList);
        model.addAttribute("customerInfo", customerInfo);
        return "customersea/summary/list";
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
    @RequestMapping(params = "method=initModify")
    public String initModify(HttpServletRequest request, Model model, int id) {

        CustomerSummaryDTO customerSummary = customerSummaryService.getCustomerSummary(id);
        if (customerSummary == null) {
            model.addAttribute("error", "选中总结不存在或已被删除！");
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerSummary
                .getCustomerCode());

        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("summary", customerSummary);
        return "customer/summary/modify";
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
    public String modify(HttpServletRequest request, HttpServletResponse response, CustomerSummaryVO customerSummary) {

        String message = null;
        if (customerSummary == null) {
            message = "总结不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(customerSummary.getCustomerCode())
                || StringUtils.isBlank(customerSummary.getStartTime())
                || StringUtils.isBlank(customerSummary.getEndTime())
                || StringUtils.isBlank(customerSummary.getDescription())) {
            message = "总结信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }
        
        // 判断是否有上级评价，如果评价进行设置
        if (!StringUtils.isBlank(customerSummary.getReplyInfo())) {
            EmployeeDTO employee = super.getEmployee(request);
            customerSummary.setIsReply(Constants.REPLY);
            customerSummary.setEmployeeCode(employee.getEmployeeCode());
        }

        if (!Constants.SAVE.equals(customerSummary.getInfoState())) {
            customerSummary.setInfoState(Constants.COMMIT);
        }

        CustomerSummaryDTO newCustomerSummary = new CustomerSummaryDTO();
        BeanUtils.copyProperties(customerSummary, newCustomerSummary);
        newCustomerSummary.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerSummaryService.modifyCustomerSummary(newCustomerSummary);
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
    public String del(HttpServletRequest request, HttpServletResponse response, int id) {
        String message = null;
        if (id == 0) {
            message = "需删除的总结不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = customerSummaryService.delCustomerSummary(id);
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
