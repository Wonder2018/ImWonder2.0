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
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.myblog.exception.WonderException;
import top.imwonder.myblog.exception.WonderResourceNotFoundException;

@Slf4j
@ControllerAdvice
public class GloubleExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model, Exception ex) {
        res.setStatus(500);
        model.addAttribute("code", 500);
        String msg = ex.getMessage();
        log.warn(msg, ex);
        model.addAttribute("msg", msg != null && msg.indexOf("SQL") > -1 ? "未知异常，请联系管理员" : msg);
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String noHandlerFoundExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model, NoHandlerFoundException ex) {
        res.setStatus(404);
        model.addAttribute("code", 404);
        model.addAttribute("msg", "您请求的资源未找到！");
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(WonderResourceNotFoundException.class)
    public String wonderResourceNotFoundExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model, WonderException ex) {
        res.setStatus(404);
        model.addAttribute("code", ex.getCode());
        model.addAttribute("msg", ex.getMessage());
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(WonderException.class)
    public String wonderExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model, WonderException ex) {
        model.addAttribute("code", ex.getCode());
        model.addAttribute("msg", ex.getMessage());
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(AuthorizationException.class)
    public String authorizationExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model, AuthorizationException ex) {
        res.setStatus(403);
        model.addAttribute("code", 403);
        model.addAttribute("msg", "非法访问，您无权限执行此操作");
        return checkRequestType(req.getRequestURI(), req);
    }

    private String checkRequestType(String uri, HttpServletRequest req) {
        if (uri.indexOf("cgi-bin") != -1 || uri.indexOf("php") != -1 || uri.indexOf("adm") != -1) {
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
