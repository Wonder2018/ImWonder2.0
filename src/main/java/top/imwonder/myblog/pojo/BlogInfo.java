package top.imwonder.myblog.pojo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.domain.ArticleResource;
import top.imwonder.myblog.domain.Tag;

@Data
public class BlogInfo {

    private String id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;

    private String keywords;

    private String summary;

    private Integer read;

    private List<Tag> tags;

    List<ArticleResource> resourceList;

    private String html;

    private String face;

    public BlogInfo copyFromArticle(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.postTime = article.getPostTime();
        this.keywords = article.getKeywords();
        this.summary = article.getSummary();
        this.read = article.getRead();
        this.face = article.getFaceId();
        return this;
    }

    public static BlogInfo createBlogInfoFromArticle(Article article) {
        return new BlogInfo().copyFromArticle(article);
    }
}
