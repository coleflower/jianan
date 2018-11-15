package com.cubicpark.mechanic.controller.fix;

import com.cubicpark.mechanic.domain.dto.fix.FixDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.fix.IFixService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
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
 * 调试工单
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/debugorder")
public class FixController {

    @Autowired
    private IFixService debugOrderService;
    @Autowired
    private IEmployeeService employeeService;

    /**
     * 〈一句话功能简述〉<br>
     * 跳到列表页面
     *
     * @param [model]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params ="method=index")
    public String index(Model model){
        List<FixDTO> fixDTOList = debugOrderService.findOneMouseDebugOrder();
        List<FixDTO> fixDTOS = this.employeeCodeToName(fixDTOList);
        model.addAttribute("fixDTOS", fixDTOS);
        return "fix/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 根据查询条件筛选
     *
     * @param [codeLike, status, startEntryDate, endEntryDate, model]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=select")
    public String select( @RequestParam(value = "codeLike",required = false)String codeLike,
                          @RequestParam(value = "status",required = false) Integer status,
                          @RequestParam(value = "startEntryDate",required = false)String startEntryDate,
                          @RequestParam(value = "endEntryDate",required = false)String endEntryDate,
                          Model model){
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
        List<FixDTO> fixDTOList = debugOrderService.selectByCodeOrStatusOrDate(codeLike,status,ts,tt);
        model.addAttribute("fixDTOS", fixDTOList);
        model.addAttribute("codeLike",codeLike);
        model.addAttribute("status",status);
        model.addAttribute("startEntryDate",startEntryDate);
        model.addAttribute("endEntryDate",endEntryDate);
        return "fix/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到修改页面
     *
     * @param [model, id, request]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params ="method=initModify")
    public String initModify(Model model,Integer id,HttpServletRequest request){
        FixDTO debugOrder = debugOrderService.findById(id);
        EmployeeDTO employeeDTO = (EmployeeDTO)request.getSession().getAttribute("user");
        if(debugOrder.getStatus() >= 1){
            //说明已经被人处理
            if (!employeeDTO.getEmployeeCode().equals(debugOrder.getEmployeeCode())){
                //如果code不一致,说明没有权限
                return "fix/quanxian";
            }
        }else if(debugOrder.getStatus() == 0 && StringUtils.isBlank(debugOrder.getEmployeeCode())){
            return "fix/refuse";
        }
        model.addAttribute("debugOrder",debugOrder);
        return "fix/modify";
    }


    /**
     * 〈一句话功能简述〉<br>
     *  //修改操作
     *
     * @param [fixDTO, response]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params ="method=modify")
    public String modify(FixDTO fixDTO, HttpServletResponse response){
        if(fixDTO == null){
            AjaxUtil.ajaxJsonSucMessage(response,"参数错误");
            return null;
        }
        int result = debugOrderService.update(fixDTO);
        String msg = "";
        if(result > 0){
            msg = "200";
        }else {
            msg = "处理失败,请重试!";
        }
        AjaxUtil.ajaxJsonSucMessage(response,msg);
        return null;
    }


    /**
     * 〈一句话功能简述〉<br>
     * //操作人员选择调试工单
     *
     * @param [id, request, model]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params ="method=initdebug")
    public String initdebug(Integer id,HttpServletRequest request,Model model){
        EmployeeDTO employeeDTO = (EmployeeDTO)request.getSession().getAttribute("user");
        FixDTO orderDTO = debugOrderService.findById(id);
        if(orderDTO.getStatus() >= 1 || org.apache.commons.lang3.StringUtils.isNotBlank(orderDTO.getEmployeeCode())){
            //说明工单正在处理中
            return "fix/inHand";
        }
        model.addAttribute("id",id);
        model.addAttribute("employeeCode",employeeDTO.getEmployeeCode());
        model.addAttribute("username",employeeDTO.getName());
        model.addAttribute("debugOrderCode",orderDTO.getDebugOrderCode());
        return "fix/debug";
    }


    /**
     * 〈一句话功能简述〉<br>
     * //确认选择
     *
     * @param [response, id, employeeCode]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params ="method=debug")
    public String debug(HttpServletResponse response,@RequestParam("id")Integer id,@RequestParam("employeeCode")String employeeCode){
        int result = debugOrderService.choiceDebugOrder(employeeCode,id);
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
     * //根据输入的值查询相似的expressCode
     *
     * @param [code, response]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=search")
    public String search(@RequestParam("code") String code,HttpServletResponse response){
        List<String> codeList = debugOrderService.searchExpressCodeLike(code);
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
     * 管理自己要设置时间的调试工单
     *
     * @param model, request
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=selectMyFix")
    public String selectMyFix(Model model,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        List<FixDTO> fixDTOList = debugOrderService.selectByEmployeeCodeAndFixTime(employeeDTO.getEmployeeCode());
        model.addAttribute("fixDTOList",fixDTOList);

        return "fix/confirmFixTime";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 选择时间页面
     *
     * @param model, id
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initSelectFixTime")
    public String initSelectFixTime(Model model,Integer id){
        model.addAttribute("fixId",id);
        return "fix/selectTime";
    }

    @RequestMapping(params = "method=selectTime")
    public void selectTime(Integer id,String fixTime,HttpServletResponse response){
        FixDTO fixDTO = debugOrderService.findById(id);
        Timestamp tt = new Timestamp(System.currentTimeMillis());
        if(StringUtils.isNotBlank(fixTime)){
            tt = Timestamp.valueOf(fixTime);
        }else {
            tt = null;
        }
        fixDTO.setFixTime(tt);

        int update = debugOrderService.update(fixDTO);
        if(update > 0){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else {
            AjaxUtil.ajaxJsonSucMessage(response,"失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 员工编号转为员工姓名
     *
     * @param fixDTOS
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.fix.FixDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private List<FixDTO> employeeCodeToName(List<FixDTO> fixDTOS){
        List<FixDTO> fixDTOS1 = new ArrayList<FixDTO>();
        for(FixDTO fixDTO : fixDTOS){
            if(StringUtils.isNotBlank(fixDTO.getEmployeeCode())){
                EmployeeDTO employeeDTO = employeeService.getEmployeeByCode(fixDTO.getEmployeeCode());
                fixDTO.setEmployeeCode(employeeDTO.getName());
            }
            fixDTOS1.add(fixDTO);
        }
        return fixDTOS1;
    }

}
