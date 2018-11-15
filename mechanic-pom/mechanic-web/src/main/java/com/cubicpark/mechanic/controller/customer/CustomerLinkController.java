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
import com.cubicpark.mechanic.domain.dto.customer.CustomerContactRecordDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerContactsDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerContactRecordService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerContactsService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.CustomerContactRecordVO;

@Controller
@RequestMapping("customerLink")
public class CustomerLinkController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerLinkController.class);

    @Resource
    private Map<String, String> connectionWayMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> communicateTypeMap = new HashMap<String, String>();
    @Autowired
    private ICustomerInfoService customerInfoService;
    @Autowired
    private ICustomerContactsService customerContactsService;
    @Autowired
    private ICustomerContactRecordService customerContactRecordService;

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
    public String initAdd(HttpServletRequest request, Model model, String customerCode, String contactsCode) {

        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);
        CustomerContactsDTO contacts = customerContactsService.getContacts(contactsCode);
        if (customerInfo == null) {
            model.addAttribute("error", "客户不存在或已被删除！");
        }

        if (contacts == null) {
            model.addAttribute("error", "联系人不存在或已被删除！");
        }

        model.addAttribute("connectionWay", connectionWayMap);
        model.addAttribute("communicateType", communicateTypeMap);
        model.addAttribute("contacts", contacts);
        model.addAttribute("customerInfo", customerInfo);
        return "customer/contactRecord/add";
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
    public String add(HttpServletRequest request, HttpServletResponse response, CustomerContactRecordVO contactRecord) {

        String message = null;
        if (contactRecord == null) {
            message = "联系记录不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(contactRecord.getCustomerCode())
                || StringUtils.isBlank(contactRecord.getContactsCode())
                || StringUtils.isBlank(contactRecord.getCommunicateType())
                || StringUtils.isBlank(contactRecord.getContactDate())
                || StringUtils.isBlank(contactRecord.getContents())
                || StringUtils.isBlank(contactRecord.getConnectionWay())) {
            message = "联系记录不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(contactRecord
                .getCustomerCode());
        if (customerInfo == null) {
            message = "客户信息不存在或已被删除，请确认客户是否存在！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (Constants.SAVE.equals(customerInfo.getInfoState())) {
            message = "客户信息未提交，无法新增联系记录，请先提交客户信息！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        CustomerContactsDTO customerContacts = customerContactsService.getContacts(contactRecord.getContactsCode());
        if (customerContacts == null) {
            message = "客户联系人不存在或已被删除，请确认客户联系人是否存在！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (Constants.SAVE.equals(customerContacts.getInfoState())) {
            message = "客户联系人未提交，无法新增联系记录，请先提交客户联系人信息！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(contactRecord.getInfoState())) {
            contactRecord.setInfoState(Constants.COMMIT);
        }

        CustomerContactRecordDTO newCustomerContactRecord = new CustomerContactRecordDTO();
        BeanUtils.copyProperties(contactRecord, newCustomerContactRecord);
        newCustomerContactRecord.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newCustomerContactRecord.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerContactRecordService.addContactRecord(newCustomerContactRecord);
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

    @RequestMapping(params = "method=getRecords")
    public String get(HttpServletRequest request, Model model, String customerCode, String contactsCode) {
        List<CustomerContactRecordDTO> resultList = new ArrayList<CustomerContactRecordDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            if (StringUtils.isNotBlank(contactsCode)) {
                resultList = customerContactRecordService.queryContactRecord(customerCode, contactsCode);
            }
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);
        List<CustomerContactsDTO> contacts = customerContactsService.getCustomerContacts(customerCode);

        model.addAttribute("connectionWay", connectionWayMap);
        model.addAttribute("communicateType", communicateTypeMap);
        model.addAttribute("records", resultList);
        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("contacts", contacts);
        model.addAttribute("contactsCode", contactsCode);
        return "customer/contactRecord/list";
    }
    
    @RequestMapping(params = "method=getSeaRecords")
    public String getSea(HttpServletRequest request, Model model, String customerCode, String contactsCode) {
        List<CustomerContactRecordDTO> resultList = new ArrayList<CustomerContactRecordDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            if (StringUtils.isNotBlank(contactsCode)) {
                resultList = customerContactRecordService.queryContactRecord(customerCode, contactsCode);
            }
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);
        List<CustomerContactsDTO> contacts = customerContactsService.getCustomerContacts(customerCode);

        model.addAttribute("connectionWay", connectionWayMap);
        model.addAttribute("communicateType", communicateTypeMap);
        model.addAttribute("records", resultList);
        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("contacts", contacts);
        model.addAttribute("contactsCode", contactsCode);
        return "customersea/contactRecord/list";
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
    public String initModify(HttpServletRequest request, Model model, int id) {

        CustomerContactRecordDTO record = customerContactRecordService.getContactRecordById(id);
        if (record == null) {
            model.addAttribute("error", "联系记录不存在或已被删除！");
            return "customer/contactRecord/modify";
        }
        CustomerContactsDTO customerContacts = customerContactsService.getContacts(record.getContactsCode());
        if (customerContacts == null) {
            model.addAttribute("error", "联系人不存在或已被删除！");
            return "customer/contactRecord/modify";
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(record.getCustomerCode());

        model.addAttribute("connectionWay", connectionWayMap);
        model.addAttribute("communicateType", communicateTypeMap);
        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("customerContacts", customerContacts);
        model.addAttribute("contactRecord", record);
        return "customer/contactRecord/modify";
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
    public String modify(HttpServletRequest request, HttpServletResponse response, CustomerContactRecordVO contactRecord) {

        String message = null;
        if (contactRecord == null) {
            message = "联系记录不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(contactRecord.getCustomerCode())
                || StringUtils.isBlank(contactRecord.getContactsCode())
                || StringUtils.isBlank(contactRecord.getCommunicateType())
                || StringUtils.isBlank(contactRecord.getContactDate())
                || StringUtils.isBlank(contactRecord.getContents())
                || StringUtils.isBlank(contactRecord.getConnectionWay())) {
            message = "联系记录不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(contactRecord
                .getCustomerCode());
        if (customerInfo == null) {
            message = "客户信息不存在或已被删除，请确认客户是否存在！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (Constants.SAVE.equals(customerInfo.getInfoState())) {
            message = "客户信息未提交，无法新增联系记录，请先提交客户信息！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        CustomerContactsDTO customerContacts = customerContactsService.getContacts(contactRecord.getContactsCode());
        if (customerContacts == null) {
            message = "客户联系人不存在或已被删除，请确认客户联系人是否存在！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }
        
        if (Constants.SAVE.equals(customerContacts.getInfoState())) {
            message = "客户联系人未提交，无法新增联系记录，请先提交客户联系人信息！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(contactRecord.getInfoState())) {
            contactRecord.setInfoState(Constants.COMMIT);
        }

        CustomerContactRecordDTO newCustomerContactRecord = new CustomerContactRecordDTO();
        BeanUtils.copyProperties(contactRecord, newCustomerContactRecord);
        newCustomerContactRecord.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerContactRecordService.modifyContactRecord(newCustomerContactRecord);
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
            message = "需删除的联系记录不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = customerContactRecordService.delContactRecord(id);
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
