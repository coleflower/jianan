package com.cubicpark.mechanic.controller.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.controller.BaseController;
import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
import com.cubicpark.mechanic.domain.service.base.IPositionService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.firstji.mentha.common.helper.AssortmentErrorCode;

@Controller
@RequestMapping("position")
public class PositionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private IPositionService positionService;
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, Model model) {
        List<PositionDTO> positionList = positionService.getAll();

        model.addAttribute("allPositionInfo", positionList);
        return "base/position/manage";
    }

    @RequestMapping(params = "method=initAdd")
    public String initAdd(HttpServletRequest request, Model model, int parentId) {
        List<PositionDTO> positionList = positionService.getAll();

        model.addAttribute("parentId", parentId);
        model.addAttribute("allPositionInfo", positionList);
        return "base/position/add";
    }

    @RequestMapping(params = "method=initModify")
    public String initModify(HttpServletRequest request, Model model, int id, RedirectAttributes rattr) {
        PositionDTO position = positionService.getByID(id);

        if (position == null) {
            rattr.addFlashAttribute("error", "选中修改的职位不存在或已被删除");
            return "redirect:position.htm?method=index";
        }

        // 当前分类为子分类
        List<PositionDTO> allParentPosition = null;
        if (position.getParentId() > 0) {
            allParentPosition = positionService.getGroupByPath(position.getParentPath());// 获取当前分类全部父分类
        }

        model.addAttribute("position", position);
        model.addAttribute("allParentPosition", allParentPosition);
        return "base/position/modify";
    }

    @RequestMapping(params = "method=add")
    public String add(HttpServletRequest request, HttpServletResponse response) {
        int parentId = Integer.valueOf(request.getParameter("parentId"));
        String positionName = request.getParameter("positionName");
        // 自动生成CODE
        String positionCode = MenthaCodeUtil.generateMenthaCode("position", 9);

        PositionDTO position = new PositionDTO();
        this.buildPosition(position, 0, parentId, positionName, positionCode);

        String addResultInfo = positionService.add(position);
        String message = null;
        if (AssortmentErrorCode.SUCCESS.getValue().equals(addResultInfo)) {
            // 新增成功
            message = AssortmentErrorCode.SUCCESS.getValue();
        } else {
            // 新增失败返回错误信息
            message = AssortmentErrorCode.getDescByValue(addResultInfo);
        }

        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    @RequestMapping(params = "method=modify")
    public String modify(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.valueOf(request.getParameter("id"));
        String positionName = request.getParameter("positionName");

        PositionDTO position = new PositionDTO();
        this.buildPosition(position, id, 0, positionName, null);

        String modifyResultInfo = positionService.modify(position);
        String message = null;
        if (AssortmentErrorCode.SUCCESS.getValue().equals(modifyResultInfo)) {
            // 新增成功
            message = AssortmentErrorCode.SUCCESS.getValue();
        } else {
            // 新增失败返回错误信息
            message = AssortmentErrorCode.getDescByValue(modifyResultInfo);
        }

        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    @RequestMapping(params = "method=initOrder")
    public String initOrder(HttpServletRequest request, Model model) {
        List<PositionDTO> rootPositionList = positionService.getRoot();

        model.addAttribute("rootPosition", rootPositionList);
        return "base/position/order";
    }

    @RequestMapping(params = "method=initOrderN")
    public String initOrderN(HttpServletRequest request, Model model) {
        List<PositionDTO> positionList = positionService.getAllSortExceptRoot();

        model.addAttribute("allPosition", positionList);
        return "base/position/orderN";
    }

    @RequestMapping(params = "method=order")
    public String order(HttpServletRequest request, Model model, String action, RedirectAttributes rattr) {
        String moveNum = request.getParameter("moveNum");
        String id = request.getParameter("id");
        String sameRootTag = request.getParameter("sameRootTag");

        if (StringUtils.isBlank(id) || StringUtils.isBlank(sameRootTag)) {
            rattr.addFlashAttribute("error", "操作参数不足！");
            return "redirect:position.htm?method=initOrder";
        }

        if (StringUtils.isBlank(moveNum)) {
            rattr.addFlashAttribute("error", "请选择要移动的数字！");
            return "redirect:position.htm?method=initOrder";
        }

        String rootMoveResult = null;
        if ("up".equals(action)) {
            rootMoveResult = positionService.upRootSort(Integer.valueOf(id).intValue(), Integer.valueOf(sameRootTag)
                    .intValue(), Integer.valueOf(moveNum).intValue());
        } else if ("down".equals(action)) {
            rootMoveResult = positionService.downRootSort(Integer.valueOf(id).intValue(), Integer
                    .valueOf(sameRootTag).intValue(), Integer.valueOf(moveNum).intValue());
        } else {
            rattr.addFlashAttribute("error", "操作参数不足，排序顺序未知！");
            return "redirect:position.htm?method=initOrder";
        }

        if (!AssortmentErrorCode.SUCCESS.getValue().equals(rootMoveResult)) {
            rattr.addFlashAttribute("error", AssortmentErrorCode.getDescByValue(rootMoveResult));
        }

        return "redirect:position.htm?method=initOrder";
    }

    @RequestMapping(params = "method=orderN")
    public String orderN(HttpServletRequest request, Model model, String action, RedirectAttributes rattr) {
        String moveNum = request.getParameter("moveNum");
        String id = request.getParameter("id");

        if (StringUtils.isBlank(id)) {
            rattr.addFlashAttribute("error", "操作参数不足！");
            return "redirect:position.htm?method=initOrderN";
        }

        if (StringUtils.isBlank(moveNum)) {
            rattr.addFlashAttribute("error", "请选择要移动的数字！");
            return "redirect:position.htm?method=initOrderN";
        }

        String rootMoveResult = null;
        if ("up".equals(action)) {
            rootMoveResult = positionService.upSortExcpetRoot(Integer.valueOf(id).intValue(), Integer
                    .valueOf(moveNum).intValue());
        } else if ("down".equals(action)) {
            rootMoveResult = positionService.downSortExceptRoot(Integer.valueOf(id).intValue(),
                    Integer.valueOf(moveNum).intValue());
        } else {
            rattr.addFlashAttribute("error", "操作参数不足，排序顺序未知！");
            return "redirect:position.htm?method=initOrder";
        }

        if (!AssortmentErrorCode.SUCCESS.getValue().equals(rootMoveResult)) {
            rattr.addFlashAttribute("error", AssortmentErrorCode.getDescByValue(rootMoveResult));
        }

        return "redirect:position.htm?method=initOrderN";
    }
    
    @RequestMapping(params = "method=del")
    public String del(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.valueOf(request.getParameter("id"));
        PositionDTO position = positionService.getByID(id);
        // todo 分别查询雇员和客户是否有使用该销售区域的
        /**List<EmployeeDTO> employeeList = employeeService.getEmployeeByDepartmentCode(position.getDepartmentCode());
        
        if (employeeList != null && employeeList.size() > 0) {
            AjaxUtil.ajaxJsonSucMessage(response, "该部门正在使用无法删除！");
            return null;
        }*/

        String delResultInfo = positionService.del(id);
        String message = null;
        if (AssortmentErrorCode.SUCCESS.getValue().equals(delResultInfo)) {
            // 新增成功
            message = AssortmentErrorCode.SUCCESS.getValue();
        } else {
            // 新增失败返回错误信息
            message = AssortmentErrorCode.getDescByValue(delResultInfo);
        }

        AjaxUtil.ajaxJsonSucMessage(response, message);
        return null;
    }

    /**
     * 
     * 功能描述: <br>
     * 包装部门对象
     *
     * @param position
     * @param id
     * @param parentId
     * @param departmentName
     * @param isCRM
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    private void buildPosition(PositionDTO position, int id, int parentId, String positionName,
            String positionCode) {
        position.setId(id);
        position.setParentId(parentId);
        position.setPositionName(positionName);
        position.setPositionCode(positionCode);
    }
}
