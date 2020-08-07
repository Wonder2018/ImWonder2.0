package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.ArticleResource;
import top.imwonder.util.DAOTemplate;

@Component
public class ArticleResourceDAO extends DAOTemplate<ArticleResource> {

    public ArticleResourceDAO() {
        domainType = ArticleResource.class;
        tableName = "w_article_resource";
        pkColumns = new String[] { "w_article_id", "w_order" };
        ckColumns = new String[] { "w_image_id" };
        init();
    }
    
}