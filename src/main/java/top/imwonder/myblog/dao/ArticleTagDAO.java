package top.imwonder.myblog.dao;

import top.imwonder.myblog.domain.ArticleTag;
import top.imwonder.util.DAOTemplate;

public class ArticleTagDAO extends DAOTemplate<ArticleTag> {

    public ArticleTagDAO() {
        domainType = ArticleTag.class;
        tableName = "w_article_tag";
        pkColumns = new String[] { "w_tag_id", "w_article_id" };
        init();
    }
    
}