package top.imwonder.myblog.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imwonder.util.AbstractDomain;

@Data
@EqualsAndHashCode(callSuper = false)
public class Menu extends AbstractDomain {

    private String id;

    private String icon;

    private String name;

    private String href;

    private String perm;

    private Integer order;
}