package com.cubicpark.mechanic.controller.design;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.design.Design;
import com.cubicpark.mechanic.domain.dto.design.DesignReview;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.design.IDesignReviewService;
import com.cubicpark.mechanic.domain.service.design.IDesignService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.util.AjaxObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 *  设计工单审核
 * @author Surging
 * @create 2018/9/6
 * @since 1.0.0
 */
@Controller
@RequestMapping("/review")
public class DesignReviewController extends BaseController {

    @Autowired
    private IDesignReviewService designReviewService;

    @Autowired
    private IDesignService designService;

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 根据工单查询审核结果
     * @param designCode
     * @param model
     * @return
     */
    @GetMapping("/getReviewList")
    public String getReviewList(@RequestParam(value = "designCode") String designCode, Model model){
        /*DesignReview designReview = new DesignReview();
        designReview.setDesignCode(designCode);*/
        Wrapper w = new EntityWrapper();
        w.eq("design_code", designCode);
        w.orderBy("createdate",false);
        List<DesignReview> designReviewList = designReviewService.selectList(w);
        designReviewList.stream().forEach(e -> e.setEmployeeCode(employeeService.getEmployeeByCode(e.getEmployeeCode()).getName()));
        model.addAttribute("designReviewList", designReviewList);
        return "design/check/show";
    }

    @GetMapping("/preSave")
    public String preSave(@RequestParam(value = "designCode") String designCode, Model model){
        Design design = designService.selectByDesignCode(designCode);
        model.addAttribute("design", design);
        return "design/check/review";
    }

    @ResponseBody
    @PostMapping("/save")
    public Object save(DesignReview designReview, HttpServletRequest request){
        // 获取登录信息
        EmployeeDTO employeeDTO = getMember(request);
        designReview.setEmployeeCode(employeeDTO.getEmployeeCode());
        return designReviewService.insert(designReview) ? AjaxObject.ok("操作成功") : AjaxObject.error("操作失败");
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "current", defaultValue = "1") Integer current,
                       @RequestParam(value = "designCode", required = false) String designCode) {
        Wrapper w= new EntityWrapper();
        w.eq("status", Constants.check_ing);
        if (!StringUtils.isEmpty(designCode)){
            w.eq("design_code", designCode);
        }
        Page<Design> designPage = designService.selectPage(new Page(current, Constants.COMMON_PAGESIZE),w);
        model.addAttribute("result", designPage);
        model.addAttribute("designCode",designCode);
        return "design/check/list";
    }
}
