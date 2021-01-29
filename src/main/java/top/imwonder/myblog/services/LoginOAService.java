package top.imwonder.myblog.services;

import java.util.List;

import top.imwonder.myblog.domain.RolePermission;
import top.imwonder.myblog.domain.UserRole;
import top.imwonder.myblog.domain.UserTicket;

public interface LoginOAService {
    
    UserTicket loadBaseInfoByLoginName(String id);

	List<UserRole> loadUserRole(String uid);

	List<RolePermission> loadPerms(Object... params);
}
