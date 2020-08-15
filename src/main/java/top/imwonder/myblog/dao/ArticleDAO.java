package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.Article;
import top.imwonder.util.DAOTemplate;

@Component
public class ArticleDAO extends DAOTemplate<Article>{
    
    public ArticleDAO(){
        domainType = Article.class;
        tableName = "w_article";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_title", "w_post_time", "w_face_id", "w_markdown_id", "w_keywords", "w_summary", "w_read" };
        init();
    }
}