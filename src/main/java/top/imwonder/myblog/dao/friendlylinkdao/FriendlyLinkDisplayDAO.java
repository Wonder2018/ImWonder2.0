package top.imwonder.myblog.dao.friendlylinkdao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.FriendlyLink;
import top.imwonder.util.AbstractDAO;

@Component
public class FriendlyLinkDisplayDAO extends AbstractDAO<FriendlyLink> {

    public FriendlyLinkDisplayDAO() {
        domainType = FriendlyLink.class;
        tableName = "w_friendly_link";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_url", "w_protocol", "w_site_name", "w_icon", "w_varified", "w_disabled" };
        init();
    }

    @Override
    public int delete(Object... params) {
        log.warn("此操作类只支持读取数据");
        return 0;
    }

    @Override
    public int insert(FriendlyLink t) {
        log.warn("此操作类只支持读取数据");
        return 0;
    }

    @Override
    public int update(FriendlyLink t) {
        log.warn("此操作类只支持读取数据");
        return 0;
    }

    @Override
    public int updateWithPk(FriendlyLink t, Object[] oldPks) {
        log.warn("此操作类只支持读取数据");
        return 0;
    }

}
