package com.cubicpark.mechanic.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cubicpark.mechanic.domain.dto.employee.EmployeeDTO;

public class SecurityFilter implements Filter {
    private Set<String> ignores;

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hrequest = (HttpServletRequest) request;
        HttpServletResponse hresponse = (HttpServletResponse) response;

        String path = hrequest.getServletPath();
        path = path.substring(1);
        // 一些特定的请求地址不需要进行过滤
        //|| path.indexOf("/css/") >= 0 || path.indexOf("/js/") >= 0
        if (ignores.contains(path) || path.indexOf("/login") >= 0) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = hrequest.getSession();
        // 从SESSION中获得登陆admin对象
        EmployeeDTO employee = null;
        if (session != null) {
            employee = (EmployeeDTO) session.getAttribute("user");
        }

        if (employee == null) {
            this.redirectIndex(hrequest, hresponse);
            return;
        }

        chain.doFilter(request, response);
    }

    private void redirectIndex(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String spath = "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
        response.sendRedirect(spath + "/index.htm");
    }

    public void init(FilterConfig arg0) throws ServletException {
        ignores = new HashSet<String>();
        ignores.add("index.htm");
        ignores.add("login.htm");        
    }

}
