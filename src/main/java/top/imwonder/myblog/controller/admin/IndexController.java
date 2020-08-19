package top.imwonder.myblog.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.dao.MenuDAO;
import top.imwonder.myblog.domain.Menu;
import top.imwonder.myblog.util.AbstractController;

@Controller("adminIndexControler")
@RequestMapping(value = "/wonderlandsadmin")
public class IndexController extends AbstractController{

    @Autowired
    private MenuDAO mDAO;

    @RequestMapping(value = { "/", "/index", "/index.html" })
    public String index(Model model) {
        List<Menu> lmenu = mDAO.loadMore(" order by w_order", emptyObj);
        model.addAttribute("lmenu", lmenu);
        return "admin/index";
    }

    @RequestMapping(value = { "/", "/wellcome", "/wellcome.html" })
    public String wellcome(Model model) {
        model.addAttribute("testArray", new String[]{"1","2","3","4"});
        return "admin/wellcome";
    }
}