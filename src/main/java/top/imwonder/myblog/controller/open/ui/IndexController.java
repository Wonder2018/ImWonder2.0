/*
 * @Author: Wonder2019 
 * @Date: 2020-04-16 22:41:47 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-03-25 20:58:35
 */
package top.imwonder.myblog.controller.open.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.controller.AbstractUiController;
import top.imwonder.myblog.dao.ArticleDAO;
import top.imwonder.myblog.dao.TagDAO;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.domain.Tag;
import top.imwonder.myblog.pojo.BlogInfo;
import top.imwonder.myblog.services.BlogDetailsService;
import top.imwonder.myblog.services.OssResourceService;

@Controller("openSourceIndexController")
public class IndexController extends AbstractUiController {

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private BlogDetailsService blogDetailsService;

    @Autowired
    private OssResourceService ors;

    @RequestMapping(value = { "/", "index", "index.html" })
    public String index(Model model) throws UnsupportedEncodingException {
        List<Article> articles = blogDetailsService.latestArticles(20);
        String sql = "select b.w_id, b.w_name, b.w_icon from w_articl_tag a left join w_tag b on a.w_tag_id = b.w_id where a.w_article_id = ?";
        List<BlogInfo> blogs = new ArrayList<>();
        for (Article item : articles) {
            BlogInfo bi = BlogInfo.createBlogInfoFromArticle(item);
            if (bi.getFace() != null) {
                bi.setFace(ors.getUrlById(bi.getFace()));
            }
            List<Tag> tags = tagDAO.loadMoreBySQL(sql, item.getId());
            bi.setTags(tags);
            blogs.add(bi);
        }
        model.addAttribute("articles", blogs);
        initBg(model);
        listTag(model);
        return "index";
    }

    @RequestMapping(value = { "addFriendlyLink", "addFriendlyLink.html" })
    public String addFriendlyLink(Model model) throws UnsupportedEncodingException {
        List<Article> articles = blogDetailsService.latestArticles(5);
        model.addAttribute("articles", articles);
        initBg(model);
        listTag(model);
        return "addFriendlyLink";
    }

}