package com.star.generator;

import com.star.constant.MetaConstants;
import com.star.meta.MachineIdFactory;
import com.star.meta.TimeAndSequences;
import com.star.meta.TimeStampAndSequence;

import static com.star.constant.MetaConstants.MACHINE_ID_SHIFT;
import static com.star.constant.MetaConstants.TIMESTAMP_LEFT_SHIFT;

/**
 * @program: Ray
 * @description:
 * @author: liu na
 * @create: 2019-07-21 18:56
 */
public final class IdGenerator {

    private MachineIdFactory machineIdFactory;

    private TimeAndSequences timeAndSequences;

    public IdGenerator(MachineIdFactory machineIdFactory, TimeAndSequences timeAndSequences) {
        this.machineIdFactory = machineIdFactory;
        this.timeAndSequences = timeAndSequences;
    }

    public long getId() {

        // 获取机器标识
        long machineId = machineIdFactory.getMachineId();

        // 计算时间戳和序列
        TimeStampAndSequence timeStampAndSequence = timeAndSequences.calculate();

        // 根据元数据生成ID
        return ((timeStampAndSequence.getTimestamp() - MetaConstants.ORIGIN_TIME_STAMP) << TIMESTAMP_LEFT_SHIFT)
                | (machineId << MACHINE_ID_SHIFT)
                | timeStampAndSequence.getSequence();
    }

}
