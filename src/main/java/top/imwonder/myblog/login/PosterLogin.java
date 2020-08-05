/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 16:28:49 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 16:28:49 
 */
package top.imwonder.myblog.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.login.util.LoginUtil;
import top.imwonder.myblog.util.AbstractController;

@Controller
public class PosterLogin extends AbstractController {
    @RequestMapping(value = { "/wonderpost/login", "/wonderpost/login.html" })
    public String posterLogin(HttpServletRequest req) {
        switch (LoginUtil.checkLonginState(req, "wonderpost")) {
            case PASS:
                return "wonderpost/login";
            default:
                return "redirect:/wonderpost/index.html";
        }
    }
}