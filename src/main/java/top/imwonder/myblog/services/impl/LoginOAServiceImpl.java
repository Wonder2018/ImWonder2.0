package top.imwonder.myblog.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import top.imwonder.myblog.dao.RolePermissionDAO;
import top.imwonder.myblog.dao.UserRoleDAO;
import top.imwonder.myblog.dao.userdao.UserPwdDAO;
import top.imwonder.myblog.domain.RolePermission;
import top.imwonder.myblog.domain.UserRole;
import top.imwonder.myblog.services.LoginOAService;

@Service
public class LoginOAServiceImpl implements LoginOAService {

    @Autowired
    private UserPwdDAO upDAO;

    @Autowired
    private UserRoleDAO urDAO;

    @Autowired
    private RolePermissionDAO rpDAO;

    @Override
    public User loadBaseInfo(String id) {
        try {
            top.imwonder.myblog.domain.User userInfo = upDAO.loadOne("where w_username=? and w_disable=0",
                    new Object[] { id });
            return new User(userInfo.getId(), userInfo.getPassword(), null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<UserRole> loadUserRole(String uid) {
        return urDAO.loadMore("where w_user_id=?", new Object[] { uid });
    }

    @Override
    public List<RolePermission> loadPerms(String[] params) {
        return rpDAO.loadMore("where w_role_id in " + buildQuestionMarks(params.length), params);
    }

    private String buildQuestionMarks(int n) {
        StringBuffer sb = new StringBuffer(n * 2 + 1);
        sb.append('(');
        for (int i = 0; i < n; i++) {
            sb.append('?');
            sb.append(',');
        }
        sb.deleteCharAt(n * 2);
        sb.append(')');
        return sb.toString();
    }
}
