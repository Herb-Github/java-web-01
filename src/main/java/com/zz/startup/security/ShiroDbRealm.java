package com.zz.startup.security;

import com.google.common.base.Objects;
import com.zz.startup.entity.Authority;
import com.zz.startup.entity.Role;
import com.zz.startup.entity.User;
import com.zz.startup.util.Constants;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
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

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    protected MongoTemplate mongoTemplate;

    private User findUserByUserName(String userName) {
        return mongoTemplate.findOne(new Query(Criteria.where("userName").is(userName)), User.class);
    }

    /**
     * 认证回调函数,登录时调用.
     */
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
            byte[] salt = new byte[0];
            try {
                salt = Hex.decodeHex(user.getSalt().toCharArray());
            } catch (DecoderException e) {
                e.printStackTrace();
            }
            return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),
                    user.getUserName()), user.getPassword(),
                    ByteSource.Util.bytes(salt), getName());
        }

        return null;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
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

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
                Constants.HASH_ALGORITHM);
        matcher.setHashIterations(Constants.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
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
         * 本函数输出将作为默认的<shiro:principal/>输出.
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
