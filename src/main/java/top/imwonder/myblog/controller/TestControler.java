/*
 * @Author: Wonder2019 
 * @Date: 2020-04-16 22:41:47 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-04-16 22:41:47 
 */
package top.imwonder.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestControler {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    // @RequestMapping(value = "/error")
    // public String info(Model model) {
    // model.addAttribute("name", "wonder");
    // model.addAttribute("sex", "man");
    // return "error";
    // }
}