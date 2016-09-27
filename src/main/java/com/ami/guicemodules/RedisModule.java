package com.ami.guicemodules;

import com.ami.service.user.SessionService;
import com.ami.service.user.SessionServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author: Amit Khandelwal
 * Date: 24/09/16
 */

public class RedisModule  extends AbstractModule{


    @Override
    protected void configure() {
       bind(SessionService.class).to(SessionServiceImpl.class).in(Singleton.class);
    }
}
