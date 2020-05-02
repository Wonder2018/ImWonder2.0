/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:47:44 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:47:44 
 */
package top.imwonder.myblog.dao.userdao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.User;
import top.imwonder.myblog.exception.WonderException;
import top.imwonder.util.DAOTemplate;

@Component
public class UserInfoDAO extends DAOTemplate<User> {
    public UserInfoDAO() {
        domainType = User.class;
        tableName = "w_user";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_username", "w_nickname", "w_phone", "w_email", "w_qq", "w_birth", "w_reg_date", "w_disable" };
        init();
    }

    @Override
    public int insert(User t) {
        throw new WonderException("500", "使用了错误的数据存储对象！");
    }
    
}