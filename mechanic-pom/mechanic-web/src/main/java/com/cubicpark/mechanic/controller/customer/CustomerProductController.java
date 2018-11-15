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
import com.cubicpark.mechanic.domain.dto.customer.CustomerProductDTO;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerProductService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.CustomerProductVO;

@Controller
@RequestMapping("customerProduct")
public class CustomerProductController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerProductController.class);

    @Autowired
    private ICustomerInfoService customerInfoService;
    @Autowired
    private ICustomerProductService customerProductService;

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
        return "customer/product/add";
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
    public String add(HttpServletRequest request, HttpServletResponse response, CustomerProductVO customerProduct) {

        String message = null;
        if (customerProduct == null) {
            message = "客户产品不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(customerProduct.getCustomerCode()) || StringUtils.isBlank(customerProduct.getProductName())) {
            message = "客户产品不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }
        
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerProduct.getCustomerCode());
        if (Constants.SAVE.equals(customerInfo.getInfoState())) {
            message = "客户信息未提交，无法新增产品信息，请先提交客户信息！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(customerProduct.getInfoState())) {
            customerProduct.setInfoState(Constants.COMMIT);
        }

        CustomerProductDTO newCustomerProduct = new CustomerProductDTO();
        BeanUtils.copyProperties(customerProduct, newCustomerProduct);
        newCustomerProduct.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newCustomerProduct.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerProductService.addCustomerProduct(newCustomerProduct);
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

    @RequestMapping(params = "method=getCustomerProduct")
    public String getCustomerProduct(HttpServletRequest request, Model model, String customerCode) {
        List<CustomerProductDTO> resultList = new ArrayList<CustomerProductDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerProductService.getCustomerProducts(customerCode);
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);

        model.addAttribute("customerProducts", resultList);
        model.addAttribute("customerInfo", customerInfo);
        return "customer/product/list";
    }
    
    @RequestMapping(params = "method=getCustomerSeaProduct")
    public String getCustomerSeaProduct(HttpServletRequest request, Model model, String customerCode) {
        List<CustomerProductDTO> resultList = new ArrayList<CustomerProductDTO>();

        if (StringUtils.isBlank(customerCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            resultList = customerProductService.getCustomerProducts(customerCode);
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerCode);

        model.addAttribute("customerProducts", resultList);
        model.addAttribute("customerInfo", customerInfo);
        return "customersea/product/list";
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

        CustomerProductDTO customerProduct = customerProductService.getCustomerProduct(id);
        if (customerProduct == null) {
            model.addAttribute("error", "选中修改的产品不存在或已被删除！");
        }
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(customerProduct
                .getCustomerCode());

        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("customerProduct", customerProduct);
        return "customer/product/modify";
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
    public String modify(HttpServletRequest request, HttpServletResponse response, CustomerProductVO customerProduct) {

        String message = null;
        if (customerProduct == null) {
            message = "客户产品不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }
        
        if (customerProduct.getId() == 0 ||  StringUtils.isBlank(customerProduct.getCustomerCode()) || StringUtils.isBlank(customerProduct.getProductName())) {
            message = "客户产品不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (!Constants.SAVE.equals(customerProduct.getInfoState())) {
            customerProduct.setInfoState(Constants.COMMIT);
        }
        
        CustomerProductDTO newCustomerProduct = new CustomerProductDTO();
        BeanUtils.copyProperties(customerProduct, newCustomerProduct);
        newCustomerProduct.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = customerProductService.modifyCustomerProduct(newCustomerProduct);
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

        String result = customerProductService.delCustomerProduct(id);
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
