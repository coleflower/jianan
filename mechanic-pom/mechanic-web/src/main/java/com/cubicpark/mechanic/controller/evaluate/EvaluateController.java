package com.cubicpark.mechanic.controller.evaluate;

import com.cubicpark.mechanic.dao.evaluate.IEvaluateDetailDAO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDetailDTO;
import com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDTO;
import com.cubicpark.mechanic.domain.service.evaluate.IEvaluateService;
import com.cubicpark.mechanic.util.AjaxUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 评价工单
 *
 * @author qwc-01
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Controller
@RequestMapping("productEvaluate")
public class EvaluateController {

    @Autowired
    private IEvaluateService IEvaluateService;

    @Autowired
    private IEvaluateDetailDAO productEvaluateDetailDAO;

    /**
     * 〈一句话功能简述〉<br>
     * 初始化列表页面
     *
     * @param [model]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params ="method=index")
    public String index(Model model){
        List<EvaluateDTO> evaluateDTOList = IEvaluateService.selectOneMouthOrder();
        List<EvaluateDTO> evaluateDTOS = this.countScore(evaluateDTOList);
        model.addAttribute("productEvaluateList",evaluateDTOS);
        return "evaluate/manage";
    }

    /**
     * 〈一句话功能简述〉<br>
     * 根据条件筛选查询评价工单
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
        if (StringUtils.isNotBlank(startEntryDate)) {
            ts = Timestamp.valueOf(startEntryDate);
        } else {
            ts = null;
        }
        if (StringUtils.isNotBlank(endEntryDate)) {
            tt = Timestamp.valueOf(endEntryDate);
        } else {
            tt = null;
        }
        List<EvaluateDTO> debugOrderDTOList = IEvaluateService.selectByCodeOrStatusOrDate(codeLike, status, ts, tt);
        model.addAttribute("productEvaluateList",debugOrderDTOList);
        model.addAttribute("codeLike",codeLike);
        model.addAttribute("status",status);
        model.addAttribute("startEntryDate",startEntryDate);
        model.addAttribute("endEntryDate",endEntryDate);
        return "evaluate/manage";
    }


    /**
     * 〈一句话功能简述〉<br>
     * 跳转到评价页面
     *
     * @param [productEvluateCode, model, request]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=initModify")
    public String initModify(@RequestParam("productEvluateCode")String productEvluateCode,Model model,HttpServletRequest request){
        if(productEvluateCode == null){
            return "evaluate/error";
        }
        EmployeeDTO employeeDTO = (EmployeeDTO) request.getSession().getAttribute("user");
        EvaluateDetailDTO evaluateDetailDTO = productEvaluateDetailDAO.selectByProductEvaluateCodeAndEmployeeCode( productEvluateCode,employeeDTO.getEmployeeCode());
        //只有部门编号和员工编号相同才能看到信息
        if(evaluateDetailDTO != null && evaluateDetailDTO.getDepartmentCode().equals(employeeDTO.getDepartmentCode())) {

            return "evaluate/hasSubmit";
        }
        model.addAttribute("productEvluateCode",productEvluateCode);
        return "evaluate/modify";
    }


    /**
     * 〈一句话功能简述〉<br>
     * 根据输入的值查询相似的expressCode
     *
     * @param [code, response]
     * @return java.lang.String
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    @RequestMapping(params = "method=search")
    public String search(@RequestParam("code") String code,HttpServletResponse response){
        List<String> codeList = IEvaluateService.searchExpressCodeLike(code);
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
     * 计算分数
     *
     * @param [evaluateDTOList]
     * @return java.util.List<com.cubicpark.mechanic.domain.dto.evaluate.EvaluateDTO>
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    private List<EvaluateDTO> countScore(List<EvaluateDTO> evaluateDTOList){
        List<EvaluateDTO> evaluateDTOS = new ArrayList<>();
        BigDecimal result = new BigDecimal(0);
        for(EvaluateDTO p : evaluateDTOList){
            List<EvaluateDetailDTO> evaluateDetailDTOList = productEvaluateDetailDAO.selectByProductEvaluateCode(p.getProductEvluateCode());
            if(evaluateDetailDTOList.size() == 4){
                BigDecimal count = new BigDecimal(0);
                for(EvaluateDetailDTO evaluateDetailDTO : evaluateDetailDTOList){
                    count = count.add(evaluateDetailDTO.getScore());
                }
                result = count.divide(new BigDecimal(4),2,RoundingMode.HALF_UP);
                p.setAverage(result);
                int a = IEvaluateService.updateById(p);
            }else {
                p.setAverage(new BigDecimal(0));
            }
            evaluateDTOS.add(p);
        }
        return evaluateDTOS;
    }
}
