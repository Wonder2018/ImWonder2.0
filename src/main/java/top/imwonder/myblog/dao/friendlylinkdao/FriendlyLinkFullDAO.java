package top.imwonder.myblog.dao.friendlylinkdao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.FriendlyLink;
import top.imwonder.util.AbstractDAO;

@Component
public class FriendlyLinkFullDAO extends AbstractDAO<FriendlyLink> {

    public FriendlyLinkFullDAO() {
        domainType = FriendlyLink.class;
        tableName = "w_friendly_link";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_url", "w_protocol", "w_site_name", "w_icon", "w_add_time", "w_webmaster", "w_email", "w_password", "w_varified", "w_last_varified", "w_disabled", "w_order" };
        init();
    }
}