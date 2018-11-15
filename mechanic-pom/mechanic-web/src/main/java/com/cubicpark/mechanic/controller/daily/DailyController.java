package com.cubicpark.mechanic.controller.daily;

import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.daily.DailyDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.daily.IDailyService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.domain.service.express.IExpressService;
import com.cubicpark.mechanic.util.AjaxUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 日报管理
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/daily")
public class DailyController {

    @Autowired
    private IDailyService IDailyService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到添加页面
     *
     * @param request
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initAdd")
    public String index(HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        //查看今天日报有没有填写
        DailyDTO dailyDTO1 = IDailyService.selectTodayByEmployeeCode(employeeDTO.getEmployeeCode());
        if(dailyDTO1 != null){
            //如果填写了
            return "daily/hasSubmit";
        }
        return "daily/add";
    }

    /**
     * 添加当天日报
     * @param dailyDTO
     * @param response
     * @param request
     */
    @RequestMapping(params = "method=add")
    public void add(DailyDTO dailyDTO,  HttpServletResponse response,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        dailyDTO.setEmployeeCode(employeeDTO.getEmployeeCode());
        //获取他的部门
        DepartmentDTO departmentByCode = departmentService.getDepartmentByCode(employeeDTO.getDepartmentCode());
        dailyDTO.setDepartment(departmentByCode.getDepartmentName());
        int row = IDailyService.insert(dailyDTO);
        if(row > 0){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重新提交!");
        }
    }

    /**
     * 查询一个月内自己的日报列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(params = "method=self")
    public String self(Model model,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        List<DailyDTO> dailyDTOList = IDailyService.findOneMouthBefore(employeeDTO.getEmployeeCode());
        List<DailyDTO> dailyDTOList1 = this.employeeCodeToName(dailyDTOList);
        model.addAttribute("dailyDTOList",dailyDTOList1);
        return "daily/manage";
    }

    /**
     * 日报详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(params = "method=detail")
    public String detail(Integer id,Model model){
        DailyDTO dailyDTO = IDailyService.selectById(id);
        EmployeeDTO employeeByCode = employeeService.getEmployeeByCode(dailyDTO.getEmployeeCode());
        dailyDTO.setEmployeeCode(employeeByCode.getName());
        model.addAttribute("daily",dailyDTO);
        return "daily/detail";
    }

    /**
     * 查询当天所有的日报
     * @param model
     * @return
     */
    @RequestMapping(params = "method=all")
    public String all(Model model){
        List<DailyDTO> dailyDTOList = IDailyService.selectToday();
        List<DailyDTO> dailyDTOList1 = this.employeeCodeToName(dailyDTOList);
        model.addAttribute("dailyDTOList",dailyDTOList1);
        //显示的时间为今天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String time = format.format(date);
        model.addAttribute("time",time);
        return "daily/manage1";
    }

    /**
     * 根据日期查询自己的日报
     * @param request
     * @param model
     * @param time
     * @return
     */
    @RequestMapping(params = "method=selectMyself")
    public String selectMyself(HttpServletRequest request,Model model,@RequestParam(value = "time",required = false) String time){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        List<DailyDTO> dailyDTOList = IDailyService.selectMyselfByDate(employeeDTO.getEmployeeCode(),time);
        model.addAttribute("dailyDTOList",dailyDTOList);
        model.addAttribute("time",time);
        return "daily/manage";
    }

    /**
     * 查询相似的employeeCode
     * @param code
     * @param response
     */
    @RequestMapping(params = "method=search")
    public void searchCodeLike(String code,HttpServletResponse response){
        List<String> codeList = IDailyService.selectCodeLike(code);
        if(codeList.size() != 0){
            if(codeList.size() > 5){
                for(int i = 5 ; i <= codeList.size(); i++){
                    codeList.remove(i);
                }
            }
            AjaxUtil.ajaxJsonSucMessage(response,codeList);
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,null);
        }
    }

    /**
     * 根据输入的时间和code模糊查询
     * @param model
     * @param time
     * @param codeLike
     * @return
     */
    @RequestMapping(params = "method=select")
    public String selectByDateAndCodeLike(Model model, @RequestParam(value = "time",required = false) String time,
                                          @RequestParam(value = "codeLike",required = false)String codeLike){
        List<DailyDTO> dailyDTOList = IDailyService.selectByDateAndCodeLike(time, codeLike);
        model.addAttribute("dailyDTOList",dailyDTOList);
        model.addAttribute("time",time);
        model.addAttribute("codeLike",codeLike);
        return "daily/manage1";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 将员工编号转为员工名字
     *
     * @param dailyDTOList
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private List<DailyDTO> employeeCodeToName(List<DailyDTO> dailyDTOList){
        List<DailyDTO> arrayList = new ArrayList<>();
        for(DailyDTO dailyDTO : dailyDTOList){
            if(StringUtils.isNotBlank(dailyDTO.getEmployeeCode())){
                EmployeeDTO employeeDTO = employeeService.getEmployeeByCode(dailyDTO.getEmployeeCode());
                dailyDTO.setEmployeeCode(employeeDTO.getName());
            }
            arrayList.add(dailyDTO);
        }
        return arrayList;
    }
}
