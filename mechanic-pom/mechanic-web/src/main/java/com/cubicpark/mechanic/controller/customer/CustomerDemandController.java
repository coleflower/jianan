package com.cubicpark.mechanic.controller.customer;

import java.sql.Timestamp;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.customer.CustomerDemandDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerDemandService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.CustomerDemandVO;

@Controller
@RequestMapping("customerDemand")
public class CustomerDemandController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDemandController.class);

    @Autowired
    private ICustomerInfoService customerInfoService;
    @Autowired
    private ICustomerDemandService customerDemandService;
    @Resource
    private Map<String, String> demandTypeMap = new HashMap<String, String>();
    

    /**
     * 
     * 功能描述: <br>
     * 初始化话新增页面
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
            model.addAttribute("error", "选中添加产品的客户不存在或已被删除！");
        }

        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("demandType", demandTypeMap);
        return "customer/demand/add";
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
    public String add(HttpServletRequest request, HttpServletResponse response, CustomerDemandVO customerDemand) {

        String message = null;
        if (customerDemand == null) {
            message = "客户产品不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(customerDemand.getCustomerCode()) || StringUtils.isBlank(customerDemand.getDemandInfo())) {
            message = "客户产品不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }
        
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerDemand.getCustomerCode());
        if (Constants.SAVE.equals(customerInfo.getInfoState())) {
            message = "客户信息未提交，无法新增产品信息，请先提交客户信息！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(customerDemand.getInfoState())) {
            customerDemand.setInfoState(Constants.COMMIT);
        }

        CustomerDemandDTO newCustomerDemand = new CustomerDemandDTO();
        BeanUtils.copyProperties(customerDemand, newCustomerDemand);
        newCustomerDemand.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newCustomerDemand.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerDemandService.addCustomerDemand(newCustomerDemand);
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

    @RequestMapping(params = "method=getCustomerDemand")
    public String getCustomerDemand(HttpServletRequest request, Model model, String customerCode) {
        List<CustomerDemandDTO> resultList = new ArrayList<CustomerDemandDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerDemandService.getCustomerDemands(customerCode);
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);

        model.addAttribute("customerDemands", resultList);
        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("demandType", demandTypeMap);
        return "customer/demand/list";
    }
    
    @RequestMapping(params = "method=getCustomerSeaDemand")
    public String getCustomerSeaDemand(HttpServletRequest request, Model model, String customerCode) {
        List<CustomerDemandDTO> resultList = new ArrayList<CustomerDemandDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerDemandService.getCustomerDemands(customerCode);
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);

        model.addAttribute("customerDemands", resultList);
        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("demandType", demandTypeMap);
        return "customersea/demand/list";
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

        CustomerDemandDTO customerDemand = customerDemandService.getCustomerDemand(id);
        if (customerDemand == null) {
            model.addAttribute("error", "选中修改的产品不存在或已被删除！");
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerDemand
                .getCustomerCode());

        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("customerDemand", customerDemand);
        model.addAttribute("demandType", demandTypeMap);
        return "customer/demand/modify";
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
    public String modify(HttpServletRequest request, HttpServletResponse response, CustomerDemandVO customerDemand) {

        String message = null;
        if (customerDemand == null) {
            message = "客户需求信息不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }
        
        if (customerDemand.getId() == 0 ||  StringUtils.isBlank(customerDemand.getCustomerCode()) || StringUtils.isBlank(customerDemand.getDemandInfo())) {
            message = "客户需求信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(customerDemand.getInfoState())) {
            customerDemand.setInfoState(Constants.COMMIT);
        }
        
        CustomerDemandDTO newCustomerDemand = new CustomerDemandDTO();
        BeanUtils.copyProperties(customerDemand, newCustomerDemand);
        newCustomerDemand.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerDemandService.modifyCustomerDemand(newCustomerDemand);
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
            message = "需删除的产品信息有误，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = customerDemandService.delCustomerDemand(id);
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
