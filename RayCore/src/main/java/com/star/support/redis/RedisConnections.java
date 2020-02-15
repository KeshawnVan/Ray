package com.star.support.redis;

import com.star.spring.SpringContextHolder;
import io.lettuce.core.api.StatefulRedisConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisConnections {

    private volatile static RedisConnectionContainer redisConnectionContainer;

    public static StatefulRedisConnection<String, String> getConnection() {
        if (redisConnectionContainer == null) {
            synchronized (RedisConnections.class) {
                if (redisConnectionContainer == null) {
                    redisConnectionContainer = SpringContextHolder.getBean(RedisConnectionContainer.class);
                }
            }
        }
        return redisConnectionContainer == null ? null : redisConnectionContainer.getConnection();
    }

}
