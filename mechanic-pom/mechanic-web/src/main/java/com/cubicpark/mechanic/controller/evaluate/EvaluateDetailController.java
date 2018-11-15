package com.cubicpark.mechanic.controller.evaluate;

import com.cubicpark.mechanic.dao.base.IDepartmentDAO;
import com.cubicpark.mechanic.dao.employee.IEmployeeDAO;
import com.cubicpark.mechanic.domain.dto.base.DepartmentDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO;
import com.cubicpark.mechanic.domain.service.evaluate.IEvaluateDetailService;
import com.cubicpark.mechanic.util.AjaxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 * 评价详情
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("/productEvaluateDetail")
public class EvaluateDetailController {

    @Autowired
    private IEvaluateDetailService IEvaluateDetailService;

    @Autowired
    private IEmployeeDAO employeeDAO;

    @Autowired
    private IDepartmentDAO departmentDAO;

    /**
     * 〈一句话功能简述〉<br>
     * 添加评价详情
     *
     * @param [evaluateDetailDTO, request, response]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=add")
    public String add(EvaluateDetailDTO evaluateDetailDTO, HttpServletRequest request
                      , HttpServletResponse response){
        int result = 0;
        if(evaluateDetailDTO.getId() == null){
            //说明是添加新评论
            EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
            result = IEvaluateDetailService.insert(evaluateDetailDTO,employeeDTO);
        }else{
            //如果id存在,说明是更改评论信息
            result = IEvaluateDetailService.update(evaluateDetailDTO);
        }
        String msg= null;
        if(result > 0 ){
            //说明插入成功
            msg = "200";
        }else{
            //插入失败
            msg = "评论更改或添加失败!";
        }
        AjaxUtil.ajaxJsonSucMessage(response,msg);

        return null;
    }

    /**
     * 〈一句话功能简述〉<br>
     * 根据productEvluateCode查看所有的评价工单
     *
     * @param [productEvluateCode, model, request]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=look")
    public String look(String productEvluateCode, Model model,HttpServletRequest request){
        List<EvaluateDetailDTO> evaluateDetailDTOList = IEvaluateDetailService.selectByProductEvaluateCode(productEvluateCode);
        if(evaluateDetailDTOList.size() == 0){
            return "evaluate/empty";
        }
        List<EvaluateDetailDTO> evaluateDetailDTOS = new ArrayList<>();
        for(EvaluateDetailDTO p: evaluateDetailDTOList){
            DepartmentDTO departmentDTO = departmentDAO.getDepartmentByCode(p.getDepartmentCode());
            EmployeeDTO employeeDTO = employeeDAO.getEmployeeByCode(p.getEmployeeCode());
            p.setDepartmentCode(departmentDTO.getDepartmentName());
            p.setEmployeeCode(employeeDTO.getName());
            evaluateDetailDTOS.add(p);
        }
        model.addAttribute("productEvaluateDetailDTOS", evaluateDetailDTOS);
        return "evaluate/look";
    }
}
