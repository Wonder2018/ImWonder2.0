package top.imwonder.myblog.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.login.util.LoginUtil;
import top.imwonder.myblog.util.AbstractController;

@Controller
public class AdminLogin extends AbstractController {

    @RequestMapping(value = { "/wonderlandsadmin/login" })
    public String adminLogin(HttpServletRequest req) {
        switch (LoginUtil.checkLonginState(req, "wonderlandsadmin")) {
            case SKIP:
                return "redirect:/wonderlandsadmin/index.html";
            case RESET:
                Subject sub = SecurityUtils.getSubject();
                sub.logout();
            default:
                return "wonderlandsadmin/login";
        }

    }
}