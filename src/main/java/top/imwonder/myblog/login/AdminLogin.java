/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 16:28:32 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 16:28:32 
 */
package top.imwonder.myblog.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.login.util.LoginUtil;
import top.imwonder.myblog.util.AbstractController;

@Controller
public class AdminLogin extends AbstractController {

    @RequestMapping(value = { "/wonderlandsadmin/login", "/wonderlandsadmin/login.html" })
    public String adminLogin(HttpServletRequest req, Model model) {
        switch (LoginUtil.checkLonginState(req, "wonderlandsadmin")) {
            case SKIP:
                model.asMap().clear();
                return "redirect:/wonderlandsadmin/index";
            case RESET:
                Subject sub = SecurityUtils.getSubject();
                sub.logout();
            default:
                return "admin/login";
        }
    }
}