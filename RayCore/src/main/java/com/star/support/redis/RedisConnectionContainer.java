package com.star.support.redis;

import io.lettuce.core.api.StatefulRedisConnection;

/**
 * @author liuna
 */
public class RedisConnectionContainer {

    private StatefulRedisConnection<String, String> connection;

    public StatefulRedisConnection<String, String> getConnection() {
        return connection;
    }

    public void setConnection(StatefulRedisConnection<String, String> connection) {
        this.connection = connection;
    }
}
