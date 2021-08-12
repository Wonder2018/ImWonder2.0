package top.imwonder.myblog.pojo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imwonder.myblog.domain.Article;
import top.imwonder.myblog.domain.ArticleResource;
import top.imwonder.myblog.domain.Tag;
import top.imwonder.util.AbstractDomain;

@Data
@EqualsAndHashCode(callSuper = false)
public class BlogInfo extends AbstractDomain {

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

    public static BlogInfo createBlogInfoFromArticle(Article article) {
        BlogInfo bi = new BlogInfo();
        bi.copyFrom(article, true);
        return bi;
    }
}
