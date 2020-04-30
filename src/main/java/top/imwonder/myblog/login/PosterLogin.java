package top.imwonder.myblog.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PosterLogin {
    @RequestMapping(value={"/wonderpost/login"})
    public String posterLogin(){
        return "wonderpost/login";
    }
}