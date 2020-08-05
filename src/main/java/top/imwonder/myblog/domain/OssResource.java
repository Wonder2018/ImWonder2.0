package top.imwonder.myblog.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OssResource {
    
    private String id;

    private String name;

    private String prefix;

    private String category;

    private Integer order;

    private String path;

    private Date createTime;

    private Object bz;
}