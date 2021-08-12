package top.imwonder.myblog.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.myblog.domain.RolePermission;
import top.imwonder.myblog.domain.UserRole;
import top.imwonder.myblog.pojo.UserTicket;
import top.imwonder.myblog.services.login.LoginOAService;

@Slf4j
@Service
public class WonderUserDetailsService implements UserDetailsService {

	@Autowired
	private LoginOAService loas;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserTicket user = loas.loadBaseInfoByLoginName(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在或密码错误！");
		}
		return loadAuthorizationInfo(user);
	}

	private UserTicket loadAuthorizationInfo(UserTicket user) {
		log.debug("user:{}", user.getUsername());
		List<UserRole> urs = loas.loadUserRole(user.getUsername());
		if (urs.isEmpty()) {
			return user.lockValue();
		}
		Object[] params = new Object[urs.size()];
		int i = 0;
		for (UserRole ur : urs) {
			user.addRole(ur.getRoleId());
			params[i++] = ur.getRoleId();
		}
		List<RolePermission> rps = loas.loadPerms(params);
		for (RolePermission rp : rps) {
			user.addPermission(rp.getPermId());
		}
		return user.lockValue();
	}
}
