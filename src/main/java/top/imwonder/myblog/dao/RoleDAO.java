/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:57:41 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:57:41 
 */
package top.imwonder.myblog.dao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.Role;
import top.imwonder.util.DAOTemplate;

@Component
public class RoleDAO extends DAOTemplate<Role> {
    
    public RoleDAO() {
        domainType = Role.class;
        tableName = "w_perm";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_name" };
        init();
    }
    
}