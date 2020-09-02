package top.imwonder.myblog.controller.init;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import top.imwonder.myblog.dao.ArticleDAO;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.util.AbstractController;
import top.imwonder.myblog.util.SiteMapUtil;
import top.imwonder.myblog.util.enumeration.ChangeFreqEnum;

@Order(value = 1)
@Component
public class CreateSiteMapTask extends AbstractController implements CommandLineRunner {

    @Autowired
    private ArticleDAO arDAO;

    @Override
    public void run(String... args) throws Exception {
        File sitemap = new File("sitemap.xml");
        if (!sitemap.exists() || System.currentTimeMillis() - sitemap.lastModified() > 604800000) {
            Document doc = SiteMapUtil.createSiteMap();
            List<Article> articles = arDAO.loadMore(" Order by w_post_time desc", emptyObj);
            SiteMapUtil.addUrl(doc, "http://www.imwonder.top", new Date(), ChangeFreqEnum.WEEKLY, "1");
            for (Article article : articles) {
                String loc = String.format("http://www.imwonder.top/blog/blogDetails?blogId=%s", article.getId());
                SiteMapUtil.addUrl(doc, loc, article.getPostTime(), ChangeFreqEnum.WEEKLY, "0.9");
            }
            try (OutputStream os = new FileOutputStream(sitemap)) {
                XMLWriter writer = new XMLWriter(os);
                writer.write(doc);
            }
        }

    }

}
