
package com.cubicpark.mechanic.controller.express;

import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.fix.FixDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.express.ExpressDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.fix.IFixService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.domain.service.express.IExpressService;
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

@Controller
@RequestMapping("/express")
public class ExpressController {

    @Autowired
    private IExpressService expressService;
    @Autowired
    private IFixService debugOrderService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;

    //列表页
    @RequestMapping(params = "method=index")
    public String index(Model model,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        //列表数据
        List<ExpressDTO> expressDTOList = expressService.findOneMouseDebugOrder();
        List<ExpressDTO> expressDTOS = this.employeeCodeToName(expressDTOList);
        model.addAttribute("expressDTOList",expressDTOS);
        model.addAttribute("employeeDTO",employeeDTO);
        return "express/manage";
    }

    @RequestMapping(params = "method=select")
    public String select(HttpServletRequest request, @RequestParam(value = "codeLike",required = false)String codeLike,
                          @RequestParam(value = "status",required = false) Integer status,
                          @RequestParam(value = "startEntryDate",required = false)String startEntryDate,
                          @RequestParam(value = "endEntryDate",required = false)String endEntryDate,
                          Model model){

        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
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
        List<ExpressDTO> expressDTOList = expressService.selectByCodeOrStatusOrDate(codeLike,status,ts,tt);
        model.addAttribute("expressDTOList",expressDTOList);
        model.addAttribute("codeLike",codeLike);
        model.addAttribute("status",status);
        model.addAttribute("startEntryDate",startEntryDate);
        model.addAttribute("endEntryDate",endEntryDate);
        model.addAttribute("employeeDTO",employeeDTO);
        return "express/manage";
    }

    //跳到物流工单修改页面
    @RequestMapping(params = "method=initModify")
    public String initModify(Model model,Integer id,HttpServletRequest request){
        ExpressDTO expressDTO = expressService.findById(id);
        EmployeeDTO employeeDTO = (EmployeeDTO)request.getSession().getAttribute("user");
        if(expressDTO.getStatus() >= 1){
            //说明已经被人处理
            if (!employeeDTO.getEmployeeCode().equals(expressDTO.getEmployeeCode())){
                //如果code不一致,说明没有权限
                return "fix/quanxian";
            }
        }else if(expressDTO.getStatus() == 0 && StringUtils.isBlank(expressDTO.getEmployeeCode())){
            //工单还没有处理不能编辑
            return "fix/refuse";
        }
        model.addAttribute("expressDTO",expressDTO);
        return "express/modify";
    }

    //修改物流工单
    @RequestMapping(params = "method=modify")
    public String modify(ExpressDTO expressDTO, HttpServletResponse response){
       if(expressDTO == null){
            AjaxUtil.ajaxJsonSucMessage(response,"参数错误");
            return null;
       }
        ExpressDTO expressDTO1 = expressService.findById(expressDTO.getId());
       int result = expressService.updateById(expressDTO);
       String msg = null;
       if(result > 0){
          msg = "200";
//          String contractCode = expressDTO1.getContractCode();
//          expressDTO.setContractCode(expressDTO1.getContractCode());
//          expressDTO.setEmployeeCode(expressDTO1.getEmployeeCode());
//          FixDTO fixDTO = debugOrderService.selectByContractCode(contractCode);
           //说明没有重复的调试工单
//           if(fixDTO == null){
               //如果状态物流状态为发货状态,生成设备调试工单
//               if(expressDTO.getStatus() == 2 && expressDTO1.getStatus() == 1){
//                   debugOrderService.create(expressDTO);
//               }
//           }
       }else{
           msg = "修改失败";
       }
       AjaxUtil.ajaxJsonSucMessage(response,msg);
       return null;
    }

