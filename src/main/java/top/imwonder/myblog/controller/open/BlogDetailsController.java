package top.imwonder.myblog.controller.open;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import top.imwonder.myblog.dao.ArticleDAO;
import top.imwonder.myblog.dao.TagDAO;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.domain.Tag;
import top.imwonder.myblog.exception.WonderResourceNotFoundException;
import top.imwonder.myblog.util.AbstractController;

@RequestMapping(value = "/blog")
@Controller("blogDetailsController")
public class BlogDetailsController extends AbstractController {

    @Autowired
    private JdbcTemplate jt;

    @Autowired
    private ArticleDAO arDAO;

    @Autowired
    private TagDAO tagDAO;

    @RequestMapping(value = { "/blogDetails", "/blogDetails.html" })
    public String blogDetails(String blogId, HttpServletRequest req, Model model) {
        String readList = (String) req.getSession().getAttribute("readList");
        if (readList == null) {
            readList = "";
        }
        Article art = null;
        String sql = "select b.w_id, b.w_name, b.w_icon from w_articl_tag a left join w_tag b on a.w_tag_id = b.w_id where a.w_article_id = ?";
        try {
            art = arDAO.loadOne(blogId);
            art.setMarkdownId(calcOne(art.getMarkdownId()));
            List<Tag> tags = tagDAO.loadMoreBySQL(sql, new Object[] { art.getId() });
            art.setTags(tags);
            String blogTag = String.format("-%s-", blogId);
            if (readList.indexOf(blogTag) == -1) {
                jt.update("update w_article set w_read = ? where w_id = ?", new Object[] { art.getRead() + 1, blogId });
                readList+=blogTag;
                req.getSession().setAttribute("readList", readList);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new WonderResourceNotFoundException("404", "你要查看的博客不存在或已被删除！");
        }
        model.addAttribute("blogDetails", art);
        initBg(model);
        return "blog/blogDetails";
    }
}