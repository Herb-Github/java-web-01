package com.zz.startup.security;

import com.zz.startup.security.ShiroDbRealm.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.data.domain.AuditorAware;

public class UserAuditorAware implements AuditorAware<Long> {

    public Long getCurrentAuditor() {
        SecurityManager securityManager = ThreadContext.getSecurityManager();
        if (securityManager == null) {
            return null;
        }

        Subject subject = SecurityUtils.getSubject();
        Object object = subject.getPrincipal();
        if (object == null) {
            return null;
        }

        ShiroUser user = (ShiroUser) object;
        return user.getId();
    }

}
