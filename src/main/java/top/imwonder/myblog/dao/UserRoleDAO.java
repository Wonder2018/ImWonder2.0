/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:57:48 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-05-02 19:43:28
 */
package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.UserRole;
import top.imwonder.util.AbstractDAO;

@Component
public class UserRoleDAO extends AbstractDAO<UserRole> {
    
    public UserRoleDAO() {
        domainType = UserRole.class;
        tableName = "w_user_role";
        pkColumns = new String[] { "w_role_id", "w_user_id" };
        init();
    }
    
}