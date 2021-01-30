/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:47:50 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-05-02 18:49:09
 */
package top.imwonder.myblog.dao.userdao;

import org.springframework.stereotype.Component;

import top.imwonder.myblog.domain.User;
import top.imwonder.myblog.exception.WonderException;
import top.imwonder.util.AbstractDAO;

@Component
public class UserPwdDAO extends AbstractDAO<User> {
    
    public UserPwdDAO() {
        domainType = User.class;
        tableName = "w_user";
        pkColumns = new String[] { "w_id" };
        ckColumns = new String[] { "w_username", "w_password" };
        init();
    }

    @Override
    public int insert(User t) {
        throw new WonderException("500", "使用了错误的数据存储对象！");
    }

}