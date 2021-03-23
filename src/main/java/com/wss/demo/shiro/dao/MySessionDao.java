package com.wss.demo.shiro.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class MySessionDao extends EnterpriseCacheSessionDAO {

    private RedisTemplate<String,Object> redisTemplate;

    public MySessionDao(RedisTemplate<String,Object> redisTemplate){
        setCacheManager(null);
        this.redisTemplate=redisTemplate;
    }

    protected Session doReadSession(Serializable sessionId){
        System.err.println("读取Session"+sessionId);
        Object object=redisTemplate.opsForHash().get("shiro-session",sessionId.toString());
        return object==null?null:(Session)object;
    }

    protected void doUpdate(Session session){
        System.err.println("更新Session"+session.getId());
        Serializable sessionId=session.getId();
        redisTemplate.opsForHash().put("shiro-session",sessionId.toString(),session);
    }

    protected void doDelete(Session session){
        System.err.println("删除Session"+session.getId());
        Serializable sessionId=session.getId();
        redisTemplate.opsForHash().delete("shiro-session", sessionId.toString());
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    public Collection<Session> getActiveSessions(){
        List values=redisTemplate.opsForHash().values("shiro-session");
        return values;
    }
}
