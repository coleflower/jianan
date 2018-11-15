package com.cubicpark.mechanic.controller.contract;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cubicpark.mechanic.domain.dto.design.Design;
import com.cubicpark.mechanic.domain.dto.production.ProductionDTO;
import com.cubicpark.mechanic.domain.service.design.IDesignService;
import com.cubicpark.mechanic.domain.service.production.IProductionService;
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
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.cubicpark.mechanic.vo.ContractInfoVO;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("contractInfo")
public class ContractInfoController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ContractInfoController.class);
    
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private ICustomerInfoService customerInfoService;
    @Autowired
    private IContractService contractService;
    @Autowired
    private IProductionService productionService;
    @Autowired
    private IDesignService designService;
    @Resource
    private Map<String, String> contractStateMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> fundStateMap = new HashMap<String, String>();

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
    public String initAdd(HttpServletRequest request, Model model,@RequestParam(value = "contractCode",required = false) String contractCode) {

        List<DepartmentDTO> departmentList = departmentService.getAll();

        if(org.apache.commons.lang3.StringUtils.isNotBlank(contractCode)){
            model.addAttribute("contractCode", contractCode);
        }

        model.addAttribute("departments", departmentList);
        model.addAttribute("contractStates", contractStateMap);
        model.addAttribute("fundStates", fundStateMap);
        return "contract/info/add";
    }

    /**
     * 
     * 功能描述: <br>
     * 新增客户信息
     *
     * @param request
     * @param response
     * @param contractInfo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=add")
    public String add(HttpServletRequest request, HttpServletResponse response, ContractInfoVO contractInfo) {

        EmployeeDTO employee = super.getEmployee(request);
        String message = null;
        if (employee == null) {
            message = "您的状态出现问题，请重新登录！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (contractInfo == null) {
            message = "合同信息不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (StringUtils.isBlank(contractInfo.getEmployeeCode()) || StringUtils.isBlank(contractInfo.getContractName())
                || StringUtils.isBlank(contractInfo.getCustomerCode())
                || StringUtils.isBlank(contractInfo.getDealDate()) || StringUtils.isBlank(contractInfo.getTotal())
                || StringUtils.isBlank(contractInfo.getDeliveryDate())
                || StringUtils.isBlank(contractInfo.getDeliveryInfo())
                || StringUtils.isBlank(contractInfo.getContacts())
                || StringUtils.isBlank(contractInfo.getDeliveryAdr())
                || StringUtils.isBlank(contractInfo.getPayMethod())
                || StringUtils.isBlank(contractInfo.getAdvanceRatio())
                || StringUtils.isBlank(contractInfo.getContractState())
                || StringUtils.isBlank(contractInfo.getFundState())) {
            message = "合同信息不完善，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (("100".equals(contractInfo.getAdvanceRatio()) && !Constants.FUND_STATE_NOPAY.equals(contractInfo
                .getFundState()))
                || (!"100".equals(contractInfo.getAdvanceRatio()) && !Constants.FUND_STATE_NOFIRSTPAY
                        .equals(contractInfo.getFundState()))) {
            message = "预付款比例对应的款项状态不匹配，请重新选择！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        // 判断合同备案号是否存在，判断备案号是否唯一
        if (StringUtils.isNotBlank(contractInfo.getRecordNumber())) {
            ContractDTO exitsContract = contractService.getContractByRecordNumber(contractInfo.getRecordNumber());
            if (exitsContract != null) {
                message = "存在相同备案号的合同，请确认后重试！";
                AjaxUtil.ajaxJsonSucMessage(response, message);
                return null;
            }
        }

        // 插入的是配件补发合同
        if(org.apache.commons.lang3.StringUtils.isNotBlank(contractInfo.getContractCode())){
            contractInfo.setParentContractCode(contractInfo.getContractCode());
        }

        String contractCode = MenthaCodeUtil.generateMenthaCode("contract", 17);
        contractInfo.setContractCode(contractCode);

        ContractDTO newContract = new ContractDTO();
        BeanUtils.copyProperties(contractInfo, newContract);
        newContract.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newContract.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = contractService.addContract(newContract);
        if (CommonErrorCode.SUCCESS.getValue().equals(resultInfo)) {
            // 新增成功
            message = CommonErrorCode.SUCCESS.getValue();

            //如果合同为生效状态,创建生产工单和设计工单
            createNewProtuction(newContract);
            createDesign(newContract);
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
     * @param contractCode
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=getContractInfo")
    public String getContractInfo(HttpServletRequest request, Model model, String contractCode) {
        ContractDTO contract = contractService.getContractByCode(contractCode);
        if (contract == null) {
            model.addAttribute("error", "选中合同不存在或已被删除！");
        }

        // 获取销售人员信息
        EmployeeDTO employee = employeeService.getEmployeeByCode(contract.getEmployeeCode());
        List<DepartmentDTO> allParentDepartment = null;
        if (employee != null) {
            DepartmentDTO department = departmentService.getDepartmentByCode(employee.getDepartmentCode());
            if (department != null) {
                allParentDepartment = departmentService.getGroupByPath(department.getParentPath());
                allParentDepartment.add(department);
            }
        }

        // 获取客户信息
        CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(contract.getCustomerCode());

        model.addAttribute("contractInfo", contract);
        model.addAttribute("allParentDepartment", allParentDepartment);
        model.addAttribute("employee", employee);
        model.addAttribute("customerInfo", customerInfo);
        model.addAttribute("contractStates", contractStateMap);
        model.addAttribute("fundStates", fundStateMap);
        return "contract/info/modify";
    }

    /**
     * 
     * 功能描述: <br>
     * 修改合同信息
     *
     * @param request
     * @param response
     * @param contractInfo
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(params = "method=modify")
    public String modify(HttpServletRequest request, HttpServletResponse response, ContractInfoVO contractInfo) {

        EmployeeDTO employee = super.getEmployee(request);
        ContractDTO currentContract = contractService.getContractByCode(contractInfo.getContractCode());

        String message = null;
        if (employee == null) {
            message = "您的状态出现问题，请重新登录！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        if (currentContract == null) {
            message = "合同不存在，请重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        // 待生效的合同，只有合同标的，预付款和款项状态可以修改，其他合同状态一概不准修改
        if (!Constants.CONTRACT_STATE_TOBEEFFECTIVE.equals(currentContract.getContractState())) {
            message = "当前合同状态非待生效，无法修改合同状态，请确认后重试！";
            AjaxUtil.ajaxJsonSucMessage(response, message);
            return null;
        }

        ContractDTO newContractInfo = new ContractDTO();
        newContractInfo.setId(contractInfo.getId());
        newContractInfo.setContractCode(contractInfo.getContractCode());
        newContractInfo.setDeliveryDate(contractInfo.getDeliveryDate());
        newContractInfo.setDeliveryInfo(contractInfo.getDeliveryInfo());
        newContractInfo.setContacts(contractInfo.getContacts());
        newContractInfo.setDeliveryAdr(contractInfo.getDeliveryAdr());
        newContractInfo.setPayMethod(contractInfo.getPayMethod());
        newContractInfo.setTotal(contractInfo.getTotal());
        newContractInfo.setAdvanceRatio(contractInfo.getAdvanceRatio());
        newContractInfo.setContractState(contractInfo.getContractState());
        newContractInfo.setFundState(contractInfo.getFundState());
        newContractInfo.setRemarks(contractInfo.getRemarks());
        newContractInfo.setProductType(contractInfo.getProductType());
        newContractInfo.setProductName(contractInfo.getProductName());
        newContractInfo.setProductCount(contractInfo.getProductCount());
        newContractInfo.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        String resultInfo = contractService.modifyContract(newContractInfo);
        if (CommonErrorCode.SUCCESS.getValue().equals(resultInfo)) {
            // 修改成功
            message = CommonErrorCode.SUCCESS.getValue();

            //如果合同为生效状态,创建生产工单和设计工单
            createNewProtuction(newContractInfo);
            createDesign(newContractInfo);

        } else {
            // 修改失败返回错误信息
            message = CommonErrorCode.getDescByValue(resultInfo);
        }

        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 生成生产工单
     *
     * @param newContract
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private void createNewProtuction(ContractDTO newContract){
        //如果合同为生效状态,创建生产工单
        if(newContract.getContractState().equals(Constants.CONTRACT_STATE_EFFECTIVE)){
            //判断生产工单有没有已经生成
            ProductionDTO productionDTO = productionService.selectByContractCode(newContract.getContractCode());
            if(productionDTO == null){
                //没有相同的产品合同,则生成生产工单
                ProductionDTO productionDTO1 = new ProductionDTO();

                productionDTO1.setProductType(newContract.getProductType());
                productionDTO1.setProductName(newContract.getProductName());
                productionDTO1.setProductCount(newContract.getProductCount());
                productionDTO1.setContractCode(newContract.getContractCode());
                productionDTO1.setStatus(0);
                productionService.insert(productionDTO1);
            }
        }
    }


    private void createDesign(ContractDTO newContract){
        //如果合同为生效状态,创建生产工单
        if(newContract.getContractState().equals(Constants.CONTRACT_STATE_EFFECTIVE)){
            //判断生产工单有没有已经生成
            Design design1 = designService.selectByContractCode(newContract.getContractCode());
            if(design1 == null){
                //没有相同的产品合同,则生成生产工单
                Design design = new Design();
                //设置设计单编号
                String designCode = MenthaCodeUtil.generateMenthaCode("design", 18);

                design.setDesignCode(designCode);
                design.setContractCode(newContract.getContractCode());
                design.setStatus(0); //0为待审核中状态

                designService.insert(design);
            }
        }
    }

}
