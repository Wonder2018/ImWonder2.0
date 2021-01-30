package top.imwonder.myblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.controller.AbstractController;
import top.imwonder.myblog.services.admin.MenuService;

@Controller("adminIndexControler")
@RequestMapping(value = "/wonderlandsadmin")
public class IndexController extends AbstractController {

    @Autowired
    private MenuService ms;

    @RequestMapping(value = { "/login", "login.html" })
    public String login(Model model) {
        return "admin/login";
    }

    @RequestMapping(value = { "/", "/index", "/index.html" })
    public String index(Model model) {
        model.addAttribute("lmenu", ms.listMenus());
        return "admin/index";
    }

    @RequestMapping(value = {"/wellcome", "/wellcome.html" })
    public String wellcome(Model model) {
        model.addAttribute("testArray", new String[] { "1", "2", "3", "4" });
        return "admin/wellcome";
    }

    @RequestMapping("/menu/listMenu.html")
    public String listMenu(Model model) {
        model.addAttribute("lmenu", ms.listMenus());
        return "admin/listMenu";
    }
}