    //操作人员选择调试工单
    @RequestMapping(params ="method=initdebug")
    public String initdebug(Integer id,HttpServletRequest request,Model model){
        EmployeeDTO employeeDTO = (EmployeeDTO)request.getSession().getAttribute("user");
        ExpressDTO orderDTO = expressService.findById(id);
        if(orderDTO.getStatus() >= 1 || org.apache.commons.lang3.StringUtils.isNotBlank(orderDTO.getEmployeeCode())){
            //说明工单正在处理中
            return "fix/inHand";
        }
        model.addAttribute("id",id);
        model.addAttribute("employeeCode",employeeDTO.getEmployeeCode());
        model.addAttribute("username",employeeDTO.getName());
        model.addAttribute("expressCode",orderDTO.getExpressCode());
        return "express/debug";
    }

    //确认选择
    @RequestMapping(params ="method=debug")
    public String debug(HttpServletResponse response,@RequestParam("id")Integer id,@RequestParam("employeeCode")String employeeCode){
        int result = expressService.choiceDebugOrder(employeeCode,id);
        String msg = null;
        if(result > 0){
            msg = "200";
        }else {
            msg = "选择失败,请重试!";
        }
        AjaxUtil.ajaxJsonSucMessage(response,msg);
        return null;
    }

    //根据输入的值查询相似的expressCode
    @RequestMapping(params = "method=search")
    public String search(@RequestParam("code") String code,HttpServletResponse response){
        List<String> codeList = expressService.searchExpressCodeLike(code);
        if(codeList.size() != 0){
            if(codeList.size() > 5){
                for(int i = 5 ; i < codeList.size(); i++){
                    codeList.remove(i);
                }
            }
            AjaxUtil.ajaxJsonSucMessage(response,codeList);
            return null;
        }
        AjaxUtil.ajaxJsonSucMessage(response,null);
        return null;
    }

//    @RequestMapping(params = "method=receiveGoods")
//    public void receiveGoods(Integer id,HttpServletResponse response){
//        ExpressDTO expressDTO = expressService.findById(id);
//        expressDTO.setStatus(2); //2为已收货状态
//        expressDTO.setArriveTime(new Timestamp(System.currentTimeMillis()));
//
//        int result = expressService.updateById(expressDTO);
//        String msg = null;
//        if(result > 0){
//            msg = "200";
//            debugOrderService.create(expressDTO);
//        }else {
//            msg = "选择失败,请重试!";
//        }
//        AjaxUtil.ajaxJsonSucMessage(response,msg);
//    }

    @RequestMapping(params = "method=initConfirmFixTime")
    public String initConfirmFixTime(Model model,Integer id){
        List<DepartmentDTO> departmentDTOList = departmentService.getAll();

        model.addAttribute("departmentDTOList",departmentDTOList);
        model.addAttribute("expressId",id);
        return "express/selectHandler";
    }

    @RequestMapping(params = "method=confirmFixTime")
    public void confirmFixTime(HttpServletResponse response,Integer expressId,String handlerCode){
        ExpressDTO expressDTO = expressService.findById(expressId);
        EmployeeDTO employee = employeeService.getEmployeeByCode(handlerCode);

        //将状态改为已发货
        expressDTO.setStatus(2); //2为已发货状态
        //生成售后评价工单
        int row = debugOrderService.create(expressDTO, handlerCode,employee.getName());
        if(row > 0){
            expressService.updateById(expressDTO);
            //插入成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //插入失败
            AjaxUtil.ajaxJsonSucMessage(response,"失败,请重试");
        }
    }

    //code转为名字
    private List<ExpressDTO> employeeCodeToName(List<ExpressDTO> afterServiceDTOS){
        List<ExpressDTO> arrayList = new ArrayList<>();
        for(ExpressDTO afterServiceDTO : afterServiceDTOS){
            if(StringUtils.isNotBlank(afterServiceDTO.getEmployeeCode())){
                EmployeeDTO employeeDTO = employeeService.getEmployeeByCode(afterServiceDTO.getEmployeeCode());
                afterServiceDTO.setEmployeeCode(employeeDTO.getName());
            }
            arrayList.add(afterServiceDTO);
        }
        return arrayList;
    }
}
