package com.cubicpark.mechanic.controller.procurement;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.domain.dto.assessor.Assessor;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementDTO;
import com.cubicpark.mechanic.domain.dto.procurement.ProcurementGoodsDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductMaterialsDTO;
import com.cubicpark.mechanic.domain.dto.production.ProductionDTO;
import com.cubicpark.mechanic.domain.service.assessor.IAssessorService;
import com.cubicpark.mechanic.domain.service.base.IDepartmentService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.domain.service.procurement.IProcurementGoodsService;
import com.cubicpark.mechanic.domain.service.procurement.IProcurementService;
import com.cubicpark.mechanic.domain.service.production.IProductMaterialsService;
import com.cubicpark.mechanic.domain.service.production.IProductionService;
import com.cubicpark.mechanic.util.AjaxUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br>
 * 采购申请controller
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/procurement")
public class ProcurementController  {

    @Autowired
    private IProcurementService procurementService;

    @Autowired
    private IProductMaterialsService productMaterialsService;

    @Autowired
    private IProductionService productionService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IAssessorService assessorService;

    @Autowired
    private IProcurementGoodsService procurementGoodsService;

    /**
     * 新增一个采购工单
     * @param response
     * @param count
     * @param productionMaterialId
     */
//    @RequestMapping(params = "method=insertProcurement")
//    public void insertProcurement(HttpServletResponse response, @RequestParam("count") Integer count, @RequestParam("productionMaterialId") Integer productionMaterialId){
//        ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(productionMaterialId);
//
//        int row = procurementService.insert(count, productMaterialsDTO);
//        if(row > 0){
//            //添加成功
//            productMaterialsDTO.setProcurementStatus(2); // 2为采购状态
//            productMaterialsService.updateById(productMaterialsDTO);
//            AjaxUtil.ajaxJsonSucMessage(response,"200");
//        }else{
//            //添加失败
//            AjaxUtil.ajaxJsonSucMessage(response,"提交成功,请重试!");
//        }
//    }

    /**
     * 跳到待处理页面
     * @param model
     * @return
     */
    @RequestMapping(params = "method=index")
    public String index(Model model){
        List<ProcurementDTO> procurementDTOList = procurementService.selectByStatusAndEmployeeCode(0,null); //0为待处理状态

        model.addAttribute("procurementDTOList",procurementDTOList);
        return "procurement/manage";
    }

    /**
     * 选择处理人页面
     * @param model,id
     * @return
     */
    @RequestMapping(params = "method=initChooseHandler")
    public String  initProcurementCheck(Model model,Integer id){
        List<DepartmentDTO> departmentDTOList = departmentService.getAll();
        List<Assessor> assessors = assessorService.selectByProcurementId(id);
        for(Assessor assessor : assessors){
            EmployeeDTO employeeByCode = employeeService.getEmployeeByCode(assessor.getEmployeeCode());
            assessor.setEmployeeCode(employeeByCode.getName());
        }
        model.addAttribute("assessorList",assessors);
        model.addAttribute("departmentDTOList",departmentDTOList);
        model.addAttribute("procurementId",id);
        return "procurement/selectHandler";
    }

    /**
     * 选择处理人
     * @param departmentCode,response
     * @return
     */
    @RequestMapping(params = "method=selectEmployee")
    public void selectEmployee(String departmentCode,HttpServletResponse response){
        List<EmployeeDTO> employeeList = employeeService.getEmployeeByDepartmentCode(departmentCode);
        AjaxUtil.ajaxJsonSucMessage(response,employeeList);
    }

    /**
     * 〈一句话功能简述〉<br>
     * 添加审核人
     *
     * @param assessor, response
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=addAssessor")
    public void addAssessor(Assessor assessor, HttpServletResponse response){

        List<Assessor> assessorList = assessorService.selectByProcurementId(assessor.getProcurementId());
        if(assessorList.size() == 0){
            //是第一个添加的审核人
            assessor.setSort(1); //审核人1
        }else{
            assessor.setSort(assessorList.size()+1);
        }

        assessor.setType(0); //普通审核

        for(Assessor assessor1 : assessorList){
            if(assessor1.getDepartmentCode().equals(assessor.getDepartmentCode()) && assessor1.getEmployeeCode().equals(assessor.getEmployeeCode())){
                //有重复的审核人
                AjaxUtil.ajaxJsonErrMessage(response,"审核人不能重复");
                return;
            }
        }

        boolean insert = assessorService.insert(assessor);
        if(insert){
            //添加成功
            EmployeeDTO employeeByCode = employeeService.getEmployeeByCode(assessor.getEmployeeCode());
            assessor.setEmployeeCode(employeeByCode.getName());
            AjaxUtil.ajaxJsonSucMessage(response, assessor);
        }else{
            //添加失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 删除审核人
     *
     * @param id,response
     * @return void
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=delAssessor")
    public void delAssessor(Integer id,HttpServletResponse response){
        Assessor assessor = assessorService.selectById(id);
        List<Assessor> assessors = assessorService.selectByProcurementId(assessor.getProcurementId());
        boolean delete = assessorService.deleteById(id);
        if(delete){
            //删除成功
            //将后面的序号依次向前推一个
            if(assessor.getSort() != assessors.size()){
                for(int i = assessor.getSort();i<assessors.size();i++){
                    Assessor assessor1 = assessorService.selectByProcurementIdAndSort(assessor.getProcurementId(), i+1);
                    assessor1.setSort(i);
                    assessorService.updateById(assessor1);
                }
            }
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //删除失败
            AjaxUtil.ajaxJsonErrMessage(response,"删除失败,请重试");
        }
    }

    /**
     * 选择处理人提交
     * @param response,handlerCode,procurementId
     * @param handlerCode
     */
    @RequestMapping(params = "method=chooseHandler")
    public void chooseHandler(HttpServletResponse response,String handlerCode,Integer procurementId){
        ProcurementDTO procurementDTO = procurementService.selectById(procurementId);
        procurementDTO.setOperator(handlerCode);
        procurementDTO.setStatus(1); //1 为待审批状态

        //让第一个人审核
        Assessor assessor = assessorService.selectByProcurementIdAndSort(procurementId, 1);
        assessor.setStatus(1);
        assessorService.updateById(assessor);

        int row = procurementService.updateById(procurementDTO);
        if(row > 0){
            //提交成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //提交失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试");
        }
    }

