package com.star.meta;

import com.star.constant.MetaConstants;

import javax.inject.Inject;
import java.util.concurrent.atomic.AtomicReference;

public final class TimeAndSequences {

    private static volatile AtomicReference<TimeStampAndSequence>[] atomicReferences = new AtomicReference[4];

    @Inject
    private InitTimeStampFactory initTimeStampFactory;

    public TimeStampAndSequence calculate(int index) {
        initTimeStampAndSequence(index);
        AtomicReference<TimeStampAndSequence> atomicReference = atomicReferences[index];
        TimeStampAndSequence timeStampAndSequence = atomicReference.get();
        TimeStampAndSequence newTimeStampAndSequence = buildNewTimeStampAndSequence(timeStampAndSequence);
        return atomicReference.compareAndSet(timeStampAndSequence, newTimeStampAndSequence) ? newTimeStampAndSequence : calculate(index);
    }

    private void initTimeStampAndSequence(int index) {
        if (atomicReferences[index] == null) {
            synchronized (this) {
                if (atomicReferences[index] == null) {
                    // 远程获取最后的时间戳，从下一位毫秒开始计算sequence
                    long initTimeStamp = initTimeStampFactory.getTimeStamp(index) + 1;
                    TimeStampAndSequence timeStampAndSequence = new TimeStampAndSequence(initTimeStamp, 0);
                    AtomicReference<TimeStampAndSequence> reference = new AtomicReference<>(timeStampAndSequence);
                    atomicReferences[index] = reference;
                }
            }
        }
    }

    private TimeStampAndSequence buildNewTimeStampAndSequence(TimeStampAndSequence timeStampAndSequence) {
        long lastTimestamp = timeStampAndSequence.getTimestamp();
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis == lastTimestamp) {
            return buildAppendTimeStampAndSequence(currentTimeMillis, timeStampAndSequence);
        } else if (currentTimeMillis > lastTimestamp) {
            return new TimeStampAndSequence(currentTimeMillis, 0);
        } else {
            return buildAppendTimeStampAndSequence(lastTimestamp, timeStampAndSequence);
        }
    }

    private TimeStampAndSequence buildAppendTimeStampAndSequence(long timeStamp, TimeStampAndSequence timeStampAndSequence) {
        long currentSequence = timeStampAndSequence.getSequence() + 1;
        return currentSequence > MetaConstants.SEQUENCE_MASK
                ? new TimeStampAndSequence(timeStamp + 1, 0)
                : new TimeStampAndSequence(timeStamp, currentSequence);
    }
}
