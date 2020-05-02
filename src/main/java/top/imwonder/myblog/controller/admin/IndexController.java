package top.imwonder.myblog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminIndexControler")
@RequestMapping(value = "/wonderlandsadmin")
public class IndexController {

    @RequestMapping(value = { "/", "/index", "/index.html" })
    public String index() {
        return "wonderlandsadmin/index";
    }
}