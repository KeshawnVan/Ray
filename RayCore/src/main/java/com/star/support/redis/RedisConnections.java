package com.star.support.redis;

import com.star.spring.SpringContextHolder;
import io.lettuce.core.api.StatefulRedisConnection;

/**
 * @author liuna
 */
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
        if (redisConnectionContainer == null){
            throw new RuntimeException("redis not init");
        }
        return redisConnectionContainer.getConnection();
    }

}
