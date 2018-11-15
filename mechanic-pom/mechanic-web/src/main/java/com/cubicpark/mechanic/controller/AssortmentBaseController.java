package com.cubicpark.mechanic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cubicpark.mechanic.common.util.MenthaCodeUtil;
import com.cubicpark.mechanic.dao.base.ISeqDAO;
import com.cubicpark.mechanic.util.AjaxUtil;
import com.firstji.mentha.common.helper.AssortmentErrorCode;
import com.firstji.mentha.domain.dto.assortment.AssortmentBaseDTO;
import com.firstji.mentha.domain.service.assortment.IAssortmentBseService;

public abstract class AssortmentBaseController<M extends AssortmentBaseDTO, T extends IAssortmentBseService<M>, Patch extends String> extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AssortmentBaseController.class);

    @Autowired
    private T t;
    private Patch p;
    @Autowired
    private ISeqDAO seqDao;

    @RequestMapping(params = "method=index")
    public String index(HttpServletRequest request, Model model) {
        List<M> mList = t.getAll();

        model.addAttribute("allAssortmentInfo", mList);
        return "base/department/manage";
    }

    @RequestMapping(params = "method=initAdd")
    public String initAdd(HttpServletRequest request, Model model, int parentId) {
        List<M> mList = t.getAll();

        model.addAttribute("parentId", parentId);
        model.addAttribute("allAssortmentInfo", mList);
        return "base/department/add";
    }

    @RequestMapping(params = "method=initModify")
    public String initModify(HttpServletRequest request, Model model, int id, RedirectAttributes rattr) {
        M m = t.getByID(id);

        if (m == null) {
            rattr.addFlashAttribute("error", "选中修改的部门不存在或已被删除");
            return "redirect:department.htm?method=index";
        }

        // 当前分类为子分类
        List<M> allParentAssortment = null;
        if (m.getParentId() > 0) {
            allParentAssortment = t.getGroupByPath(m.getParentPath());// 获取当前分类全部父分类
        }

        model.addAttribute("assortment", m);
        model.addAttribute("allParentAssortment", allParentAssortment);
        return "base/department/modify";
    }

    @RequestMapping(params = "method=add")
    public String add(HttpServletRequest request, HttpServletResponse response) {
        int parentId = Integer.valueOf(request.getParameter("parentId"));
        String name = request.getParameter("name");
        // 自动生成CODE
        String code = MenthaCodeUtil.generateMenthaCode("department", 9);

        M m = null;
        buildAssortment(m, 0, parentId, name, code);

        String addResultInfo = t.add(m);
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
        String departmentName = request.getParameter("departmentName");
        
        M m = null;
        buildAssortment(m, id, 0, departmentName, null);

        String modifyResultInfo = t.modify(m);
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
        List<M> rootDepartmentList = t.getRoot();

        model.addAttribute("rootDepartment", rootDepartmentList);
        return "base/department/order";
    }

    @RequestMapping(params = "method=initOrderN")
    public String initOrderN(HttpServletRequest request, Model model) {
        List<M> departmentList = t.getAllSortExceptRoot();

        model.addAttribute("allDepartment", departmentList);
        return "base/department/orderN";
    }

    @RequestMapping(params = "method=order")
    public String order(HttpServletRequest request, Model model, String action, RedirectAttributes rattr) {
        String moveNum = request.getParameter("moveNum");
        String id = request.getParameter("id");
        String sameRootTag = request.getParameter("sameRootTag");

        if (StringUtils.isBlank(id) || StringUtils.isBlank(sameRootTag)) {
            rattr.addFlashAttribute("error", "操作参数不足！");
            return "redirect:department.htm?method=initOrder";
        }

        if (StringUtils.isBlank(moveNum)) {
            rattr.addFlashAttribute("error", "请选择要移动的数字！");
            return "redirect:department.htm?method=initOrder";
        }

        String rootMoveResult = null;
        if ("up".equals(action)) {
            rootMoveResult = t.upRootSort(Integer.valueOf(id).intValue(), Integer.valueOf(sameRootTag)
                    .intValue(), Integer.valueOf(moveNum).intValue());
        } else if ("down".equals(action)) {
            rootMoveResult = t.downRootSort(Integer.valueOf(id).intValue(), Integer
                    .valueOf(sameRootTag).intValue(), Integer.valueOf(moveNum).intValue());
        } else {
            rattr.addFlashAttribute("error", "操作参数不足，排序顺序未知！");
            return "redirect:department.htm?method=initOrder";
        }

        if (!AssortmentErrorCode.SUCCESS.getValue().equals(rootMoveResult)) {
            rattr.addFlashAttribute("error", AssortmentErrorCode.getDescByValue(rootMoveResult));
        }

        return "redirect:department.htm?method=initOrder";
    }

    @RequestMapping(params = "method=orderN")
    public String orderN(HttpServletRequest request, Model model, String action, RedirectAttributes rattr) {
        String moveNum = request.getParameter("moveNum");
        String id = request.getParameter("id");

        if (StringUtils.isBlank(id)) {
            rattr.addFlashAttribute("error", "操作参数不足！");
            return "redirect:department.htm?method=initOrderN";
        }

        if (StringUtils.isBlank(moveNum)) {
            rattr.addFlashAttribute("error", "请选择要移动的数字！");
            return "redirect:department.htm?method=initOrderN";
        }

        String rootMoveResult = null;
        if ("up".equals(action)) {
            rootMoveResult = t.upSortExcpetRoot(Integer.valueOf(id).intValue(), Integer
                    .valueOf(moveNum).intValue());
        } else if ("down".equals(action)) {
            rootMoveResult = t.downSortExceptRoot(Integer.valueOf(id).intValue(),
                    Integer.valueOf(moveNum).intValue());
        } else {
            rattr.addFlashAttribute("error", "操作参数不足，排序顺序未知！");
            return "redirect:department.htm?method=initOrder";
        }

        if (!AssortmentErrorCode.SUCCESS.getValue().equals(rootMoveResult)) {
            rattr.addFlashAttribute("error", AssortmentErrorCode.getDescByValue(rootMoveResult));
        }

        return "redirect:department.htm?method=initOrderN";
    }
    
    @RequestMapping(params = "method=del")
    public String del(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.valueOf(request.getParameter("id"));
        String delResultInfo = t.del(id);
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
     * @param department
     * @param id
     * @param parentId
     * @param departmentName
     * @param isCRM
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public abstract void buildAssortment(M m, Object... args);
}
