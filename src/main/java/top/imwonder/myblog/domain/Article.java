package top.imwonder.myblog.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imwonder.myblog.pojo.BlogInfo;
import top.imwonder.util.AbstractDomain;
import top.imwonder.util.annotation.DomainName;

@Data
@EqualsAndHashCode(callSuper = false)
public class Article extends AbstractDomain {

    private String id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;

    @DomainName(name = "face", type = BlogInfo.class)
    private String faceId;

    private String path;

    private String keywords;

    private String summary;

    private Integer read;

}