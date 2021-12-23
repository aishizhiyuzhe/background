package com.ming.common.security.session;

import com.ming.common.utils.JedisUtils;
import com.ming.common.utils.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.*;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/12/20
 * @Modified By:
 */
public class JedisSessionDao extends AbstractSessionDAO {
    private String sessionKeyPrefix = "shiro_session_";
    //这一步是为创建一个session的主键
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId=this.generateSessionId(session);
        this.assignSessionId(session,sessionId);
        this.update(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Jedis jedis=JedisUtils.getResource();
        Session session= (Session) ObjectUtils.unserialize(jedis.get(JedisUtils.getBytesKey(JedisUtils.KEY_PREFIX+"_"+sessionId)));
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        // 获取登录者编号
        PrincipalCollection pc = (PrincipalCollection)session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        String principalId = pc != null ? pc.getPrimaryPrincipal().toString() :"";
        Jedis jedis=JedisUtils.getResource();
        jedis.hset(sessionKeyPrefix,session.getId().toString(),principalId+"|"+session.getTimeout()+"|"+session.getLastAccessTime().getTime());
        jedis.set(JedisUtils.getBytesKey(JedisUtils.KEY_PREFIX+"_"+session.getId()), ObjectUtils.serialize(session));
        jedis.expire(JedisUtils.getBytesKey(JedisUtils.KEY_PREFIX+"_"+session),session.getTimeout()/1000);
        JedisUtils.clone(jedis);

    }

    @Override
    public void delete(Session session) {

        Jedis jedis=JedisUtils.getResource();
        jedis.hdel(sessionKeyPrefix,session.getId().toString());
        jedis.del(JedisUtils.getBytesKey(JedisUtils.KEY_PREFIX+"_"+session));
        JedisUtils.clone(jedis);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Jedis jedis=JedisUtils.getResource();
        Map<String, String> map = jedis.hgetAll(sessionKeyPrefix);
        Set set=new HashSet();
        for (String key: map.keySet()){
            SimpleSession session=new SimpleSession();
            session.setId(key);
            String ss[]=map.get(key).split("\\|");
            session.setTimeout(Long.parseLong(ss[1]));
            session.setAttribute("principalId", ss[0]);
            session.setLastAccessTime(new Date(Long.parseLong(ss[2])));
            set.add(session);
        }
        return set;
    }
}
