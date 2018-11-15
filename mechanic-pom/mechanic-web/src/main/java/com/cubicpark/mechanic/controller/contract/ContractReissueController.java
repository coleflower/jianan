package com.cubicpark.mechanic.controller.contract;

import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.customer.CustomerInfoDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
import com.cubicpark.mechanic.domain.service.customer.ICustomerService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.util.AjaxObject;
import com.cubicpark.mechanic.util.AjaxUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("contractReissue")
public class ContractReissueController {

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IContractService contractService;

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(params = "method=reissue")
    public String reissue(Model model, HttpServletRequest request,@RequestParam(value = "bill",required = false) String bill) {
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        List<CustomerInfoDTO> customerInfoDTOList = customerService.getCustomerByEmployeeCode(employeeDTO.getEmployeeCode());
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        List<EmployeeDTO> employeeDTOList = employeeService.selectByDepartmentCode(employeeDTO.getDepartmentCode());

        //开票界面
        if(StringUtils.isNotBlank(bill)){
            model.addAttribute("billInterface","billInterface");
        }
        model.addAttribute("customerInfoDTOList", customerInfoDTOList);
        model.addAttribute("departments", departmentDTOS);
        model.addAttribute("employeeDTO", employeeDTO);
        model.addAttribute("employeeDTOList",employeeDTOList);

        return "contract/reissueParts/customerInfo";
    }

    @RequestMapping(params = "method=addReissueContract")
    public String addReissueContract(Model model, String contractcode) {
        model.addAttribute("contractCode", contractcode);
        return "contract/info/add";
    }

    @RequestMapping(params = "method=selectReissueContractByContractCode")
    public String selectReissueContractByContractCode(Model model, String contractCode) {
        List<ContractDTO> contractDTOList = contractService.getReissueContractByContractCode(contractCode);
        List<ContractDTO> contractDTOS = contractDTOList.stream().filter(c -> c.getParentContractCode() != null).collect(Collectors.toList());
        model.addAttribute("contractDTOS", contractDTOS);
        return "contract/reissueParts/list";
    }

    @RequestMapping(params = "method=selectCustomer")
    public String selectCustomer(@RequestParam(value = "customerName", required = false) String customerName,
                               @RequestParam(value = "employeeCode") String employeeCode, @RequestParam(value = "departmentCode") String departmentCode, Model model) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeByCode(employeeCode);
        List<CustomerInfoDTO> customerInfoDTOList = customerService.selectCustomer(customerName, employeeCode);
        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        List<EmployeeDTO> employeeDTOList = employeeService.selectByDepartmentCode(departmentCode);

        model.addAttribute("customerInfoDTOList",customerInfoDTOList);
        model.addAttribute("employeeDTO",employeeDTO);
        model.addAttribute("customerName",customerName);
        model.addAttribute("departments",departmentDTOS);
        model.addAttribute("employeeDTOList",employeeDTOList);

        return "contract/reissueParts/customerInfo";
    }
}
