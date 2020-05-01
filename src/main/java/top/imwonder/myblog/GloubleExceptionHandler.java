/*
 * @Author: Wonder2019 
 * @Date: 2020-04-30 23:16:38 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-05-01 12:03:26
 */
package top.imwonder.myblog;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.myblog.exception.WonderException;

@Slf4j
@ControllerAdvice
public class GloubleExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(HttpServletRequest req, Model model, Exception ex) {
        String uri = req.getRequestURI();
        model.addAttribute("code", 500);
        String msg = ex.getMessage();
        log.warn(msg, ex);
        model.addAttribute("msg", msg != null && msg.indexOf("SQL") > -1 ? "未知异常，请联系管理员" : msg);
        return checkRequestType(uri);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundExceptionHandler(HttpServletRequest req, Model model, NoHandlerFoundException ex) {
        model.addAttribute("code", 404);
        model.addAttribute("msg", "您请求的资源未找到！");
        return checkRequestType(req.getRequestURI());
    }
    
    @ExceptionHandler(WonderException.class)
    public String wonderExceptionHandler(HttpServletRequest req, Model model, WonderException ex) {
        model.addAttribute("code", ex.getCode());
        model.addAttribute("msg", ex.getMessage());
        return checkRequestType(req.getRequestURI());
    }

    @ExceptionHandler(AuthorizationException.class)
    public String authorizationExceptionHandler(HttpServletRequest req, Model model, AuthorizationException ex) {
        model.addAttribute("code", 403);
        model.addAttribute("msg", "非法访问，您无权限执行此操作");
        return checkRequestType(req.getRequestURI());
    }

    private String checkRequestType(String uri){
        log.debug("uri:{}", uri);
        return uri.endsWith("Ajax") ? "json" : "error";
    }


}
