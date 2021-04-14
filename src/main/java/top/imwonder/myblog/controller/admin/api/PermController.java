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

import top.imwonder.myblog.domain.Permission;
import top.imwonder.myblog.services.admin.PermService;
import top.imwonder.util.StringUtil;

@Controller("permControler")
@RequestMapping("/wonderlandsadmin/api/perms")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PermController {

    @Autowired
    private PermService ps;

    @PostMapping("/{id}/{name}")
    public String add(Permission perm, Model model) {
        perm.setId(StringUtil.decodeBase64ToString(perm.getId()));
        ps.addPerm(perm);
        model.addAttribute("code", 200);
        model.addAttribute("msg", "添加成功！");
        return "json";
    }

    @PutMapping("/{id}/{name}")
    public String update(Permission perm, Model model) {
        perm.setId(StringUtil.decodeBase64ToString(perm.getId()));
        model.addAttribute("perm", ps.updatePerm(perm));
        model.addAttribute("code", 200);
        model.addAttribute("msg", "更新成功！");
        return "json";
    }

    @DeleteMapping(value = { "/{id}" })
    public String delete(@PathVariable("id") String id, Model model) {
        ps.deletePerm(id);
        model.addAttribute("code", 200);
        model.addAttribute("msg", "删除成功！");
        return "json";
    }
}