package top.imwonder.myblog.services;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import top.imwonder.myblog.domain.RolePermission;
import top.imwonder.myblog.domain.UserRole;

public interface LoginOAService {
    
    User loadBaseInfo(String id);

	List<UserRole> loadUserRole(String uid);

	List<RolePermission> loadPerms(String[] params);
}
