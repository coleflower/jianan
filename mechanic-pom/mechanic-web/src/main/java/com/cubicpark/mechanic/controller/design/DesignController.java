package com.cubicpark.mechanic.controller.design;

import com.baomidou.mybatisplus.plugins.Page;
import com.cubicpark.mechanic.common.helper.CommonErrorCode;
import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.design.Design;
import com.cubicpark.mechanic.domain.service.design.IDesignService;
import com.cubicpark.mechanic.util.AjaxObject;
import com.cubicpark.mechanic.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/4
 * @since 1.0.0
 */
@Controller
@RequestMapping("/design")
public class DesignController {

    @Autowired
    private IDesignService designService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "designCode", required = false) String designCode
            , @RequestParam(value = "current", defaultValue = "1") Integer current
            , Model model) {
        Page<Design> designPage = designService.selectUserPage(new Page(current, Constants.COMMON_PAGESIZE), designCode);
        Map<String, Object> param = new HashMap<>();
        param.put("designCode", designCode);
        model.addAttribute("param", param);
        model.addAttribute("result", designPage);
        // 页码超过 查出的页码 直接重定向到最后一页
        if (current > designPage.getPages() && designPage.getPages() != 0){
            return "redirect:/design/list.htm?designCode" +designCode +"&current"+designPage.getPages();
        }
        return "design/list";
    }

    @RequestMapping("/preSave")
    public String preSave(Model model, @RequestParam(value = "id", required = false) Integer id) {
        if (!StringUtils.isEmpty(id)) {
            Design design = designService.selectById(id);
            model.addAttribute("design", design);
        }
        return "design/add";
    }

    /**
     * 修改/新增设计工单
     * @param design
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Object save(Design design) {
        // TODO 合同编号获取
        if (StringUtils.isEmpty(design.getContractCode())) {
            design.setContractCode(KeyUtil.genUniqueKey());
        }
        design.setStatus(0);
        if (design.getId() == null) {
            design.setDesignCode(KeyUtil.genUniqueKey());
            return designService.insert(design) ? AjaxObject.ok("添加成功") : AjaxObject.error("添加失败");
        } else {
            Design design1 = designService.selectById(design.getId());
            if (design1.getStatus() == Constants.check_ing) {
                return AjaxObject.error(CommonErrorCode.DESIGN_STATUS_REVIEWED.getDesc());
            }
            return designService.updateById(design) ? AjaxObject.ok("修改成功") : AjaxObject.error("修改失败");
        }
    }

    /**
     * 删除设计工单
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(@RequestParam(value = "id", required = false) Integer id) {
        return designService.deleteById(id) ? AjaxObject.ok("删除成功") : AjaxObject.error("删除失败");
    }

    /**
     * 提交审核
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping("/check")
    public Object check(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "status") Integer status) {
        Design design = designService.selectById(id);
        if (design == null) {
            return AjaxObject.error(CommonErrorCode.DESIGN_NOT_EXIST.getDesc());
        }
        if (design.getStatus() != Constants.CHECK_NO) {
            return AjaxObject.error(CommonErrorCode.DESIGN_STATUS_REVIEWED.getDesc());
        }
        design.setStatus(status);
        return designService.updateById(design) ? AjaxObject.ok("操作成功") : AjaxObject.error("操作失败");
    }

}
