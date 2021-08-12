package top.imwonder.myblog.controller.admin.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.controller.AbstractUiController;
import top.imwonder.myblog.services.admin.PermService;

@Controller("adminIndexControler")
@RequestMapping(value = "/wonderlandsadmin")
public class IndexController extends AbstractUiController {

    @Autowired
    private PermService ps;

    @RequestMapping(value = { "/login", "login.html" })
    public String login(HttpServletRequest req, Model model) {
        if (req.getParameter("error") != null) {
            AuthenticationException ex = (AuthenticationException) req.getSession()
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            String errorMsg = (ex != null) ? ex.getMessage() : "Invalid credentials";
            model.addAttribute("error", true);
            model.addAttribute("msg", errorMsg);
        }
        return "admin/login";
    }

    @RequestMapping(value = { "/index" })
    public String index(Model model) {
        model.addAttribute("testArray", new String[] { "1", "2", "3", "4" });
        return "admin/index";
    }

    @RequestMapping("/perm")
    public String listPerm(Model model) {
        model.addAttribute("lperm", ps.listPerms());
        return "admin/listPerm";
    }
}