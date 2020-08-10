/*
 * @Author: Wonder2019 
 * @Date: 2020-04-30 23:16:38 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-10 18:22:58
 */
package top.imwonder.myblog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        return checkRequestType(uri, req);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundExceptionHandler(HttpServletRequest req, Model model, NoHandlerFoundException ex) {

        model.addAttribute("code", 404);
        model.addAttribute("msg", "您请求的资源未找到！");
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(WonderException.class)
    public String wonderExceptionHandler(HttpServletRequest req, Model model, WonderException ex) {
        model.addAttribute("code", ex.getCode());
        model.addAttribute("msg", ex.getMessage());
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(AuthorizationException.class)
    public String authorizationExceptionHandler(HttpServletRequest req, Model model, AuthorizationException ex) {
        model.addAttribute("code", 403);
        model.addAttribute("msg", "非法访问，您无权限执行此操作");
        return checkRequestType(req.getRequestURI(), req);
    }

    private String checkRequestType(String uri, HttpServletRequest req) {
        if (uri.indexOf("cgi-bin") != -1 || uri.indexOf("php") != -1) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            try (PrintWriter pw = new PrintWriter(new FileOutputStream("reqofphp.log", true))) {
                pw.print(sdf.format(new Date()));
                pw.print("-[ imwonder ]-");
                pw.print(req.getRemoteHost());
                pw.print("--");
                pw.print(req.getRemoteAddr());
                pw.print(":");
                pw.print(req.getRemotePort());
                pw.print("-[ imwonder ]-");
                pw.print(uri);
                pw.print("-[ imwonder ]-");
                pw.println(req.getHeader("User-Agent"));
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
        log.debug("uri:{}", uri);
        return uri.endsWith("Ajax") ? "json" : "error";
    }

}
