package top.imwonder.myblog.services.admin;

import java.util.List;

import top.imwonder.myblog.domain.Permission;

public interface PermService {

    List<Permission> listPerms();
    
    void addPerm(Permission perm);

    Permission updatePerm(Permission perm);

    void deletePerm(String id);
}
