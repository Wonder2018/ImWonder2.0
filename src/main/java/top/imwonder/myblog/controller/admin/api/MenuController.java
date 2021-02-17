package top.imwonder.myblog.controller.admin.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.domain.Menu;
import top.imwonder.myblog.services.admin.MenuService;
import top.imwonder.util.StringUtil;

@Controller("menuControler")
@RequestMapping("/wonderlandsadmin/api/menus")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MenuController {

    @Autowired
    private MenuService ms;

    @PostMapping("/{name}/{icon}/{perm}/{order}/{href}")
    public String add(Menu menu, Model model) {
        menu.setHref(StringUtil.decodeBase64ToString(menu.getHref()));
        menu.setPerm(StringUtil.decodeBase64ToString(menu.getPerm()));
        ms.addMenu(menu);
        model.addAttribute("code", 200);
        model.addAttribute("msg", "添加成功！");
        return "json";
    }

    @PutMapping("/{id}/{name}/{icon}/{perm}/{order}/{href}")
    public String update(Menu menu, Model model) {
        menu.setHref(StringUtil.decodeBase64ToString(menu.getHref()));
        menu.setPerm(StringUtil.decodeBase64ToString(menu.getPerm()));
        model.addAttribute("menu", ms.updateMenu(menu));
        model.addAttribute("code", 200);
        model.addAttribute("msg", "更新成功！");
        return "json";
    }

    @DeleteMapping(value = { "/{id}" })
    public String delete(@PathVariable("id") String id, Model model) {
        ms.deleteMenu(id);
        model.addAttribute("code", 200);
        model.addAttribute("msg", "删除成功！");
        return "json";
    }
}