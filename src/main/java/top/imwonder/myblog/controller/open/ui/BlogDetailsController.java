package top.imwonder.myblog.controller.open.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import top.imwonder.myblog.SystemProperties;
import top.imwonder.myblog.controller.AbstractUiController;
import top.imwonder.myblog.dao.ArticleDAO;
import top.imwonder.myblog.dao.ArticleResourceDAO;
import top.imwonder.myblog.dao.TagDAO;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.domain.ArticleResource;
import top.imwonder.myblog.domain.Tag;
import top.imwonder.myblog.exception.WonderResourceNotFoundException;
import top.imwonder.myblog.services.OssResourceService;
import top.imwonder.myblog.util.SpiderUtil;

@RequestMapping(value = "/blog")
@Controller("blogDetailsController")
public class BlogDetailsController extends AbstractUiController {

    @Autowired
    private ArticleDAO arDAO;

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private ArticleResourceDAO arrDAO;

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
        String readList = (String) req.getSession().getAttribute("readList");
        if (readList == null) {
            readList = "";
        }
        Article art = null;
        String sql = "select b.w_id, b.w_name, b.w_icon from w_articl_tag a left join w_tag b on a.w_tag_id = b.w_id where a.w_article_id = ?";
        try {
            art = arDAO.loadOne(blogId);
            art.setMarkdownId(ors.getUrlById(art.getMarkdownId()));
            List<Tag> tags = tagDAO.loadMoreBySQL(sql, art.getId());
            art.setTags(tags);
            List<ArticleResource> resourceList = arrDAO.loadMore(" where w_article_id = ? order by w_order asc", blogId);
            art.setResourceList(resourceList);
            String blogTag = String.format("-%s-", blogId);
            if (readList.indexOf(blogTag) == -1 && !SpiderUtil.isSpider(req.getHeader("User-Agent"))) {
                jt.update("update w_article set w_read = ? where w_id = ?", art.getRead() + 1, blogId);
                readList += blogTag;
                req.getSession().setAttribute("readList", readList);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new WonderResourceNotFoundException("404", "你要查看的博客不存在或已被删除！");
        }
        model.addAttribute("blogDetails", art);
        List<Article> articles = arDAO.loadMore(" order by w_post_time desc limit 0,5");
        model.addAttribute("articles", articles);
        initBg(model);
        listTag(model);
        return "blog/blogDetails";
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