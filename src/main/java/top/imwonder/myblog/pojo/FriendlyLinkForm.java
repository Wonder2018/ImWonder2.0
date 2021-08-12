package top.imwonder.myblog.pojo;

import lombok.Data;
import top.imwonder.myblog.domain.FriendlyLink;
import top.imwonder.myblog.enumeration.IconType;
import top.imwonder.util.annotation.DomainName;

@Data
public class FriendlyLinkForm {

    @DomainName(name = "url", type = FriendlyLink.class)
    private String url;

    @DomainName(name = "siteName", type = FriendlyLink.class)
    private String siteName;

    private IconType iconType;

    private Object icon;

    @DomainName(name = "webMaster", type = FriendlyLink.class)
    private String master;

    @DomainName(name = "email", type = FriendlyLink.class)
    private String email;

    @DomainName(name = "password", type = FriendlyLink.class)
    private String pwd;

}
