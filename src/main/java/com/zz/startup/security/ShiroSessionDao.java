package com.zz.startup.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;

public class ShiroSessionDao extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(ShiroSessionDao.class);

    @Autowired
    private JedisShiroSessionRepository jedisShiroSessionRepository;

    public void update(Session session) throws UnknownSessionException {
        jedisShiroSessionRepository.saveSession(session);
    }

    public void delete(Session session) {
        if (session == null) {
            logger.error("session can not be null,delete failed");
            return;
        }

        Serializable id = session.getId();
        if (id != null) {
            jedisShiroSessionRepository.deleteSession(id);
        }
    }

    public Collection<Session> getActiveSessions() {
        return jedisShiroSessionRepository.getAllSessions();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        jedisShiroSessionRepository.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return jedisShiroSessionRepository.getSession(sessionId);
    }

}
