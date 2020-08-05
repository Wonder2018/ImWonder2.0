package top.imwonder.myblog.controller.open;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.util.AbstractController;


@RequestMapping(value = "/blog")
@Controller("blogDetailsController")
public class BlogDetailsController extends AbstractController {
    @RequestMapping(value = { "/blogDetails", "/blogDetails.html"})
    public String blogDetails(String blogId,Model model) {
        initBg(model);
        return "blog/blogDetails";
    }
}