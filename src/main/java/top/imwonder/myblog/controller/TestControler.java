package top.imwonder.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestControler {

    @RequestMapping(value="/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/info")
    public String info(Model model) {
        model.addAttribute("name", "wonder");
        model.addAttribute("sex", "man");
        return "info";
    }
}