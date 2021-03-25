package top.imwonder.myblog.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Article {
    
    private String id;

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;

    private String faceId;

    private String path;

    private String keywords;

    private String summary;

    private Integer read;
    
}