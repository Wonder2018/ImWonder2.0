package top.imwonder.myblog.domain;

import lombok.Data;

@Data
public class Menu {
    
    private String id;

    private String icon;

    private String name;

    private String href;

    private String perm;

    private Integer order;
}