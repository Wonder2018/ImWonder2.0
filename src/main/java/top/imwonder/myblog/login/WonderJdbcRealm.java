package top.imwonder.myblog.login;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

// import edu.cust.rbac.dao.RolePermissionDAO;
// import edu.cust.rbac.dao.UserRoleDAO;
// import edu.cust.rbac.domain.RolePermission;
// import edu.cust.rbac.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import top.imwonder.myblog.services.PasswordService;

@Slf4j
public class WonderJdbcRealm extends JdbcRealm {
	//@Autowired
	//private UserDAOWithAll userDao;
	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private UserRoleDAO userRoleDao;
	// @Autowired
	// private RolePermissionDAO rolePermissionDao;
	@Autowired
	private PasswordService passwordService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		final WonderUPT upToken = (WonderUPT) token;
		final String username = upToken.getUsername();
		final String password = new String(upToken.getPassword());
		// Null username is invalid
		if (username == null) {
			throw new AccountException("Null usernames are not allowed by this realm.");
		}

		SimpleAuthenticationInfo info = null;
		String roleBase = upToken.getRoleBase();

		// Map<String, Object> u = roleBase.equals("adm") ? jt.queryForMap("select c_username id, c_password pass from c_adm where c_username=?", username) :
		// 	jt.queryForMap("select c_id id, c_password pass from c_user where c_phone=? and c_disable=0", username);
			//userDao.loadOne("where c_phone=? and c_disable=0", new Object[] { username });
			Map<String, Object> u = jt.queryForMap("select w_id as id, w_username as username, w_password as password from w_user where w_username=? and w_disable=0",username);
		if(!upToken.isCorrectCaptcha()){
			return info;
		}
		if (u != null && !u.isEmpty()) {
			boolean valid = passwordService.validatePassword(password, (String)u.get("password"));
			if(valid) {
				String rn = getName();
				SimplePrincipalCollection spc = new SimplePrincipalCollection();
				spc.add(u.get("id"),rn);
				info = new SimpleAuthenticationInfo(spc, upToken.getPassword());
			}
		}
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object obj = principals.getPrimaryPrincipal();
		log.info("pp:{}", obj);
		SimpleAuthorizationInfo ai = new SimpleAuthorizationInfo();
		// List<UserRole> urs = userRoleDao.loadMore(" where c_user_id=?", new Object[] { obj });
		// if(urs.isEmpty()) {
		// 	return ai;
		// }
		// Object[] params = new Object[urs.size()];
		// int i = 0;
		// for (UserRole ur : urs) {
		// 	ai.addRole(ur.getRoleId());
		// 	params[i++] = ur.getRoleId();
		// }
		// List<RolePermission> rps = rolePermissionDao
		// 		.loadMore("where c_role_id in " + buildQuestionMarks(urs.size()), params);
		// for (RolePermission rp : rps) {
		// 	ai.addStringPermission(rp.getPermId());
		// }
		return ai;
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
