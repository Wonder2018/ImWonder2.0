/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:57:38 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:57:38 
 */
package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.Permission;
import top.imwonder.util.DAOTemplate;

@Component
public class PermissionDAO extends DAOTemplate<Permission> {
    
    public PermissionDAO() {
        domainType = Permission.class;
        tableName = "w_perm";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_name" };
        init();
    }
    
}