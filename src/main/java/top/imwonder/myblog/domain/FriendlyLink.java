package top.imwonder.myblog.domain;

import java.util.Date;

import lombok.Data;

@Data
public class FriendlyLink {

    /** HTTPS */
    public static final int PROTOCOL_HTTPS = 1;

    /** HTTP */
    public static final int PROTOCOL_HTTP = 2;

    private String id;

    private String url;

    private int protocol;

    private String siteName;

    private String icon;

    private Date addTime;

    private String webmaster;

    private String email;

    private String password;

    private Boolean varified;

    private Date lastVarified;

    private Boolean disabled;

    private Integer order;
}
