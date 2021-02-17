package top.imwonder.myblog.pojo;

import lombok.Data;
import top.imwonder.myblog.enumeration.IconType;

@Data
public class FriendlyLinkForm {

    
    
    private String url;

    private String siteName;

    private IconType iconType;

    private Object icon;

    private String master;

    private String email;

    private String pwd;

}
