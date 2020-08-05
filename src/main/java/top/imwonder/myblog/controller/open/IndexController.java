/*
 * @Author: Wonder2019 
 * @Date: 2020-04-16 22:41:47 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-05 21:45:16
 */
package top.imwonder.myblog.controller.open;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.util.AbstractController;

@Controller("openSourceIndexController")
public class IndexController extends AbstractController {

    @RequestMapping(value = { "/", "index", "index.html" })
    public String index(Model model) throws UnsupportedEncodingException {
        initBg(model);
        return "index";
    }

}