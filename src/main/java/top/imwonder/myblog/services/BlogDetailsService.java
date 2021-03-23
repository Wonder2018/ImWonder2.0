package top.imwonder.myblog.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import top.imwonder.myblog.domain.Article;

public interface BlogDetailsService {
    boolean markedReadForUser(HttpServletRequest req,String blogId);

    Article loadArticleById(String articleId);

    List<Article> latestArticles(int limit);

    int setReadCountForArticle(String articleId, int read);
}
