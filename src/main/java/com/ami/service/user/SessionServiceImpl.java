package com.ami.service.user;

import com.ami.entities.Session;
import com.google.inject.Inject;
import redis.clients.jedis.Jedis;

import javax.ws.rs.core.Context;

/**
 * @author: Amit Khandelwal
 * Date: 24/09/16
 */

public class SessionServiceImpl implements SessionService {

    @Inject
    private Jedis jedis;

    @Override
    public String save(Session session) {
        String res= jedis.set(session.getKey(),session.getValue());
        return  res;

    }

    @Override
    public String get(String key) {
       return jedis.get(key);
    }

    @Override
    public boolean invalidate(String key) {
        return false;
    }
}
