package com.cubicpark.mechanic.controller.aftersale;

import com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.aftersale.IAfterSaleService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.util.AjaxUtil;
import org.apache.commons.lang3.StringUtils;
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
 * 售后服务
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/afterservice")
public class AfterSaleController {

    @Autowired
    private IAfterSaleService IAfterSaleService;

    @Autowired
    private IEmployeeService employeeService;


    /**
     * 〈一句话功能简述〉<br>
     * //跳转到列表页面,并赋值
     *
     * @param model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=index")
    public String index(Model model){
        List<AfterSaleDTOWithBLOBs> oneMouseAfterService = IAfterSaleService.findOneMouseDebugOrder();
        List<AfterSaleDTOWithBLOBs> afterServiceDTOWithBLOBs = this.employeeCodeToName(oneMouseAfterService);
        model.addAttribute("oneMouseAfterServiceList",afterServiceDTOWithBLOBs);
        return "aftersale/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 根据条件进行筛选
     *
     * @param codeLike, status, startEntryDate, endEntryDate, model]
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
        List<AfterSaleDTOWithBLOBs> expressDTOList = IAfterSaleService.selectByCodeOrStatusOrDate(codeLike,status,ts,tt);
        model.addAttribute("oneMouseAfterServiceList",expressDTOList);
        model.addAttribute("codeLike",codeLike);
        model.addAttribute("status",status);
        model.addAttribute("startEntryDate",startEntryDate);
        model.addAttribute("endEntryDate",endEntryDate);
        return "aftersale/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳到添加页面
     *
     * @param model, id, request]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initadd")
    public String initAdd(Model model,@RequestParam(value = "id",required = false) Integer id,HttpServletRequest request){
        if(id != null){
            //说明是更改
            AfterSaleDTOWithBLOBs serviceDTOWithBLOBs = IAfterSaleService.findById(id);
            EmployeeDTO employeeDTO = (EmployeeDTO)request.getSession().getAttribute("user");
            if(serviceDTOWithBLOBs.getStatus() >= 1){
                //说明已经被人处理
                if (!employeeDTO.getEmployeeCode().equals(serviceDTOWithBLOBs.getEmployeeCode())){
                    //如果code不一致,说明没有权限
                    return "aftersale/quanxian";
                }
            }else if(serviceDTOWithBLOBs.getStatus() == 0){
                return "aftersale/refuse";
            }
            model.addAttribute("afterServiceDTO",serviceDTOWithBLOBs);
        }
        return "aftersale/modify";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 更新页面
     *
     * @param afterServiceDTO, response]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=add")
    public String add(AfterSaleDTOWithBLOBs afterServiceDTO, HttpServletResponse response){
        if(afterServiceDTO == null){
            AjaxUtil.ajaxJsonSucMessage(response,"参数错误");
        }
        int result = 0;
        if(afterServiceDTO.getId() == null){
            //说明是新增的
            result = IAfterSaleService.create(afterServiceDTO);
        }else{
            //修改工单
            result = IAfterSaleService.update(afterServiceDTO);
        }

        if(result > 0){
            //插入成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"添加失败,请重新添加");
        }
        return null;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳到调试页面
     *
     * @param id, request, model]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    //操作人员选择调试工单
    @RequestMapping(params ="method=initdebug")
    public String initdebug(Integer id,HttpServletRequest request,Model model){
        EmployeeDTO employeeDTO = (EmployeeDTO)request.getSession().getAttribute("user");
        AfterSaleDTOWithBLOBs afterServiceDTO = IAfterSaleService.findById(id);
        if(afterServiceDTO.getStatus() >= 1 || afterServiceDTO.getEmployeeCode() != null){
            //说明工单正在处理中
            return "aftersale/inHand";
        }
        String afterServiceCode = afterServiceDTO.getAfterServiceCode();
        model.addAttribute("id",id);
        model.addAttribute("employeeCode",employeeDTO.getEmployeeCode());
        model.addAttribute("username",employeeDTO.getName());
        model.addAttribute("afterServiceCode",afterServiceCode);
        return "aftersale/debug";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 选择调试单
     *
     * @param response, id, employeeCode]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    //确认是够选择该工单
    @RequestMapping(params ="method=debug")
    public String debug(HttpServletResponse response,@RequestParam("id")Integer id,@RequestParam("employeeCode")String employeeCode){
        int result = IAfterSaleService.choiceDebugOrder(employeeCode,id);
        String message = "";
        if(result > 0){
            message = "200";
        }else {
            message = "修改失败";
        }
        AjaxUtil.ajaxJsonSucMessage(response,message);
        return null;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 删除售后服务单
     *
     * @param id, response, request]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    //删除工单
    @RequestMapping(params = "method=delete")
    public String delete(Integer id,HttpServletResponse response,HttpServletRequest request){
        if(id == null){
            AjaxUtil.ajaxJsonSucMessage(response,"参数错误");
        }
        AfterSaleDTOWithBLOBs serviceDTOWithBLOBs = IAfterSaleService.findById(id);
        EmployeeDTO employeeDTO = (EmployeeDTO)request.getSession().getAttribute("user");

        //说明已经被人处理
        //todo 只有售后人员才能删除
        if (!employeeDTO.getDepartmentCode().equals("D000000011")){
            //如果code不一致,说明没有权限
            AjaxUtil.ajaxJsonErrMessage(response,"对不起,只有售后部才可以删除该工单!");
            return null;
        }

        int result = IAfterSaleService.deleteById(id);
        String msg = "";
        if(result > 0){
            msg = "200";
        }else{
            msg = "删除失败!";
        }
        AjaxUtil.ajaxJsonSucMessage(response,msg);
        return null;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 查询相似的code
     *
     * @param code, response]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    //根据输入的值查询相似的expressCode
    @RequestMapping(params = "method=search")
    public String search(@RequestParam("code") String code,HttpServletResponse response){
        List<String> codeList = IAfterSaleService.searchCodeLike(code);
        if(codeList.size() != 0){
            if(codeList.size() > 5){
                for(int i = 5 ; i <= codeList.size(); i++){
                    codeList.remove(i);
                }
            }
            AjaxUtil.ajaxJsonSucMessage(response,codeList);
            return null;
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,null);
        }
        return null;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 将员工编号转为员工名字
     *
     * @param afterServiceDTOS
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private List<AfterSaleDTOWithBLOBs> employeeCodeToName(List<AfterSaleDTOWithBLOBs> afterServiceDTOS){
        List<AfterSaleDTOWithBLOBs> arrayList = new ArrayList<>();
        for(AfterSaleDTOWithBLOBs afterServiceDTO : afterServiceDTOS){
            if(StringUtils.isNotBlank(afterServiceDTO.getEmployeeCode())){
                EmployeeDTO employeeDTO = employeeService.getEmployeeByCode(afterServiceDTO.getEmployeeCode());
                afterServiceDTO.setEmployeeCode(employeeDTO.getName());
            }
            arrayList.add(afterServiceDTO);
        }
        return arrayList;
    }

}
