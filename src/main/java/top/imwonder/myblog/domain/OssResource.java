package top.imwonder.myblog.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imwonder.util.AbstractDomain;

@Data
@EqualsAndHashCode(callSuper = false)
public class OssResource extends AbstractDomain {

    private String id;

    private String name;

    private String prefix;

    private String category;

    private Integer order;

    private String path;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Object bz;
}