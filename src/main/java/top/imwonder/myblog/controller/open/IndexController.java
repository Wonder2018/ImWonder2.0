/*
 * @Author: Wonder2019 
 * @Date: 2020-04-16 22:41:47 
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-01-30 20:20:30
 */
package top.imwonder.myblog.controller.open;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.controller.AbstractController;
import top.imwonder.myblog.dao.ArticleDAO;
import top.imwonder.myblog.dao.TagDAO;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.domain.Tag;
import top.imwonder.myblog.services.OssResourceService;

@Controller("openSourceIndexController")
public class IndexController extends AbstractController {

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private OssResourceService ors;

    @RequestMapping(value = { "/", "index", "index.html" })
    public String index(Model model) throws UnsupportedEncodingException {
        List<Article> articles = articleDAO.loadMore(" order by w_post_time desc");
        String sql = "select b.w_id, b.w_name, b.w_icon from w_articl_tag a left join w_tag b on a.w_tag_id = b.w_id where a.w_article_id = ?";
        for (Article item : articles) {
            if (item.getFaceId() != null) {
                item.setFaceId(ors.getUrlById(item.getFaceId()));
            }
            List<Tag> tags = tagDAO.loadMoreBySQL(sql, new Object[] { item.getId() });
            item.setTags(tags);
        }
        model.addAttribute("articles", articles);
        initBg(model);
        listTag(model);
        listFriendlyLink(model);
        return "index";
    }
    
    @RequestMapping(value = { "addFriendlyLink", "addFriendlyLink.html" })
    public String addFriendlyLink(Model model) throws UnsupportedEncodingException {
        List<Article> articles = articleDAO.loadMore(" order by w_post_time desc");
        String sql = "select b.w_id, b.w_name, b.w_icon from w_articl_tag a left join w_tag b on a.w_tag_id = b.w_id where a.w_article_id = ?";
        for (Article item : articles) {
            if (item.getFaceId() != null) {
                item.setFaceId(ors.getUrlById(item.getFaceId()));
            }
            List<Tag> tags = tagDAO.loadMoreBySQL(sql, new Object[] { item.getId() });
            item.setTags(tags);
        }
        model.addAttribute("articles", articles);
        initBg(model);
        listTag(model);
        listFriendlyLink(model);
        return "addFriendlyLink";
    }

}