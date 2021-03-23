package top.imwonder.myblog.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import top.imwonder.myblog.dao.ArticleDAO;
import top.imwonder.myblog.dao.ArticleResourceDAO;
import top.imwonder.myblog.dao.TagDAO;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.domain.ArticleResource;
import top.imwonder.myblog.domain.Tag;
import top.imwonder.myblog.services.BlogDetailsService;
import top.imwonder.myblog.services.OssResourceService;
import top.imwonder.myblog.util.SpiderUtil;

@Service
public class BlogDetailsServiceImpl implements BlogDetailsService {

    private static final String LOAD_TAG_SQL = "select b.w_id, b.w_name, b.w_icon from w_articl_tag a left join w_tag b on a.w_tag_id = b.w_id where a.w_article_id = ?";

    @Autowired
    private ArticleDAO arDAO;

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private ArticleResourceDAO arrDAO;

    @Autowired
    private OssResourceService ors;

    @Autowired
    private JdbcTemplate jt;

    @Override
    public boolean markedReadForUser(HttpServletRequest req, String blogId) {
        if (SpiderUtil.isSpider(req.getHeader("User-Agent"))) {
            return false;
        }
        String readList = (String) req.getSession().getAttribute("readList");
        if (readList == null) {
            readList = "";
        }
        String blogTag = String.format("-%s-", blogId);
        if (readList.indexOf(blogTag) == -1) {
            readList += blogTag;
            req.getSession().setAttribute("readList", readList);
            return true;
        }
        return false;
    }

    @Override
    public Article loadArticleById(String articleId) {
        Article art = arDAO.loadOne(articleId);
        art.setMarkdownId(ors.getUrlById(art.getMarkdownId()));
        List<Tag> tags = tagDAO.loadMoreBySQL(LOAD_TAG_SQL, art.getId());
        art.setTags(tags);
        List<ArticleResource> resourceList = arrDAO.loadMore(" where w_article_id = ? order by w_order asc", articleId);
        art.setResourceList(resourceList);
        return art;
    }

    @Override
    public List<Article> latestArticles(int limit) {
        String sql = String.format(" order by w_post_time desc limit 0,%d", limit);
        return arDAO.loadMore(sql);
    }

    @Override
    public int setReadCountForArticle(String articleId, int read) {
        return jt.update("update w_article set w_read = ? where w_id = ?", read, articleId);
    }
}
