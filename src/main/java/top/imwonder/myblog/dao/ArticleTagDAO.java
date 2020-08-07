package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.ArticleTag;
import top.imwonder.util.DAOTemplate;

@Component
public class ArticleTagDAO extends DAOTemplate<ArticleTag> {

    public ArticleTagDAO() {
        domainType = ArticleTag.class;
        tableName = "w_article_tag";
        pkColumns = new String[] { "w_tag_id", "w_article_id" };
        init();
    }
    
}