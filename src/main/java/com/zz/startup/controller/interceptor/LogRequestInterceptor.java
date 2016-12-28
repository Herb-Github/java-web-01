package com.zz.startup.controller.interceptor;

import com.zz.startup.security.ShiroDbRealm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class LogRequestInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LogRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Subject subject = SecurityUtils.getSubject();
        Object object = subject.getPrincipal();
        if (object == null) {
            return true;
        }

        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) object;
        String userName = user.getUsername();
        StringBuilder sb = new StringBuilder(String.format("user: %s, request %s ", userName, request.getRequestURI()));
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = (String) parameterNames.nextElement();
            String[] value = request.getParameterValues(name);
            sb.append(name).append("=").append(StringUtils.join(value, ",")).append("&");
        }
        logger.info(sb.deleteCharAt(sb.length() - 1).toString());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
