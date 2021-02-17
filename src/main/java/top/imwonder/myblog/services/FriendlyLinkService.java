package top.imwonder.myblog.services;

import java.util.List;

import top.imwonder.myblog.domain.FriendlyLink;
import top.imwonder.myblog.pojo.FriendlyLinkForm;

public interface FriendlyLinkService {
    
    List<FriendlyLink> listFriendlyLinks(int page, int rows);

    void addFriendlyLink(FriendlyLink friendlyLink);

    FriendlyLink addFriendlyLinkOpen(FriendlyLinkForm friendlyLinkForm);

    void updateFriendlyLink(FriendlyLink friendlyLink);

    void deleteFriendlyLink(String id);
}
