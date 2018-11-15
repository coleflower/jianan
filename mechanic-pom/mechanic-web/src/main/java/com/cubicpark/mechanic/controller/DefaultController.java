package com.cubicpark.mechanic.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cubicpark.mechanic.common.helper.Constants;
import com.cubicpark.mechanic.domain.dto.base.PositionDTO;
import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.domain.service.base.IPositionService;
import com.cubicpark.mechanic.domain.service.employee.IEmployeeService;
import com.cubicpark.mechanic.util.ValidateCodeUtil;
import com.cubicpark.mechanic.vo.LoginVO;
import com.firstji.mentha.common.utils.StringUtil;

@Controller
@RequestMapping("/")
public class DefaultController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPositionService positionService;
    @Resource
    private Map<String, String> roleMap = new HashMap<String, String>();

    @RequestMapping("500")
    public String toError() {
        return "errorPage/500";
    }

    @RequestMapping("404")
    public String to404() {
        return "errorPage/404";
    }

    @RequestMapping("index")
    public String index(HttpServletRequest request, Model model, HttpSession session) {
        if (session != null) {
            EmployeeDTO employee = (EmployeeDTO) session.getAttribute("employee");
            
            if (employee != null) {
                return "redirect:main.htm";
            }
        }
        
        return "index";
    }

    @RequestMapping("login")
    public String login(LoginVO loginUser, HttpServletRequest request, Model model, HttpSession session,
            RedirectAttributes rattr) {

        String validateCode = (String) session.getAttribute("ImgValidateCode");
        String currentValidateCode = loginUser.getValidateCode();
        if (StringUtils.isBlank(currentValidateCode)) {
            rattr.addFlashAttribute("error", "请输入验证码！");
            return "redirect:index.htm";
        }

        // 判断验证码是否一致 不一致回到首页
        if (!currentValidateCode.toUpperCase().equals(validateCode)) {
            rattr.addFlashAttribute("error", "验证码不正确，请重新输入！");
            return "redirect:index.htm";
        }

        // 查询登录名和密码是否一致
        String employeeCode = loginUser.getUserName();// 登录名（员工编号或者工号）
        String passwords = loginUser.getPasswords();

        if (StringUtils.isBlank(employeeCode) || StringUtils.isBlank(passwords)) {
            rattr.addFlashAttribute("error", "工号和密码不能为空！");
            return "redirect:index.htm";
        }

        try {
            EmployeeDTO employee = employeeService.login(employeeCode, passwords);

            if (employee == null) {
                rattr.addFlashAttribute("error", "没有该工号信息请重试！");
                return "redirect:index.htm";
            } else {
                if (Constants.ACCOUNT_FORBIDDEN.equals(employee.getAccountStatus())) {
                    rattr.addFlashAttribute("error", "该工号已被锁定，禁止使用，请联系主管领导！");
                    return "redirect:index.htm";
                }
                
                if (Constants.ACCOUNT_DISABLE.equals(employee.getAccountStatus())) {
                    rattr.addFlashAttribute("error", "该工号已无效！");
                    return "redirect:index.htm";
                }
                
                session.setAttribute("user", employee);
                return "redirect:main.htm";
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            
            rattr.addFlashAttribute("error", "无法查询到该工号，请重试或者联系管理员！");
            return "redirect:index.htm";
        }
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpSession session) {
        session.removeAttribute("user");
        return "redirect:index.htm";
    }
    
    @RequestMapping("main")
    public String main(HttpServletRequest request, HttpSession session, Model model) {
        
        EmployeeDTO employee = (EmployeeDTO) session.getAttribute("user");
        
        EmployeeDTO currentemployee = employeeService.getEmployeeByCode(employee.getEmployeeCode());
        //String roleCode = currentemployee.getRoleCode();
        String permission = currentemployee.getPermission();
        String accountStatus = currentemployee.getAccountStatus();
        //List<String> roleList = StringUtil.parseString2ListByPattern(roleCode);
        List<String> permissionList = StringUtil.parseString2ListByPattern(permission);
        
        //DepartmentDTO department = departmentService.getDepartmentByCode(currentemployee.getDepartmentCode());
        PositionDTO position = positionService.getPositionByCode(currentemployee.getPositionCode());
        
        //model.addAttribute("roleCodes", roleList);
        
        model.addAttribute("loginEmployee", employee);
        model.addAttribute("position", position);
        model.addAttribute("permissions", permissionList);
        model.addAttribute("accountStatus", accountStatus);
        return "main";
    }
    
    @RequestMapping("portal")
    public String portal(HttpServletRequest request) {        
        return "portal";
    }
    
    /**
     * 
     * 功能描述: <br>
     * 获取请求IP地址
     *
     * @param request
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String getIpAddr(HttpServletRequest request) {
        String strUserIp = null;
        /** * */
        // Apache 代理 解决IP地址问题
        strUserIp = request.getHeader("X-Forwarded-For");
        if (strUserIp == null || strUserIp.length() == 0 || "unknown".equalsIgnoreCase(strUserIp)) {
            strUserIp = request.getHeader("Proxy-Client-IP");
        }
        if (strUserIp == null || strUserIp.length() == 0 || "unknown".equalsIgnoreCase(strUserIp)) {
            strUserIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (strUserIp == null || strUserIp.length() == 0 || "unknown".equalsIgnoreCase(strUserIp)) {
            strUserIp = request.getRemoteAddr();
        }
        // 解决获取多网卡的IP地址问题
        if (strUserIp != null) {
            strUserIp = strUserIp.split(",")[0];
        } else {
            strUserIp = "127.0.0.1";
        }
        // 解决获取IPv6地址 暂时改为本机地址模式
        if (strUserIp.length() > 16) {
            strUserIp = "127.0.0.1";
        }
        return strUserIp;
        // 没有IP Apache 代理 解决IP地址问题
        // strUserIp=request.getRemoteAddr();
        // if (strUserIp != null) {strUserIp = strUserIp.split(",")[0];}
        // return strUserIp;
    }

    /**
     * 
     * 功能描述: <br>
     * 图片验证码
     *
     * @param response
     * @param request
     */
    @RequestMapping("getValidateCodeImg.do")
    public void getImg(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        String code = ValidateCodeUtil.generateTextCode(3, 4, "I10O");
        BufferedImage buffImg = ValidateCodeUtil.generateImageCode(code, 75, 25, 5, true, Color.WHITE, Color.BLACK,
                null);
        // HttpSession session = request.getSession();
        session.setAttribute("ImgValidateCode", code);
        logger.error("Img ValidateCode is : " + code, this.getClass());
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");

        ServletOutputStream sos;
        try {
            sos = response.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
