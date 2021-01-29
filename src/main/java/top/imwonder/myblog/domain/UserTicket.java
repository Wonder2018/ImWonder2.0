package top.imwonder.myblog.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserTicket implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Collection<GrantedAuthority> authorities;

    private String pwd;

    @Getter
    private String loginName;

    private String uid;

    @Getter
    private boolean accountNonExpired = true;

    @Getter
    private boolean accountNonLocked = true;

    @Getter
    private boolean credentialsNonExpired = true;

    @Getter
    private boolean enabled = true;

    private boolean lock;

    
    public UserTicket(String uid, String loginName, String pwd, boolean... isLockValue) {
        this.uid = uid;
        this.loginName = loginName;
        this.pwd = pwd;
        this.authorities = new ArrayList<>();
        this.lock = isLockValue.length == 0 ? false : true;
    }

    public UserTicket(String uid, String loginName, String pwd, Collection<GrantedAuthority> authorities) {
        this.uid = uid;
        this.loginName = loginName;
        this.pwd = pwd;
        this.authorities = authorities == null ? new ArrayList<>() : authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    /**
     * 获取用户 uid
     */
    @Override
    public String getUsername() {
        return uid;
    }

    public UserTicket lockValue() {
        this.lock = true;
        return this;
    }

    public UserTicket setAccountNonExpired(boolean accountNonExpired, boolean... isLockValue) {
        if (lock) {
            log.debug("The Value is Locked!!!");
            return this;
        }
        this.accountNonExpired = accountNonExpired;
        this.lock = isLockValue.length == 0 ? false : true;
        return this;
    }

    public UserTicket setAccountNonLocked(boolean accountNonLocked, boolean... isLockValue) {
        if (lock) {
            log.debug("The Value is Locked!!!");
            return this;
        }
        this.accountNonLocked = accountNonLocked;
        this.lock = isLockValue.length == 0 ? false : true;
        return this;
    }

    public UserTicket setCredentialsNonExpired(boolean credentialsNonExpired, boolean... isLockValue) {
        if (lock) {
            log.debug("The Value is Locked!!!");
            return this;
        }
        this.credentialsNonExpired = credentialsNonExpired;
        this.lock = isLockValue.length == 0 ? false : true;
        return this;
    }

    public UserTicket setEnabled(boolean enabled, boolean... isLockValue) {
        if (lock) {
            log.debug("The Value is Locked!!!");
            return this;
        }
        this.enabled = enabled;
        this.lock = isLockValue.length == 0 ? false : true;
        return this;
    }

    public UserTicket addAuthorities(String authorities, boolean... isLockValue) {
        if (lock) {
            log.debug("The Value is Locked!!!");
            return this;
        }
        this.authorities.addAll(AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
        this.lock = isLockValue.length == 0 ? false : true;
        return this;
    }

    public UserTicket addRole(String role, boolean... isLockValue) {
        if (lock) {
            log.debug("The Value is Locked!!!");
            return this;
        }
        this.authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", role)));
        this.lock = isLockValue.length == 0 ? false : true;
        return this;
    }

    public UserTicket addPermission(String permission, boolean... isLockValue) {
        if (lock) {
            log.debug("The Value is Locked!!!");
            return this;
        }
        this.authorities.add(new SimpleGrantedAuthority(permission));
        this.lock = isLockValue.length == 0 ? false : true;
        return this;
    }

    public UserTicket setAuthorities(Collection<GrantedAuthority> authorities, boolean... isLockValue) {
        if (lock) {
            log.debug("The Value is Locked!!!");
            return this;
        }
        this.authorities = authorities;
        return this;
    }

}
