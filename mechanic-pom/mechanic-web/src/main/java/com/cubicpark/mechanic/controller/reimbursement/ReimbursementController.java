package com.cubicpark.mechanic.controller.reimbursement;

import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementDTO;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.domain.service.finance.IReimbursementService;
import com.cubicpark.mechanic.domain.service.procurement.IProcurementService;
import com.cubicpark.mechanic.util.AjaxUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 报销单
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/reimbursement")
public class ReimbursementController {

    @Autowired
    private IReimbursementService IReimbursementService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IProcurementService procurementService;

    /**
     * 〈一句话功能简述〉<br>
     * //初始化页面
     *
     * @param model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=index")
    public String index(Model model){
        List<ReimbursementDTO> reimbursementDTOList = IReimbursementService.findAll();
        for(ReimbursementDTO reimbursementDTO : reimbursementDTOList){
            IReimbursementService.updateCostById(reimbursementDTO.getCost(),reimbursementDTO.getId());
            EmployeeDTO employeeByCode = employeeService.getEmployeeByCode(reimbursementDTO.getEmployeeCode());
            reimbursementDTO.setEmployeeCode(employeeByCode.getName());
        }
        model.addAttribute("reimbursementDTOList",reimbursementDTOList);
        return "reimbursement/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 根据条件筛选
     *
     * @param codeLike, status, startEntryDate, endEntryDate, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=select")
    public String select( @RequestParam(value = "codeLike",required = false)String codeLike,
                          @RequestParam(value = "status",required = false) String status,
                          @RequestParam(value = "startEntryDate",required = false)String startEntryDate,
                          @RequestParam(value = "endEntryDate",required = false)String endEntryDate,
                          Model model){
        //查询列表数据
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Timestamp tt = new Timestamp(System.currentTimeMillis());
        if(StringUtils.isNotBlank(startEntryDate)){
            ts = Timestamp.valueOf(startEntryDate);
        }else {
            ts = null;
        }
        if(StringUtils.isNotBlank(endEntryDate)){
            tt = Timestamp.valueOf(endEntryDate);
        }else{
            tt = null;
        }
        List<ReimbursementDTO> reimbursementDTOList = IReimbursementService.selectByCodeOrStatusOrDate(codeLike,status,ts,tt);
        model.addAttribute("reimbursementDTOList",reimbursementDTOList);
        model.addAttribute("codeLike",codeLike);
        model.addAttribute("status",status);
        model.addAttribute("startEntryDate",startEntryDate);
        model.addAttribute("endEntryDate",endEntryDate);
        return "reimbursement/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * //跳到处理页面
     *
     * @param model, id, request
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initModify")
    public String initModify(Model model,Integer id,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        ReimbursementDTO reimbursementDTO = IReimbursementService.findById(id);
        if(StringUtils.isBlank(reimbursementDTO.getApprover())){
            return "reimbursement/needSelect";
        }
        if(!employeeDTO.getName().equals(reimbursementDTO.getApprover())){
            return "reimbursement/quanxian";
        }
        model.addAttribute("reimbursementDTO",reimbursementDTO);
        return "reimbursement/modify";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 财务提交报销成功
     *
     * @param reimbursementDTO, request, response
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=modify")
    public String modify(ReimbursementDTO reimbursementDTO, HttpServletRequest request, HttpServletResponse response){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        String employeeCode = employeeDTO.getName();
        int result = IReimbursementService.updateById(reimbursementDTO.getId(),reimbursementDTO.getState()
                                                    ,reimbursementDTO.getPayWay(),reimbursementDTO.getVoucher(),reimbursementDTO.getPayTime()
                                                    ,reimbursementDTO.getRemarks(),employeeCode);
        if(result > 0){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
            return null;
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败!");
        }
        return null;
    }


    /**
     * 〈一句话功能简述〉<br>
     * //财务提交报销拒绝
     *
     * @param reimbursementDTO, request, response
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=refuse")
    public String refuse(ReimbursementDTO reimbursementDTO, HttpServletRequest request, HttpServletResponse response){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        String employeeCode = employeeDTO.getName();
        int result = IReimbursementService.updateByIdRefuse(reimbursementDTO.getId(),reimbursementDTO.getState()
                ,reimbursementDTO.getRemarks(),employeeCode);
        if(result > 0){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
            return null;
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败!");
        }
        return null;
    }


    /**
     * 〈一句话功能简述〉<br>
     * //根据输入的值查询相似的Code
     *
     * @param code, response
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=search")
    public String search(@RequestParam("code") String code,HttpServletResponse response){
        List<String> codeList = IReimbursementService.searchExpressCodeLike(code);
        if(codeList.size() != 0){
            if(codeList.size() > 5){
                for(int i = 5 ; i <= codeList.size(); i++){
                    codeList.remove(i);
                }
            }
            AjaxUtil.ajaxJsonSucMessage(response,codeList);
            return null;
        }
        AjaxUtil.ajaxJsonSucMessage(response,null);
        return null;
    }


    /**
     * 〈一句话功能简述〉<br>
     *  //操作人员选择调试工单
     *
     * @param id, request, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params ="method=initdebug")
    public String initdebug(Integer id,HttpServletRequest request,Model model){
        EmployeeDTO employeeDTO = (EmployeeDTO)request.getSession().getAttribute("user");
        ReimbursementDTO reimbursementDTO = IReimbursementService.findById(id);
        if(!reimbursementDTO.getState().equals("0") || !org.springframework.util.StringUtils.isEmpty(reimbursementDTO.getApprover())){
            //说明工单正在处理中
            return "reimbursement/inHand";
        }
        model.addAttribute("id",id);
        model.addAttribute("employeeCode",employeeDTO.getEmployeeCode());
        model.addAttribute("username",employeeDTO.getName());
        model.addAttribute("applyNo",reimbursementDTO.getApplyNo());
        return "reimbursement/debug";
    }


    /**
     * 〈一句话功能简述〉<br>
     *  //确认选择工单
     *
     * @param response, id, username
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params ="method=debug")
    public String debug(HttpServletResponse response,@RequestParam("id")Integer id,@RequestParam("username")String username){
        int result = IReimbursementService.choiceDebugOrder(username,id);
        String msg = null;
        if(result > 0){
            msg = "200";
        }else {
            msg = "修改失败";
        }
        AjaxUtil.ajaxJsonSucMessage(response,msg);
        return null;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 采购处理页面
     *
     * @param model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=procurement")
    public String fundApplication(Model model){
        List<ProcurementDTO> procurementDTOList = procurementService.selectByStatusAndEmployeeCode(4,null); //4为资金申请处理
        model.addAttribute("procurementDTOList",procurementDTOList);
        return "procurement/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 财务采购拨款
     *
     * @param response, id
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=appropriation")
    public void appropriation(HttpServletResponse response,Integer id){
        ProcurementDTO procurementDTO = procurementService.selectById(id);
        procurementDTO.setStatus(5); //5为已拨款状态
        int low = procurementService.updateById(procurementDTO);
        if(low > 0){
            //跟新成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //跟新失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     *  //code转为name
     *
     * @param debugOrderDTOS
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.finance.ReimbursementDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private List<ReimbursementDTO> employeeCodeToName(List<ReimbursementDTO> debugOrderDTOS){
        List<ReimbursementDTO> debugOrderDTOS1 = new ArrayList<>();
        for(ReimbursementDTO debugOrderDTO : debugOrderDTOS){
            if(StringUtils.isNotBlank(debugOrderDTO.getEmployeeCode())){
                EmployeeDTO employeeDTO = employeeService.getEmployeeByCode(debugOrderDTO.getEmployeeCode());
                debugOrderDTO.setEmployeeCode(employeeDTO.getName());
            }
            debugOrderDTOS1.add(debugOrderDTO);
        }
        return debugOrderDTOS1;
    }
}
