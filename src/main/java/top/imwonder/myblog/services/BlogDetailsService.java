package top.imwonder.myblog.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.pojo.BlogInfo;

public interface BlogDetailsService {

    /**
     * 为当前会话标记已读文章
     * 
     * @param req    请求
     * @param blogId 要标记的文章ID
     * @return 只有当文章存在，并且会话之前未读该文章时返回 <code>true</code> ，其他情况返回 <code>false</code>
     */
    boolean markedReadForUser(HttpServletRequest req, String blogId);

    BlogInfo loadBlogById(String blogId);

    /**
     * 获取最近文章列表
     * 
     * @param limit 要获取的文章数量
     * @return
     */
    List<Article> latestArticles(int limit);

    /**
     * 设置文章阅读量
     * 
     * @param blogId
     * @param read
     * @return
     */
    int setReadCountForArticle(String blogId, int read);
}
