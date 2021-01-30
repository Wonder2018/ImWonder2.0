/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:57:44 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:57:44 
 */
package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.RolePermission;
import top.imwonder.util.AbstractDAO;

@Component
public class RolePermissionDAO extends AbstractDAO<RolePermission> {
    
    public RolePermissionDAO() {
        domainType = RolePermission.class;
        tableName = "w_role_perm";
        pkColumns = new String[] { "w_role_id", "w_perm_id" };
        init();
    }
    
}