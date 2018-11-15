package com.cubicpark.mechanic.controller.handler;

import com.cubicpark.mechanic.exception.AuthorizeException;
import com.cubicpark.mechanic.exception.ServiceException;
import com.cubicpark.mechanic.util.AjaxObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/6
 * @since 1.0.0
 */

@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(value = ServiceException.class)
    public Object ServiceException(ServiceException e){
        return AjaxObject.error(Integer.valueOf(e.getErrorId()),e.getMessage());
    }

    @ExceptionHandler(value = AuthorizeException.class)
    public Object AuthorizeException(AuthorizeException e, HttpServletRequest req){
        //使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json, 如果为非ajax则返回view(即ModelAndView)
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
            return AjaxObject.error((e.getMessage()));
        } else {
            ModelAndView modelAndView = new ModelAndView();
            /*modelAndView.addObject("msg", e.getMessage());
            modelAndView.addObject("url", req.getRequestURL());
            modelAndView.addObject("stackTrace", e.getStackTrace());*/
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
    }
}
