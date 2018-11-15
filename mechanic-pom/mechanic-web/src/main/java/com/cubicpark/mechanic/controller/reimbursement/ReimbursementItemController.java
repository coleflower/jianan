package com.cubicpark.mechanic.controller.reimbursement;

import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO;
import com.cubicpark.mechanic.domain.dto.finance.ReimbursementItemDTO;
import com.cubicpark.mechanic.domain.service.finance.IReimbursementItemService;
import com.cubicpark.mechanic.domain.service.finance.IReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 报销明细
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/reimbursementItem")
public class ReimbursementItemController {

    @Autowired
    private IReimbursementItemService IReimbursementItemService;

    @Autowired
    private IReimbursementService IReimbursementService;

    /**
     * 〈一句话功能简述〉<br>
     * 查看报销详情
     *
     * @param [applyNo, model, request]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=index")
    public String lookList(@RequestParam("applyNo") String applyNo, Model model, HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        ReimbursementDTO reimbursementDTO = IReimbursementService.selectByApplyNo(applyNo);
        if(StringUtils.isEmpty(reimbursementDTO.getApprover())){
            return "reimbursement/error";
        }
        if(!employeeDTO.getName().equals(reimbursementDTO.getApprover())){
            return "reimbursement/quanxian";
        }
        List<ReimbursementItemDTO> reimbursementItemDTOS = IReimbursementItemService.selectByApplyNo(applyNo);
        if(reimbursementItemDTOS.size() == 0){
            return "reimbursement/empty";
        }
        model.addAttribute("reimbursementItemDTOS",reimbursementItemDTOS);
        return "reimbursement/look";
    }
}
