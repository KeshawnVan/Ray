package com.star.meta;

import com.star.constant.MetaConstants;
import com.star.support.redis.Lock;
import com.star.support.redis.RedisConnections;
import com.star.support.redis.RedisLock;
import com.star.util.TimerUtil;

/**
 * @program: Ray
 * @description:
 * @author: liu na
 * @create: 2019-07-21 19:21
 */
public class RedisMachineIdFactory implements MachineIdFactory {

    private static final int SECONDS = 1000;

    private static final String LOCK_MACHINE = "lock:machine:";

    private volatile Long machineId;

    public RedisMachineIdFactory() {
        TimerUtil.schedule(() -> {
            if (machineId != null) {
                RedisConnections.getConnection().sync().expire(LOCK_MACHINE + machineId, 10 * 60);
            }
        }, 0, 5 * 60 * SECONDS);
    }

    @Override
    public long getMachineId() {
        if (machineId == null) {
            synchronized (this) {
                if (machineId == null) {
                    machineId = lockId(System.nanoTime() & MetaConstants.MAX_MACHINE_ID, 0);
                }
            }
        }
        return machineId;
    }

    private long lockId(long id, int tryTime) {
        if (tryTime > MetaConstants.MAX_MACHINE_ID) throw new RuntimeException("cannot get machine id");
        Lock lock = new RedisLock(LOCK_MACHINE + id, 0, 10 * 60 * SECONDS);
        return lock.lock() ? id : lockId((id + 1) & MetaConstants.MAX_MACHINE_ID, tryTime + 1);
    }

}