    /**
     * 处理审核页面
     * @param model
     * @return
     */
    @RequestMapping(params = "method=initCheck")
    public String initCheck(Model model,HttpServletRequest request){
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");

        //查询该用户有没有在审核的采购单
        List<Assessor> assessors = assessorService.selectByEmployeeCodeAndStatus(employeeDTO.getEmployeeCode(), 1);

        if(assessors.size() != 0){
            List<Integer> procurementIds = assessors.stream().map(Assessor::getProcurementId).collect(Collectors.toList());
            //有要处理的采购单
            List<ProcurementDTO> procurementDTOList = procurementService.selectByProcurementIdArray(procurementIds);
            model.addAttribute("procurementDTOList",procurementDTOList);
        }

        return "procurement/manage";
    }

    /**
     * 处理审核页面
     * @param model
     * @return
     */
    @RequestMapping(params = "method=check")
    public String check(Model model,@RequestParam("id") Integer id){
        model.addAttribute("id",id);
        return "procurement/startCheck";
    }

    /**
     * 提交审核
     * @param procurementDTO
     * @param response
     */
    @RequestMapping(params = "method=submitCheck")
    public void submitCheck(ProcurementDTO procurementDTO,HttpServletResponse response){
        ProcurementDTO procurementDTO1 = procurementService.selectById(procurementDTO.getId());

        if(procurementDTO.getStatus() == 2){  //2为审批成功
            //审核成功
            procurementDTO1.setSupplier(procurementDTO.getSupplier());
            procurementDTO1.setQuotedPrice(procurementDTO.getQuotedPrice());

            //审核成功,给下一位审核人审核
            List<Assessor> assessorList = assessorService.selectByProcurementId(procurementDTO.getId());
            Assessor assessor = assessorService.selectByProcurementIdAndStatus(procurementDTO.getId(), 1);
            if(assessor.getSort() == assessorList.size()){
                //是最后一个人审核
                procurementDTO1.setStatus(2); //2为审批成功
            }else{
                procurementDTO1.setStatus(1); //1为待审批
                Assessor assessor1 = assessorService.selectByProcurementIdAndSort(procurementDTO.getId(), assessor.getSort()+1);
                assessor.setStatus(0); //0为审核完成状态
                assessor1.setStatus(1); //1为当前审核人处理状态
                assessorService.updateById(assessor);
                assessorService.updateById(assessor1);
            }

        }else{
            //审核拒绝
            procurementDTO1.setStatus(procurementDTO.getStatus());
        }

        procurementDTO1.setRemark(procurementDTO.getRemark());
        int row = procurementService.updateById(procurementDTO1);

        if(row > 0){
            //提交成功
            if(procurementDTO.getStatus() == 3){ //3为审核拒绝
                //审批拒绝 更改原料审核状态
                ProductMaterialsDTO productMaterialsDTO = productMaterialsService.selectById(procurementDTO1.getProductMaterialsId());
                productMaterialsDTO.setProcurementStatus(4); //4为采购拒绝
                int result = productMaterialsService.updateById(productMaterialsDTO);
                if(result > 0){
                    //将产品原料状态改为采购拒绝状态
                    ProductionDTO productionDTO = productionService.selectByProductCode(productMaterialsDTO.getProductCode());
                    productionDTO.setStatus(7); //7为采购拒绝状态
                    productionService.updateById(productionDTO);
                }
            }
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //提交时失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试");
        }
    }

