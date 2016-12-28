package com.zz.startup.controller.interceptor;

import com.zz.startup.security.ShiroDbRealm;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class StopWatchInterceptor extends HandlerInterceptorAdapter {

    private static final String TRACK_ID = "trackId";

    private static Logger logger = LoggerFactory.getLogger(StopWatchInterceptor.class);
    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("StopWatch-StartTime");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);

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
        String track = RandomStringUtils.randomAlphanumeric(8);
        MDC.put(TRACK_ID, track);

        logger.info(sb.deleteCharAt(sb.length() - 1).toString());


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {
        long endTime = System.currentTimeMillis();
        long beginTime = startTimeThreadLocal.get();
        long consumeTime = endTime - beginTime;
        logger.debug("method:{}, path:{}: consume {}ms", request.getMethod(), request.getRequestURI(), consumeTime);

        MDC.remove(TRACK_ID);
    }
}
