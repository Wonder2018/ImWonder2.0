package top.imwonder.myblog.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.dao.MenuDAO;
import top.imwonder.myblog.domain.Menu;
import top.imwonder.myblog.util.AbstractController;

@Controller("menuControler")
@RequestMapping(value = "/wonderlandsadmin")
public class MenuController extends AbstractController{

    @Autowired
    private MenuDAO mDAO;

    @RequestMapping(value = { "/listMenu.html" })
    public String listMenu(Model model) {
        List<Menu> lmenu = mDAO.loadMore(" order by w_order", emptyObj);
        model.addAttribute("lmenu", lmenu);
        return "wonderlandsadmin/listMenu";
    }
}