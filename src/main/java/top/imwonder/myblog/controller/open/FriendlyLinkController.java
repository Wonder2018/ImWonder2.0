package top.imwonder.myblog.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import top.imwonder.myblog.controller.AbstractController;
import top.imwonder.myblog.pojo.FriendlyLinkForm;
import top.imwonder.myblog.services.FriendlyLinkService;

@Controller("FriendlyLinkController")
public class FriendlyLinkController extends AbstractController {

    @Autowired
    private FriendlyLinkService fls;

    @Autowired
    private PasswordEncoder pe;

    @PostMapping("/api/addFriendlyLink")
    public String addFriendlyLink(FriendlyLinkForm flf, Model model) {
        flf.setPwd(pe.encode(flf.getPwd()));
        fls.addFriendlyLinkOpen(flf);
        return "json";
    }
}
