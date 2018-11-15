package com.cubicpark.mechanic.controller;

import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;
import com.cubicpark.mechanic.exception.AuthorizeException;
import com.cubicpark.mechanic.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BaseController {
    
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    
    public static final String LOGIN = "member/login/login";
    public static final String INDEX = "site/index";
    public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
        
    }
    
    protected String getBasePath(HttpServletRequest request)
    {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/";
    }
    
    
    protected void getValidError(BindingResult result) throws SystemException
    {
        if (result.hasErrors())
        {
            String message = "";
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors)
            {
                if (error == null)
                {
                    continue;
                }
                message = message + error.getDefaultMessage();
            }
            logger.error("Nested path [" + result.getNestedPath()
                    + "] has errors [" + message + "]", this.getClass());
            throw new SystemException(message);
        }
    }
    
    /**
     * 
     * 功能描述: <br>
     * 根据IP获取访问所在地信息
     *
     * @param request
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    protected String getIPMapAdrResult(HttpServletRequest request)
    {
        URL url = null;
        HttpURLConnection connection = null;
        try
        {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getRemoteAddr();
            }
            url = new URL("http://ip.taobao.com/service/getIpInfo.php");
            connection = (HttpURLConnection) url.openConnection();// 新建连接实例
            connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
            connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
            connection.setDoOutput(true);// 是否打开输出流 true|false
            connection.setDoInput(true);// 是否打开输入流true|false
            connection.setRequestMethod("POST");// 提交方法POST|GET
            connection.setUseCaches(false);// 是否缓存true|false
            connection.connect();// 打开连接端口
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());// 打开输出流往对端服务器写数据
            out.writeBytes("ip=" + ip);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.flush();// 刷新
            out.close();// 关闭输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF-8"));// 往对端写完数据对端服务器返回数据
            // ,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (connection != null)
            {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }
    
    public EmployeeDTO getEmployee(HttpServletRequest request) {
        // 取shiro中保存的SESSION中 以下例子
        //Subject subject = SecurityUtils.getSubject();
        //PrincipalCollection principals = subject.getPrincipals();
        EmployeeDTO employee = null;
        if(request != null) {
            employee = (EmployeeDTO) request.getSession().getAttribute("user");
        }
        return employee;
    }

    public EmployeeDTO getMember(HttpServletRequest request) {
        EmployeeDTO employee = null;
        if(request != null) {
            employee = (EmployeeDTO) request.getSession().getAttribute("user");
        }
        if (employee == null){
            throw new AuthorizeException("请先登录!!!!!!!!!");
        }
        return employee;
    }
}
