package top.imwonder.myblog.login;

import java.util.List;

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

import lombok.extern.slf4j.Slf4j;
import top.imwonder.myblog.dao.RolePermissionDAO;
import top.imwonder.myblog.dao.UserRoleDAO;
import top.imwonder.myblog.dao.userdao.UserPwdDAO;
import top.imwonder.myblog.domain.RolePermission;
import top.imwonder.myblog.domain.User;
import top.imwonder.myblog.domain.UserRole;
import top.imwonder.myblog.services.PasswordService;

@Slf4j
public class WonderJdbcRealm extends JdbcRealm {

	@Autowired
	private UserPwdDAO userDao;

	@Autowired
	private UserRoleDAO urDao;

	@Autowired
	private RolePermissionDAO rpDao;

	@Autowired
	private PasswordService passwordService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		final WonderUPT wonderUPT = (WonderUPT) token;
		final String username = wonderUPT.getUsername();
		final String password = new String(wonderUPT.getPassword());

		if (username == null) {
			// May Not Happended
			throw new AccountException("Null usernames are not allowed by this realm.");
		}

		if (!wonderUPT.isCorrectCaptcha()) {
			return null;
		}

		SimpleAuthenticationInfo saei = null;
		User u = userDao.loadOne("where w_username=? and w_disable=0", new Object[] { username });

		if (u != null) {
			boolean valid = passwordService.validatePassword(password, (String) u.getPassword());
			if (valid) {
				String rn = getName();
				SimplePrincipalCollection spc = new SimplePrincipalCollection();
				spc.add(u.getId(), rn);
				spc.add(wonderUPT.getRoleBase(), rn);
				saei = new SimpleAuthenticationInfo(spc, wonderUPT.getPassword());
			}
		}

		return saei;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object obj = principals.getPrimaryPrincipal();
		log.info("user:{}", obj);
		SimpleAuthorizationInfo saoi = new SimpleAuthorizationInfo();
		List<UserRole> urs = urDao.loadMore("where w_user_id=?", new Object[] { obj });
		if (urs.isEmpty()) {
			return saoi;
		}
		Object[] params = new Object[urs.size()];
		int i = 0;
		for (UserRole ur : urs) {
			saoi.addRole(ur.getRoleId());
			params[i++] = ur.getRoleId();
		}
		List<RolePermission> rps = rpDao.loadMore("where w_role_id in " + buildQuestionMarks(urs.size()), params);
		boolean isLoginFromPoster = "poster".equals(principals.asList().get(1));
		for (RolePermission rp : rps) {
			if(!isLoginFromPoster || (isLoginFromPoster && rp.getPermId().startsWith("poster:"))){
				saoi.addStringPermission(rp.getPermId());
			}
		}
		return saoi;
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
