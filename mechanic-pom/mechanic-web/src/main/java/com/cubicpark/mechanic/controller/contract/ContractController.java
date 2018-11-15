package com.cubicpark.mechanic.controller.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerInfoService;
import com.cubicpark.mechanic.vo.ContractInfoVO;
import com.cubicpark.mechanic.vo.ContractQueryVO;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("contract")
public class ContractController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private IContractService contractService;
    @Autowired
    private ICustomerInfoService customerInfoService;
    @Resource
    private Map<String, String> contractStateMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> fundStateMap = new HashMap<String, String>();
    @Resource
    private Map<String, String> fundTypeMap = new HashMap<String, String>();
    @Autowired
    private IDepartmentService departmentService;

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
    public String index(HttpServletRequest request, Model model, @ModelAttribute("query") ContractQueryVO query) {

        model.addAttribute("contractStates", contractStateMap);
        return "contract/manage";
    }

    @RequestMapping(params = "method=query")
    public String query(HttpServletRequest request, Model model, @ModelAttribute("query") ContractQueryVO query) {
        List<ContractInfoVO> result = new ArrayList<ContractInfoVO>();

        if (query == null) {
            model.addAttribute("error", "查询条件为空，请输入查询条件后进行查询！");
        } else {
            // 判断查询条件是否存在 前期不做限制 后期限制至少输入一组条件
            List<ContractDTO> resultList = new ArrayList<ContractDTO>();
            resultList = contractService.queryContract(query.getContractName(), query.getStartDealDate(),
                    query.getEndDealDate(), query.getContractState());
            
            if (resultList != null || !resultList.isEmpty()) {
                for(ContractDTO contract : resultList){
                    result.add(convert2VO(contract));
                }
                
                for(ContractInfoVO vo : result){
                    // 后期从缓存中读取
                    CustomerInfoDTO customerInfo = customerInfoService.getCustomerInfoByCustomerCode(vo.getCustomerCode());
                    if (customerInfo != null) {
                        vo.setCustomerName(customerInfo.getCustomerName());
                    }
                }
            }
        }

        model.addAttribute("fundTypes", fundTypeMap);
        model.addAttribute("fundStates", fundStateMap);
        model.addAttribute("contractStates", contractStateMap);
        model.addAttribute("contracts", result);
        return "contract/manage";
    }

    @RequestMapping(params = "method=selectContractByCustomerCode")
    public String selectContractByCustomerCode(@RequestParam("customercode") String customercode, Model model,@RequestParam(value = "bill",required = false) String bill){
        List<ContractDTO> contractDTOS = contractService.selectContractByCustomerCode(customercode);
        if(StringUtils.isNotBlank(bill)){
            //是开发票页面
            model.addAttribute("bill","billInterface");
        }
        model.addAttribute("customercode",customercode);
        model.addAttribute("contractDTOS",contractDTOS);
        return "contract/reissueParts/addone";
    }

    @RequestMapping(params = "method=selectContractByContractNameLike")
    public String selectContractByContractNameLike(Model model,String contractNameLike){
        List<ContractDTO> contractDTOS = contractService.selectContractByContractNameLike(contractNameLike);
        model.addAttribute("contractDTOS",contractDTOS);
        model.addAttribute("customerName",contractNameLike);
        return "contract/reissueParts/addone";
    }

    /**
     * 添加补发配件合同
     * @param model
     * @param contractCode
     * @param customercode
     * @return
     */
    @RequestMapping(params = "method=initAdd")
    public String initAdd(Model model,@RequestParam("contractCode") String contractCode,@RequestParam("customercode") String customercode){

        List<DepartmentDTO> departmentList = departmentService.getAll();

        model.addAttribute("contractCode",contractCode);
        model.addAttribute("customercode",customercode);
        model.addAttribute("departments", departmentList);
        model.addAttribute("contractStates", contractStateMap);
        model.addAttribute("fundStates", fundStateMap);
        return "contract/reissueParts/add";
    }
    
    private ContractInfoVO convert2VO(ContractDTO contract) {
        if (null == contract) {
            return null;
        }
        ContractInfoVO vo = new ContractInfoVO();
        BeanUtils.copyProperties(contract, vo);
        return vo;
    }
}
