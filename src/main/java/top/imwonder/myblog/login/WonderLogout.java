/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:58:31 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:58:31 
 */
package top.imwonder.myblog.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.util.AbstractController;

@Controller
public class WonderLogout extends AbstractController {

    @RequestMapping(value = { "/wonderlandsadmin/logout", "/wonderpost/logout" })
    public String logout(HttpServletRequest req, Model model) {
        SecurityUtils.getSubject().logout();
        model.asMap().clear();
        return "redirect:/";
    }
}