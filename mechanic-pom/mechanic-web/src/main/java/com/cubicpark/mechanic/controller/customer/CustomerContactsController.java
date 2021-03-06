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
import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.customer.CustomerContactsDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerContactsService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.CustomerContactsVO;

@Controller
@RequestMapping("customerContacts")
public class CustomerContactsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerContactsController.class);

    @Autowired
    private ICustomerInfoService customerInfoService;
    @Autowired
    private ICustomerContactsService customerContactsService;

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
            model.addAttribute("error", "客户不存在或已被删除！");
        }

        model.addAttribute("customerInfo", customerInfo);
        return "customer/contacts/add";
    }

    /**
     * 
     * 功能描述: <br>
     * 新增项目信息
     *
     * @param request
     * @param response
     * @param project
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=add")
    public String add(HttpServletRequest request, HttpServletResponse response, CustomerContactsVO customerContacts) {

        String message = null;
        if (customerContacts == null) {
            message = "联系人不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(customerContacts.getCustomerCode())
                || StringUtils.isBlank(customerContacts.getPosition())
                || StringUtils.isBlank(customerContacts.getContactsName())
                || StringUtils.isBlank(customerContacts.getDepartment())) {
            message = "联系人信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerContacts
                .getCustomerCode());
        if (Constants.SAVE.equals(customerInfo.getInfoState())) {
            message = "客户信息未提交，无法新增联系人，请先提交客户信息！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(customerContacts.getInfoState())) {
            customerContacts.setInfoState(Constants.COMMIT);
        }
        
        // 自动生成CODE
        String contactsCode = MenthaCodeUtil.generateMenthaCode("customercontacts", 18);
        customerContacts.setContactsCode(contactsCode);

        CustomerContactsDTO newCustomerContacts = new CustomerContactsDTO();
        BeanUtils.copyProperties(customerContacts, newCustomerContacts);
        newCustomerContacts.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newCustomerContacts.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerContactsService.addCustomerContacts(newCustomerContacts);
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

    @RequestMapping(params = "method=getContacts")
    public String get(HttpServletRequest request, Model model, String customerCode) {
        List<CustomerContactsDTO> resultList = new ArrayList<CustomerContactsDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerContactsService.getCustomerContacts(customerCode);
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);

        model.addAttribute("customerContacts", resultList);
        model.addAttribute("customerInfo", customerInfo);
        return "customer/contacts/list";
    }
    
    @RequestMapping(params = "method=getSeaContacts")
    public String getSea(HttpServletRequest request, Model model, String customerCode) {
        List<CustomerContactsDTO> resultList = new ArrayList<CustomerContactsDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerContactsService.getCustomerContacts(customerCode);
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);

        model.addAttribute("customerContacts", resultList);
        model.addAttribute("customerInfo", customerInfo);
        return "customersea/contacts/list";
    }

    /**
     * 
     * 功能描述: <br>
     * 查看信息
     *
     * @param request
     * @param model
     * @param projectCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=initModify")
    public String initModify(HttpServletRequest request, Model model, String contactsCode) {

        CustomerContactsDTO customerContacts = customerContactsService.getContacts(contactsCode);
        if (customerContacts == null) {
            model.addAttribute("error", "联系人不存在或已被删除！");
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerContacts
                .getCustomerCode());

        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("customerContacts", customerContacts);
        return "customer/contacts/modify";
    }

    /**
     * 
     * 功能描述: <br>
     * 修改信息
     *
     * @param request
     * @param response
     * @param project
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=modify")
    public String modify(HttpServletRequest request, HttpServletResponse response, CustomerContactsVO customerContacts) {

        String message = null;
        if (customerContacts == null) {
            message = "联系人不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(customerContacts.getCustomerCode())
                || StringUtils.isBlank(customerContacts.getContactsCode())
                || StringUtils.isBlank(customerContacts.getContactsName())
                || StringUtils.isBlank(customerContacts.getDepartment())) {
            message = "联系人信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(customerContacts.getInfoState())) {
            customerContacts.setInfoState(Constants.COMMIT);
        }

        CustomerContactsDTO newCustomerContacts = new CustomerContactsDTO();
        BeanUtils.copyProperties(customerContacts, newCustomerContacts);
        newCustomerContacts.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerContactsService.modifyCustomerContacts(newCustomerContacts);
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
     * 删除非提交状态下的信息
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

        String result = customerContactsService.delCustomerContacts(id);
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
