package com.zz.startup.security;

import com.google.common.base.Objects;
import com.zz.startup.entity.Authority;
import com.zz.startup.entity.Role;
import com.zz.startup.entity.User;
import com.zz.startup.util.Constants;
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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springside.modules.utils.Encodes;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    protected MongoTemplate mongoTemplate;

    private User findUserByUserName(String userName) {
        return mongoTemplate.findOne(new Query(Criteria.where("userName").is(userName)), User.class);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
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
                throw new RuntimeException("验证码错误");
            }
        }
        User user = findUserByUserName(username);
        if (user != null) {
            byte[] salt = Encodes.decodeHex(user.getSalt());
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),
                    user.getUserName()), user.getPassword(),
                    ByteSource.Util.bytes(salt), getName());
        }

        return null;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        User user = findUserByUserName(shiroUser.userName);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<Role> roles = user.getRoleList();
        if (roles != null) {
            for (Role role : roles) {
                List<Authority> auths = role.getAuthorities();
                if (auths != null) {
                    for (Authority auth : auths) {
                        if (auth != null) {
                            info.addStringPermission(auth.getPermission());
                        }
                    }
                }

                List<String> permissions = role.getPermissions();
                if (permissions != null) {
                    info.addStringPermissions(permissions);
                }
                info.addRole(role.getRoleCode());
            }
        }
        List<String> permissions = user.getPermissions();
        if (permissions != null) {
            info.addStringPermissions(permissions);
        }
        return info;
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
                Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }


    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public String id;
        public String userName;

        public ShiroUser(String id, String loginName) {
            this.id = id;
            this.userName = loginName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * <shiro:principal/>
         */
        @Override
        public String toString() {
            return userName;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(userName);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (userName == null) {
                if (other.userName != null) {
                    return false;
                }
            } else if (!userName.equals(other.userName)) {
                return false;
            }
            return true;
        }
    }
}
