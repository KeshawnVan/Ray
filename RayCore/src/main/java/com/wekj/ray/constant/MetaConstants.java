package com.wekj.ray.constant;

/**
 * @program: Ray
 * @description: 64位ID位数分配
 * @author: liu na
 * @create: 2019-07-25 10:27
 */
public class MetaConstants {
    /**
     * 现在是2020-03-01
     * 描述 : 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    public static final long ORIGIN_TIME_STAMP = 1582992000000L;

    /**
     * 描述 : 机器标识位数
     */
    public static final long MACHINE_ID_BITS = 10L;

    /**
     * 描述 : 机器ID最大值
     */
    public static final long MAX_MACHINE_ID = ~ (-1L << MACHINE_ID_BITS);

    /**
     * 描述 : 毫秒内自增位
     */
    public static final long SEQUENCE_BITS = 12L;

    /**
     * 描述 : 机器ID左移14位
     */
    public static final long MACHINE_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 描述 : 时间毫秒左移22位
     */
    public static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + MACHINE_ID_BITS;

    /**
     * 描述 : 序列号最大值 , 一微秒能产生的ID个数
     */
    public static final long SEQUENCE_MASK = ~ (-1L << SEQUENCE_BITS);
}
