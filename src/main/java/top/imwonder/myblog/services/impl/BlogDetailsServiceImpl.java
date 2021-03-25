package top.imwonder.myblog.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;
import top.imwonder.myblog.Env;
import top.imwonder.myblog.dao.ArticleDAO;
import top.imwonder.myblog.dao.ArticleResourceDAO;
import top.imwonder.myblog.dao.TagDAO;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.domain.ArticleResource;
import top.imwonder.myblog.domain.Tag;
import top.imwonder.myblog.exception.WonderResourceNotFoundException;
import top.imwonder.myblog.pojo.BlogInfo;
import top.imwonder.myblog.services.BlogDetailsService;
import top.imwonder.myblog.services.OssResourceService;
import top.imwonder.myblog.services.RedisService;
import top.imwonder.myblog.util.SpiderUtil;
import top.imwonder.util.FileOperatingUtil;
import top.touchface.md2x.Md2x;

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

    @Resource
    private RedisService<String> redisService;

    @Autowired
    private Md2x md2x;

    @Autowired
    private Env env;

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
    public BlogInfo loadBlogById(String blogId) {
        Article art = arDAO.loadOneByPrimaryKey(blogId);
        List<Tag> tags = tagDAO.loadMoreBySQL(LOAD_TAG_SQL, art.getId());
        List<ArticleResource> resourceList = arrDAO.loadMore(" where w_article_id = ? order by w_order asc", blogId);
        BlogInfo bi = new BlogInfo();
        bi.copyFromArticle(art);
        bi.setTags(tags);
        bi.setResourceList(resourceList);
        bi.setHtml(cacheBlogHtml(bi, art.getPath()));
        return bi;
    }

    @Override
    public List<Article> latestArticles(int limit) {
        String sql = String.format(" order by w_post_time desc limit 0,%d", limit);
        return arDAO.loadMore(sql);
    }

    @Override
    public int setReadCountForArticle(String blogId, int read) {
        return jt.update("update w_article set w_read = ? where w_id = ?", read, blogId);
    }

    private String cacheBlogHtml(BlogInfo blog, String path) {
        String html = redisService.get(blog.getId());
        if (html != null) {
            return html;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(blog.getPostTime());
            String md = FileOperatingUtil.readForString(new File(env.getArticleRoot(), path));
            html = md2x.parse(md);
            blog.setHtml(html);
            long ttl = injectBlogResource(blog);
            redisService.set(blog.getId(), blog.getHtml(), ttl);
            return html;
        } catch (IOException e) {
            throw new WonderResourceNotFoundException("404", "你要查看的博客不存在或已被删除！", e);
        }
    }

    private long injectBlogResource(BlogInfo blog) {
        List<ArticleResource> ars = blog.getResourceList();
        if (ars.size() < 1) {
            return env.getResourceExpireInSeconds();
        }
        long ttl = -1;
        String html = blog.getHtml();
        for (ArticleResource ar : ars) {
            String url = ors.getUrlById(ar.getResourceId());
            html = html.replace(ar.getResourceId(), url);
            ttl = calcTTL(ttl, ar.getResourceId());
        }
        blog.setHtml(html);
        if (ttl < 0) {
            return env.getResourceExpireInSeconds();
        }
        return ttl;
    }

    private long calcTTL(long oldTTL, String resourceId) {
        long ttl = redisService.getExpire(resourceId);
        if (oldTTL <= 0 && ttl > 0) {
            return ttl;
        }
        if (ttl > 0 && ttl < oldTTL) {
            return ttl;
        }
        return oldTTL;
    }

}