    /**
     * 提交资金申请
     * @param response
     * @param procurementGoodsCode
     */
    @RequestMapping(params = "method=submitFundApplication")
    public void submitFundApplication(HttpServletResponse response,String procurementGoodsCode,HttpServletRequest request){
        List<ProcurementGoodsDTO> procurementGoodsDTOList = procurementGoodsService.selectByProcurementGoodsCode(procurementGoodsCode);
        //获得员工的信息
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        procurementGoodsDTOList.stream().map(p -> {
            p.setStatus(1);
            p.setEmployeeCode(employeeDTO.getEmployeeCode());
            p.setEmployeeName(employeeDTO.getName());
            return p;
        }).collect(Collectors.toList());

        //批量更新
        boolean b = procurementGoodsService.updateAllColumnBatchById(procurementGoodsDTOList);

        if(b){
            //插入成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //插入失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试");
        }
    }

    /**
     * 采购清单管理页面
     * @param model
     * @return
     */
    @RequestMapping(params = "method=all")
    public String all(Model model){
        List<ProcurementDTO> procurementDTOList = procurementService.selectAll();
        model.addAttribute("procurementDTOList",procurementDTOList);
        model.addAttribute("selectAll","all");
        return "procurement/manage";
    }

    /**
     * 采购拒绝详情页面
     * @param model,id
     * @return
     */
    @RequestMapping(params = "method=refuseDetail")
    public String refuseDetail(Model model,Integer id){
        ProcurementDTO procurementDTO = procurementService.selectByProductMaterialsId(id);

        model.addAttribute("remark",procurementDTO.getRemark());
        model.addAttribute("reason","refuseReason");
        return "procurement/startCheck";
    }

    /**
     * 财务拨款
     * @param procurementGoodsCode
     */
    @RequestMapping(params = "method=appropriation")
    public void appropriation(String procurementGoodsCode,HttpServletResponse response){
        List<ProcurementGoodsDTO> procurementGoodsDTOList = procurementGoodsService.selectByProcurementGoodsCode(procurementGoodsCode);
        //获得员工的信息

        procurementGoodsDTOList.stream().map(p -> {
            p.setStatus(2); //2为财务已拨款
            return p;
        }).collect(Collectors.toList());

        //批量更新
        boolean b = procurementGoodsService.updateAllColumnBatchById(procurementGoodsDTOList);

        if(b){
            //插入成功
            AjaxUtil.ajaxJsonSucMessage(response,"200");
        }else{
            //插入失败
            AjaxUtil.ajaxJsonSucMessage(response,"提交失败,请重试");
        }
    }

    /**
     * 〈一句话功能简述〉<br>
     * 采购清单列表
     *
     * @param
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=createProcurementGoods")
    public String createProcurementGoods(Model model,HttpServletResponse response,@RequestParam(value = "function",required = false) String function){
        List<ProcurementGoodsDTO> procurementGoodsDTOList = procurementGoodsService.selectCodeDistinct();
        if(StringUtils.isNotBlank(function)){
            if(function.equals("fundApplication")){
                //资金申请页面
                model.addAttribute("function",function);
                procurementGoodsDTOList = procurementGoodsService.selectCodeDistinctByStatus(0);    // 0为采购单待处理状态
            }else if(function.equals("finance")){
                model.addAttribute("function",function);
                procurementGoodsDTOList = procurementGoodsService.selectCodeDistinctByStatus(1);    // 0为采购单待处理状态
            }

        }
        model.addAttribute("procurementGoodsDTOList",procurementGoodsDTOList);
        return "procurement/procurementGoods/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 采购清单详情
     *
     * @param procurementGoodsCode, model
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=selectAllProcurementGoods")
    public String selectAllProcurementGoods(@RequestParam("procurementGoodsCode") String procurementGoodsCode,Model model,@RequestParam(value = "function",required = false) String function){
        List<ProcurementGoodsDTO> procurementGoodsDTOList = procurementGoodsService.selectByProcurementGoodsCode(procurementGoodsCode);
        if(StringUtils.isNotBlank(function)){
            //是采购资金申请页面
            model.addAttribute("function",function);
        }
        model.addAttribute("procurementGoodsCode",procurementGoodsCode);
        model.addAttribute("procurementGoodsDTOList",procurementGoodsDTOList);
        return "procurement/procurementGoods/procurementGoodsManage";
    }

    @RequestMapping(params = "method=selectByStatusAndDate")
    public String selectByStatusAndDate(Model model,Integer status,String startDate,String endDate){

        //转变时间格式
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Timestamp tt = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String starTime ,endTime;

        if(org.apache.commons.lang3.StringUtils.isNotBlank(startDate)){
            starTime = startDate+" 00:00:00";
            ts = Timestamp.valueOf(starTime);
        }else {
            ts = null;
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(startDate)){
            endTime = endDate+" 00:00:00";
            tt = Timestamp.valueOf(endTime);
        }else{
            tt = null;
        }

        List<ProcurementGoodsDTO> procurementGoodsDTOList = procurementGoodsService.selectByStatusOrDate(status, ts, tt);

        model.addAttribute("procurementGoodsDTOList",procurementGoodsDTOList);
        model.addAttribute("status",status);


        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);

        return "procurement/procurementGoods/manage";

    }

}
