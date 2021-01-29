package top.imwonder.myblog.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.controller.AbstractController;
import top.imwonder.myblog.dao.MenuDAO;
import top.imwonder.myblog.domain.Menu;
import top.imwonder.util.IdUtil;
import top.imwonder.util.StringUtil;

@Controller("menuControler")
@RequestMapping("/wonderlandsadmin/api/menus")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MenuController extends AbstractController {

    @Autowired
    private MenuDAO mDAO;

    @Transactional
    @PostMapping("/{name}/{icon}/{perm}/{order}/{href}")
    public String insert(Menu menu, Model model) {
        menu.setHref(StringUtil.decodeBase64ToString(menu.getHref()));
        menu.setPerm(StringUtil.decodeBase64ToString(menu.getPerm()));
        menu.setId(IdUtil.uuid());
        mDAO.insert(menu);
        model.addAttribute("code", 200);
        model.addAttribute("msg", "添加成功！");
        return "json";
    }

    @Transactional
    @PutMapping("/{id}/{name}/{icon}/{perm}/{order}/{href}")
    public String update(Menu menu, Model model) {
        menu.setHref(StringUtil.decodeBase64ToString(menu.getHref()));
        menu.setPerm(StringUtil.decodeBase64ToString(menu.getPerm()));
        Menu old = mDAO.loadOne(menu.getId());
        old.setHref(menu.getHref());
        old.setIcon(menu.getIcon());
        old.setName(menu.getName());
        old.setOrder(menu.getOrder());
        old.setPerm(menu.getPerm());
        mDAO.update(old);
        model.addAttribute("menu", old);
        model.addAttribute("code", 200);
        model.addAttribute("msg", "更新成功！");
        return "json";
    }

    @Transactional
    @DeleteMapping(value = { "/{id}" })
    public String delete(@PathVariable("id") String id, Model model) {
        log.info(id);
        mDAO.delete(id);
        model.addAttribute("code", 200);
        model.addAttribute("msg", "删除成功！");
        return "json";
    }
}