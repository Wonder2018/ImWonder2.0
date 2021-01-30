package top.imwonder.myblog.services;

import java.util.List;

import top.imwonder.myblog.domain.FriendlyLink;

public interface FriendlyLinkService {
    
    List<FriendlyLink> listFriendlyLinks(int page, int rows);

    void addFriendlyLink(FriendlyLink friendlyLink);

    void updateFriendlyLink(FriendlyLink friendlyLink);

    void deleteFriendlyLink(String id);
}
