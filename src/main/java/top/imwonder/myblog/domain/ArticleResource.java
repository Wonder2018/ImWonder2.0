package top.imwonder.myblog.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imwonder.util.AbstractDomain;

@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleResource extends AbstractDomain {

    private String articleId;

    private Integer order;

    private String resourceId;

}