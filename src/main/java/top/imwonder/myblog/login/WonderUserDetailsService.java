package top.imwonder.myblog.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import top.imwonder.myblog.domain.UserRole;
import top.imwonder.myblog.services.LoginOAService;

@Slf4j
@Service
public class WonderUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginOAService loas;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = loas.loadBaseInfo(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在或密码错误！");
        }
        String uid = user.getUsername();
        return new User(uid, pwdEncoder.encode(user.getPassword()),loadAuthorizationInfo(uid) );
    }

    private List<GrantedAuthority> loadAuthorizationInfo(String uid) {
		log.info("user:{}", uid);
        List<UserRole> urs = loas.loadUserRole( uid );
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (urs.isEmpty()) {
			return grantedAuthorities;
		}
		String[] params = new String[urs.size()];
		int i = 0;
		for (UserRole ur : urs) {
			grantedAuthorities.add(new SimpleGrantedAuthority(ur.getRoleId()));
			params[i++] = ur.getRoleId();
		}
		// List<RolePermission> rps = uis.loadPerms(params);
		// for (RolePermission rp : rps) {
		// 	if(!isLoginFromPoster || (isLoginFromPoster && rp.getPermId().startsWith("poster:"))){
		// 		saoi.addStringPermission(rp.getPermId());
		// 	}
		// }
		return AuthorityUtils.commaSeparatedStringToAuthorityList("dsjhd,dsds");
	}
    
}
