package com.zz.startup.security;

import com.zz.startup.entity.Authority;
import com.zz.startup.entity.Role;
import com.zz.startup.entity.User;
import com.zz.startup.exception.BaseRuntimeException;
import com.zz.startup.service.UserService;
import com.zz.startup.util.Constants;
import com.zz.startup.util.Encodes;
import com.zz.startup.util.SearchFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    protected UserService userService;

    private User findByUsername(String username) {
        return userService.findOne("username", SearchFilter.Operator.EQ, username);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String username = "";
        if (authcToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken upt = (UsernamePasswordToken) authcToken;
            username = upt.getUsername();
        }

        if (authcToken instanceof UsernamePasswordCaptchaToken) {
            UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
            username = token.getUsername();
            String captcha = token.getCaptcha();
            Session s = SecurityUtils.getSubject().getSession();
            String exitCode = (String) s.getAttribute("code");
            if (null == captcha || !StringUtils.equalsIgnoreCase(captcha, exitCode)) {
                throw new BaseRuntimeException("验证码错误");
            }
        }

        User user = findByUsername(username);
        if (user != null) {
            byte[] salt = Encodes.decodeHex(user.getSalt());
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),
                    user.getUsername()), user.getPassword(),
                    ByteSource.Util.bytes(salt), getName());
        }

        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        User user = findByUsername(shiroUser.getUsername());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<Role> roles = user.getRoles();
        if (roles != null) {
            for (Role role : roles) {
                List<Authority> auths = role.getAuthorities();
                if (auths != null) {
                    for (Authority auth : auths) {
                        info.addStringPermission(auth.getPermission());
                    }
                }
                info.addRole(role.getName());
            }
        }

        return info;
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        private Long id;
        private String username;

        public ShiroUser(Long id, String username) {
            this.id = id;
            this.username = username;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * <shiro:principal/>
         */
        @Override
        public String toString() {
            return username;
        }

    }
}
