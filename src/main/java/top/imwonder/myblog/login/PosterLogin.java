package top.imwonder.myblog.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.login.util.LoginUtil;

@Controller
public class PosterLogin {
    @RequestMapping(value = { "/wonderpost/login" })
    public String posterLogin(HttpServletRequest req) {
        switch (LoginUtil.checkLonginState(req, "wonderpost")) {
            case PASS:
                return "wonderpost/login";
            default:
                return "redirect:/wonderpost/index.html";
        }
    }
}