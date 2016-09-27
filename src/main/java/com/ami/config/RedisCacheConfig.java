package com.ami.config;

import com.bendb.dropwizard.redis.JedisFactory;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * @author: Amit Khandelwal
 * Date: 25/09/16
 */

public class RedisCacheConfig {

    @NotNull
    @JsonProperty
    private JedisFactory redis;

    public JedisFactory getJedisFactory() {
        return redis;
    }

    public void setJedisFactory(JedisFactory jedisFactory) {
        this.redis = jedisFactory;
    }

}
