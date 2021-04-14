package top.imwonder.myblog.controller.open.ui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import top.imwonder.myblog.SystemProperties;
import top.imwonder.myblog.controller.AbstractUiController;
import top.imwonder.myblog.exception.WonderResourceNotFoundException;
import top.imwonder.myblog.pojo.BlogInfo;
import top.imwonder.myblog.services.BlogDetailsService;
import top.imwonder.myblog.services.OssResourceService;

@RequestMapping(value = "/blog")
@Controller("blogDetailsController")
public class BlogDetailsController extends AbstractUiController {

    @Autowired
    private BlogDetailsService bds;

    @Autowired
    private OssResourceService ors;

    @Autowired
    private SystemProperties sp;

    @RequestMapping(value = { "/api/updateIcon" })
    public String upicon(Model model) {
        sp.getIconfontUrl(true);
        model.addAttribute("ok", "fun!");
        return "json";
    }

    @RequestMapping("/details/{blogId}")
    public String blogDetails(@PathVariable("blogId") String blogId, HttpServletRequest req, Model model) {
        // try {
        BlogInfo bi = bds.loadBlogById(blogId);
        if (bds.markedReadForUser(req, blogId)) {
            bi.setRead(bi.getRead() + 1);
            bds.setReadCountForArticle(blogId, bi.getRead());
        }
        model.addAttribute("blogDetails", bi);
        model.addAttribute("articles", bds.latestArticles(5));
        initBg(model);
        listTag(model);
        return "blog/blogDetails";
        // } catch (WonderResourceNotFoundException e) {
        // log.debug(e.getMessage());
        // throw new WonderResourceNotFoundException("404", "你要查看的博客不存在或已被删除！", e);
        // }
    }

    @RequestMapping(value = { "/resource/{resourceId}" })
    public RedirectView blogResource(@PathVariable("resourceId") String resourceId, Model model) {
        String url = ors.getUrlById(resourceId);
        if ("".equals(url)) {
            throw new WonderResourceNotFoundException("404", "资源不存在或已被删除！");
        }
        model.asMap().clear();
        return new RedirectView(url, false, true, false);
    }
}