package top.imwonder.myblog.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.dao.MenuDAO;
import top.imwonder.myblog.domain.Menu;
import top.imwonder.myblog.util.AbstractController;

@Controller("menuControler")
@RequestMapping(value = "/wonderlandsadmin/menu")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MenuController extends AbstractController{

    @Autowired
    private MenuDAO mDAO;

    @RequestMapping(value = { "/listMenu.html" })
    public String listMenu(Model model) {
        List<Menu> lmenu = mDAO.loadMore(" order by w_order", emptyObj);
        model.addAttribute("lmenu", lmenu);
        return "admin/listMenu";
    }

    @Transactional
    @RequestMapping(value = {"/updateAjax"})
    public String update(Menu menu, Model model) {
        Menu old = mDAO.loadOne(menu.getId());
        old.setHref(menu.getHref());
        old.setIcon(menu.getIcon());
        old.setName(menu.getName());
        old.setOrder(menu.getOrder());
        old.setPerm(menu.getPerm());
        mDAO.update(old);
        model.addAttribute("result", old);
        model.addAttribute("code", 200);
        model.addAttribute("msg", "更新成功！");
        return "json";
    }
}