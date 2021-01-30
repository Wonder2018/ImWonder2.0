package top.imwonder.myblog.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.imwonder.myblog.dao.friendlylinkdao.FriendlyLinkDisplayDAO;
import top.imwonder.myblog.domain.FriendlyLink;
import top.imwonder.myblog.services.FriendlyLinkService;

@Service
public class FriendlyLinkServiceImpl implements FriendlyLinkService {

    @Autowired
    private FriendlyLinkDisplayDAO fldDAO;

    private static final String PAGE_CAUSE_FORMAT = " where w_disabled = 0 order by w_order asc, w_protocol asc, w_add_time asc limit %d, %d";

    @Override
    public List<FriendlyLink> listFriendlyLinks(int page, int rows) {
        if (rows == -1) {
            return fldDAO.loadMore(" where w_disabled = 0 order by w_order asc, w_protocol asc, w_add_time asc");
        }
        int offset = (page - 1) * rows;
        return fldDAO.loadMore(String.format(PAGE_CAUSE_FORMAT, offset, rows));
    }

    @Override
    public void addFriendlyLink(FriendlyLink friendlyLink) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateFriendlyLink(FriendlyLink friendlyLink) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteFriendlyLink(String id) {
        // TODO Auto-generated method stub

    }

}
