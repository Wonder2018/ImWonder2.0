/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:57:31 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-05-02 18:46:54
 */
package top.imwonder.myblog.dao.userdao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.User;
import top.imwonder.util.DAOTemplate;

@Component
public class UserFullDAO extends DAOTemplate<User> {
    public UserFullDAO() {
        domainType = User.class;
        tableName = "w_user";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_username", "w_password", "w_nickname", "w_phone", "w_email", "w_qq", "w_birth", "w_reg_date", "w_disable" };
        init();
    }
}