package com.cubicpark.mechanic.controller.contract;

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
import com.cubicpark.mechanic.common.helper.ContractErrorCode;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundCheckDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractFundDTO;
import com.cubicpark.mechanic.domain.service.contract.IContractFundService;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.ContractFundVO;

@Controller
@RequestMapping("contractFund")
public class ContractFundController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ContractFundController.class);

    @Autowired
    private IContractService contractService;
    @Autowired
    private IContractFundService contractFundService;
    @Resource
    private Map<String, String> fundTypeMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> payWayMap = new HashMap<String, String>();

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
    public String initAdd(HttpServletRequest request, Model model, String contractCode) {

        ContractDTO contract = contractService.getContractByCode(contractCode);
        if (contract == null) {
            model.addAttribute("error", "选中的合同不存在或已被删除！");
        }

        model.addAttribute("contractInfo", contract);
        model.addAttribute("fundTypes", fundTypeMap);
        model.addAttribute("payWays", payWayMap);
        return "contract/fund/add";
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
    public String add(HttpServletRequest request, HttpServletResponse response, ContractFundVO contractFund) {

        String message = null;
        ContractFundCheckDTO contractFundCheck = new ContractFundCheckDTO();

        if (contractFund == null) {
            message = "合同款项不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(contractFund.getContractCode()) || StringUtils.isBlank(contractFund.getItemName())
                || StringUtils.isBlank(contractFund.getFundType()) || StringUtils.isBlank(contractFund.getPayWay())
                || StringUtils.isBlank(contractFund.getFund())) {
            message = "合同款项信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        // 获取款项对应的合同信息
        ContractDTO contract = contractService.getContractByCode(contractFund.getContractCode());
        // 封装合同款项信息责任链模型
        contractFundCheck.setContractCode(contract.getContractCode());
        contractFundCheck.setTotal(contract.getTotal());
        contractFundCheck.setAdvanceRatio(contract.getAdvanceRatio());
        contractFundCheck.setContractState(contract.getContractState());
        contractFundCheck.setFundState(contract.getFundState());
        contractFundCheck.setSettleDate(contractFund.getSettleDate());
        // 判断当前操作是否为提交款项信息
        if (Constants.SAVE.equals(contractFund.getIsCommit())) {
            contractFundCheck.setCommit(false);
        } else {
            contractFundCheck.setCommit(true);
        }

        ContractFundDTO newContractFund = new ContractFundDTO();
        BeanUtils.copyProperties(contractFund, newContractFund);
        newContractFund.setInfoState(Constants.SAVE);// 设置默认信息状态
        newContractFund.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newContractFund.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        contractFundCheck.setContractFund(newContractFund);

        try {
            contractFundService.addContractFund(contractFundCheck);
        } catch (Exception e) {
            logger.error("add contract fund iteam error:"+e);
        }

        String resultInfo = contractFundCheck.getErrorCode();
        if (StringUtils.isBlank(resultInfo)) {
            // 新增成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 新增失败返回错误信息
            message = ContractErrorCode.getDescByValue(resultInfo);
        }

        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    @RequestMapping(params = "method=getContractFund")
    public String getContractFund(HttpServletRequest request, Model model, String contractCode) {
        List<ContractFundDTO> resultList = new ArrayList<ContractFundDTO>();
        ContractDTO contract = new ContractDTO();

        if (StringUtils.isBlank(contractCode)) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            contract = contractService.getContractByCode(contractCode);
            resultList = contractFundService.getContractFundByContractCode(contractCode);
        }

        model.addAttribute("contract", contract);
        model.addAttribute("contractFunds", resultList);
        model.addAttribute("fundTypes", fundTypeMap);
        model.addAttribute("payWays", payWayMap);
        return "contract/fund/list";
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

        ContractFundDTO contractFund = contractFundService.getContractFundByID(id);
        if (contractFund == null) {
            model.addAttribute("error", "选中修改的款项信息不存在或已被删除！");
        }

        ContractDTO contract = contractService.getContractByCode(contractFund.getContractCode());

        model.addAttribute("contractInfo", contract);
        model.addAttribute("contractFundInfo", contractFund);
        model.addAttribute("fundTypes", fundTypeMap);
        model.addAttribute("payWays", payWayMap);
        return "contract/fund/modify";
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
    public String modify(HttpServletRequest request, HttpServletResponse response, ContractFundVO contractFund) {

        String message = null;
        ContractFundCheckDTO contractFundCheck = new ContractFundCheckDTO();

        if (contractFund == null) {
            message = "合同款项不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(contractFund.getContractCode()) || StringUtils.isBlank(contractFund.getItemName())
                || StringUtils.isBlank(contractFund.getFundType()) || StringUtils.isBlank(contractFund.getPayWay())
                || StringUtils.isBlank(contractFund.getFund())) {
            message = "合同款项信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }
        
        // 获取款项对应的合同信息
        ContractDTO contract = contractService.getContractByCode(contractFund.getContractCode());
        
        // 封装合同款项信息责任链模型
        contractFundCheck.setContractCode(contract.getContractCode());
        contractFundCheck.setTotal(contract.getTotal());
        contractFundCheck.setAdvanceRatio(contract.getAdvanceRatio());
        contractFundCheck.setContractState(contract.getContractState());
        contractFundCheck.setFundState(contract.getFundState());
        contractFundCheck.setSettleDate(contractFund.getSettleDate());
        // 判断当前操作是否为提交款项信息
        if (Constants.SAVE.equals(contractFund.getIsCommit())) {
            contractFundCheck.setCommit(false);
        } else {
            contractFundCheck.setCommit(true);
        }

        ContractFundDTO newContractFund = new ContractFundDTO();
        BeanUtils.copyProperties(contractFund, newContractFund);
        newContractFund.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        contractFundCheck.setContractFund(newContractFund);
        
        try {
            contractFundService.modifyContractFund(contractFundCheck);
        } catch (Exception e) {
            logger.error("modify contract fund iteam error:"+e);
        }
    
        String resultInfo = contractFundCheck.getErrorCode();
        if (StringUtils.isBlank(resultInfo)) {
            // 修改成功
            message = CommonErrorCode.SUCCESS.getValue();
        } else {
            // 修改失败返回错误信息
            message = ContractErrorCode.getDescByValue(resultInfo);
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
            message = "需删除的款项信息有误，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        String result = contractFundService.delContractFund(id);
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
