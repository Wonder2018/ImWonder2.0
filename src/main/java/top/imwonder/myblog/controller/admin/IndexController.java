package top.imwonder.myblog.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.controller.AbstractController;
import top.imwonder.myblog.dao.MenuDAO;
import top.imwonder.myblog.domain.Menu;

@Controller("adminIndexControler")
@RequestMapping(value = "/wonderlandsadmin")
public class IndexController extends AbstractController {

    @Autowired
    private MenuDAO mDAO;

    @RequestMapping(value = { "/login", "login.html" })
    public String login(Model model) {
        return "admin/login";
    }

    @RequestMapping(value = { "/", "/index", "/index.html" })
    public String index(Model model) {
        List<Menu> lmenu = mDAO.loadMore(" order by w_order");
        model.addAttribute("lmenu", lmenu);
        return "admin/index";
    }

    @RequestMapping(value = {"/wellcome", "/wellcome.html" })
    public String wellcome(Model model) {
        model.addAttribute("testArray", new String[] { "1", "2", "3", "4" });
        return "admin/wellcome";
    }

    @RequestMapping("/menu/listMenu.html")
    public String listMenu(Model model) {
        List<Menu> lmenu = mDAO.loadMore(" order by w_order");
        model.addAttribute("lmenu", lmenu);
        return "admin/listMenu";
    }
}