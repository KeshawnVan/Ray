package com.wekj.ray.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuna
 */
public class ScheduleUtil {

    private static final ScheduledExecutorService EXECUTOR_SERVICE = new ScheduledThreadPoolExecutor(2);

    public static void schedule(Runnable runnable, long delay, long period) {
        EXECUTOR_SERVICE.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
    }

}
