package top.imwonder.myblog.login;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.util.AbstractController;
import top.imwonder.myblog.util.StringUtil;

@Controller
public class AdminLogin extends AbstractController {

    @RequestMapping(value={"/wonderlandsadmin/login"})
    public String adminLogin(HttpServletRequest req){
        String user = req.getRemoteUser();
        String loginFrom = (String)req.getSession().getAttribute("loginFrom");
        if(StringUtil.isAllNotEmpty(user,loginFrom)&& loginFrom.contains("wonderlandsadmin")){
            return "redirect:/wonderlandsadmin/index.html";
        }
        return "wonderlandsadmin/login";
    }
}