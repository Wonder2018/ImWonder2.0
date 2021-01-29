/*
 * @Author: Wonder2019 
 * @Date: 2020-04-30 23:16:38 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-29 15:24:31
 */
package top.imwonder.myblog.controller.error;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.myblog.exception.WonderException;
import top.imwonder.myblog.exception.WonderResourceNotFoundException;

@Slf4j
@ControllerAdvice
public class GloubleExceptionHandler implements AccessDeniedHandler {

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
    public String noHandlerFoundExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model,
            NoHandlerFoundException ex) {
        res.setStatus(404);
        model.addAttribute("code", 404);
        model.addAttribute("msg", "您请求的资源未找到！");
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(WonderResourceNotFoundException.class)
    public String wonderResourceNotFoundExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model,
            WonderException ex) {
        res.setStatus(404);
        model.addAttribute("code", ex.getCode());
        model.addAttribute("msg", ex.getMessage());
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(WonderException.class)
    public String wonderExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model,
            WonderException ex) {
        model.addAttribute("code", ex.getCode());
        model.addAttribute("msg", ex.getMessage());
        return checkRequestType(req.getRequestURI(), req);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String authorizationExceptionHandler(HttpServletRequest req, HttpServletResponse res, Model model,
            Exception ex) {
        res.setStatus(403);
        log.info("in 403!");
        model.addAttribute("code", 403);
        model.addAttribute("msg", "非法访问，您无权限执行此操作");
        return checkRequestType(req.getRequestURI(), req);
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex)
            throws IOException, ServletException {

        log.info("in hand!");
        req.setAttribute("msg", "非法访问，您无权限执行此操作");
        req.setAttribute("code", 403);
        req.getRequestDispatcher("/error/403/非法访问，您无权限执行此操作").forward(req, res);

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
        return uri.contains("api") ? "json" : "error";
    }

}
