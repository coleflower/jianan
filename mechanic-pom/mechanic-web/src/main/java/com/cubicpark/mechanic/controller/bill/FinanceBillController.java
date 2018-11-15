package com.cubicpark.mechanic.controller.bill;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.contract.ContractDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.finance.FinanceBillDTO;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.bill.IFinanceBillService;
import com.cubicpark.mechanic.domain.service.contract.IContractService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 开票
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/bill")
public class FinanceBillController {

    @Autowired
    private IFinanceBillService financeBillService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IContractService contractService;

    @Autowired
    private IDepartmentService departmentService;

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到添加页面
     *
     * @param request, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initAdd")
    public String initAdd(HttpServletRequest request, Model model){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        String[] contractCodeList = financeBillService.selectContractByEmployeeCode(employeeDTO.getEmployeeCode());
        model.addAttribute("contractCodeList",contractCodeList);
        return "bill/add";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 添加开票单
     *
     * @param financeBillDTO, response, request
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=add")
    public void add(FinanceBillDTO financeBillDTO,HttpServletResponse response,HttpServletRequest request){
        int insert = 0;
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        if(org.springframework.util.StringUtils.isEmpty(financeBillDTO.getId())){
            //说明是新增的
            //生成code
            String financebillCode = MenthaCodeUtil.generateMenthaCode("financebill", 18);

            financeBillDTO.setFinanceBillCode(financebillCode);
            financeBillDTO.setProposer(employeeDTO.getEmployeeCode());
            financeBillDTO.setProposerName(employeeDTO.getName());
            insert = financeBillService.insert(financeBillDTO);
        }else{
            //说明是修改的
            FinanceBillDTO financeBillDTO1 = financeBillService.selectById(financeBillDTO.getId());
            financeBillDTO.setCustomerCode(financeBillDTO1.getCustomerCode());
            financeBillDTO.setProposer(financeBillDTO1.getProposer());
            financeBillDTO.setStatus("待开票");
            financeBillDTO.setCreateDate(financeBillDTO1.getCreateDate());
            insert = financeBillService.updateById(financeBillDTO);
        }

        String msg;
        if(insert > 0){
            msg = "200";
            AjaxUtil.ajaxJsonSucMessage(response,msg);
        }else {
            msg = "提交失败";
            AjaxUtil.ajaxJsonSucMessage(response,msg);
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 开票列表页面
     *
     * @param model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=index")
    public String index(Model model){
        List<FinanceBillDTO> financeBillDTOS = financeBillService.selectUnprocessed();
        List<FinanceBillDTO> financeBillDTOList = this.employeeCodeToName(financeBillDTOS);
        model.addAttribute("financeBillDTOS",financeBillDTOList);
        //默认显示待开票状态的发票
        model.addAttribute("status",1);
        return "bill/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * //跳转到展示页面
     *
     * @param contractCode, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=look")
    public String look(String contractCode,Model model){
        List<FinanceBillDTO> financeBillDTOS = financeBillService.selectByContractCode(contractCode);
        List<FinanceBillDTO> financeBillDTOList = this.employeeCodeToName(financeBillDTOS);
        model.addAttribute("financeBillDTOS",financeBillDTOList);
        return "bill/look";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到发票审核成功页面
     *
     * @param id, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initSuccess")
    public String initSuccess(Integer id,Model model){
        model.addAttribute("id",id);
        return "bill/movie";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 提交发票审核成功
     *
     * @param id, icon, remark, response, request
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=success")
    public void success(Integer id, String icon, @RequestParam(value = "remark",required = false) String remark, HttpServletResponse response, HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        FinanceBillDTO financeBillDTO = financeBillService.selectById(id);
        financeBillDTO.setEmployeeCode(employeeDTO.getName());
        financeBillDTO.setIcon(icon);
        financeBillDTO.setRemark(remark);
        financeBillDTO.setStatus("已开票");
        int result = financeBillService.updateById(financeBillDTO);
        if(result > 0 ){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"开票失败,请重试!");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到发票审核失败页面
     *
     * @param id, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initFail")
    public String initFail(Integer id,Model model){
        model.addAttribute("id",id);
        return "bill/fail";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 提交发票审核失败
     *
     * @param id, remark, response, request
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=fail")
    public void fail(Integer id,String remark,HttpServletResponse response,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        FinanceBillDTO financeBillDTO = financeBillService.selectById(id);
        financeBillDTO.setEmployeeCode(employeeDTO.getName());
        financeBillDTO.setRemark(remark);
        financeBillDTO.setStatus("申请不通过");
        int result = financeBillService.updateById(financeBillDTO);
        if(result > 0 ){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"请求失败,请重试!");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 提交发票作废
     *
     * @param id, request, response
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=cancel")
    public void cancel(Integer id,HttpServletRequest request,HttpServletResponse response){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        FinanceBillDTO financeBillDTO = financeBillService.selectById(id);
        financeBillDTO.setEmployeeCode(employeeDTO.getName());
        financeBillDTO.setStatus("作废");
        int result = financeBillService.updateById(financeBillDTO);
        if(result > 0 ){
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            AjaxUtil.ajaxJsonSucMessage(response,"请求失败,请重试!");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 查询自己申请的发票
     *
     * @param model, request
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=myBill")
    public String myBill(Model model,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        //取出该用的employeeCode查询自己的申请发票记录

        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        List<EmployeeDTO> employeeDTOList = employeeService.selectByDepartmentCode(employeeDTO.getDepartmentCode());

        model.addAttribute("departments", departmentDTOS);
        model.addAttribute("employeeDTO", employeeDTO);
        model.addAttribute("employeeDTOList",employeeDTOList);
        return "bill/list";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 看视频页面
     *
     * @param icon, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=lookMovie")
    public String lookMovie(String icon,Model model){
        model.addAttribute("icon",icon);
        return "bill/lookMovie";
    }

    @RequestMapping(params = "method=selectBill")
    public String selectBill(Model model,@RequestParam("departmentCode") String departmentCode,
                             @RequestParam("employeeCode")String employeeCode,
                             @RequestParam(value = "status",required = false)String status,
                             @RequestParam(value = "startTime",required = false)String startTime,
                             @RequestParam(value = "endTime",required = false)String endTime){

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Timestamp tt = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(org.apache.commons.lang3.StringUtils.isNotBlank(startTime)){
            startTime = startTime+" 00:00:00";
            ts = Timestamp.valueOf(startTime);
        }else {
            ts = null;
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(endTime)){
            endTime = endTime+" 00:00:00";
            tt = Timestamp.valueOf(endTime);
        }else{
            tt = null;
        }

        List<DepartmentDTO> departmentDTOS = departmentService.getAll();
        List<EmployeeDTO> employeeDTOList = employeeService.selectByDepartmentCode(departmentCode);
        DepartmentDTO departmentDTO = departmentService.getDepartmentByCode(departmentCode);
        EmployeeDTO employeeDTO = employeeService.getEmployeeByCode(employeeCode);
        List<FinanceBillDTO> financeBillDTOList = financeBillService.selectByDepartmentCodeOrEmployeeCodeOrStatusOrDate(employeeCode,status,ts,tt);

        model.addAttribute("financeBillDTOList",financeBillDTOList);
        model.addAttribute("departmentDTO",departmentDTO);
        model.addAttribute("employeeDTO",employeeDTO);
        model.addAttribute("employeeDTOList",employeeDTOList);
        model.addAttribute("departments",departmentDTOS);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);

        return "bill/list";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 跳转到修改页面
     *
     * @param id, model, request
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initEdit")
    public String initEdit(Integer id,Model model,HttpServletRequest request){
        //所有的合同编号
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        String[] contractCodeList = financeBillService.selectContractByEmployeeCode(employeeDTO.getEmployeeCode());
        model.addAttribute("contractCodeS",contractCodeList);
        //查询发票单
        FinanceBillDTO financeBillDTO = financeBillService.selectById(id);
        model.addAttribute("financeBillDTO",financeBillDTO);
        return "bill/add";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 发票详情页面
     *
     * @param id, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=detail")
    public String detail(Integer id,Model model){
        FinanceBillDTO financeBillDTO = financeBillService.selectById(id);
        model.addAttribute("financeBillDTO",financeBillDTO);
        return "bill/myDetail";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 查询相似的code
     *
     * @param code, response
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    //根据输入的值查询相似的expressCode
    @RequestMapping(params = "method=search")
    public String search(@RequestParam("code") String code,HttpServletResponse response){
        List<String> codeList = financeBillService.searchCodeLike(code);
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
     * 根据条件进行筛选
     *
     * @param codeLike, status, startEntryDate, endEntryDate, model
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
        if(org.apache.commons.lang3.StringUtils.isNotBlank(startEntryDate)){
            ts = Timestamp.valueOf(startEntryDate);
        }else {
            ts = null;
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(endEntryDate)){
            tt = Timestamp.valueOf(endEntryDate);
        }else{
            tt = null;
        }
        List<FinanceBillDTO> expressDTOList = financeBillService.selectByCodeOrStatusOrDate(codeLike,status,ts,tt);
        model.addAttribute("financeBillDTOS",expressDTOList);
        model.addAttribute("codeLike",codeLike);
        model.addAttribute("status",status);
        model.addAttribute("startEntryDate",startEntryDate);
        model.addAttribute("endEntryDate",endEntryDate);
        return "bill/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 合同开票详情
     *
     * @param model, contractCode
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=selectBillByContractCode")
    public String selectBillByContractCode(Model model,String contractCode){
        List<FinanceBillDTO> financeBillDTOList = financeBillService.selectByContractCodeAndStatus(contractCode, "已开票");
        model.addAttribute("financeBillDTOList",financeBillDTOList);
        ContractDTO contractByCode = contractService.getContractByCode(contractCode);
        model.addAttribute("contractCode",contractByCode.getContractName());
        return "contract/bill/list";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 将员工编号转为员工名字
     *
     * @param financeBillDTOS
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.aftersale.AfterSaleDTOWithBLOBs>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private List<FinanceBillDTO> employeeCodeToName(List<FinanceBillDTO> financeBillDTOS){
        List<FinanceBillDTO> arrayList = new ArrayList<>();
        for(FinanceBillDTO financeBillDTO : financeBillDTOS){
            if(StringUtils.isNotBlank(financeBillDTO.getProposer())){
                EmployeeDTO employeeDTO = employeeService.getEmployeeByCode(financeBillDTO.getProposer());
                financeBillDTO.setProposer(employeeDTO.getName());
            }
            arrayList.add(financeBillDTO);
        }
        return arrayList;
    }

}
