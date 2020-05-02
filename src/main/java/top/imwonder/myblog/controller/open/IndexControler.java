/*
 * @Author: Wonder2019 
 * @Date: 2020-04-16 22:41:47 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-05-02 19:33:57
 */
package top.imwonder.myblog.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("openSourceIndexControler")
public class IndexControler {

    @RequestMapping(value = { "/", "index", "index.html" })
    public String index() {
        return "index";
    }

    

}