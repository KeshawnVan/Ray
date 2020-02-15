package com.star.meta;

import com.star.constant.MetaConstants;

import java.util.concurrent.atomic.AtomicReference;

public final class TimeAndSequences {

    private static volatile AtomicReference<TimeStampAndSequence> atomicReference;

    private InitTimeStampFactory initTimeStampFactory;

    public TimeAndSequences(InitTimeStampFactory initTimeStampFactory) {
        this.initTimeStampFactory = initTimeStampFactory;
    }

    public TimeStampAndSequence calculate() {
        initTimeStampAndSequence();
        TimeStampAndSequence timeStampAndSequence = atomicReference.get();
        TimeStampAndSequence newTimeStampAndSequence = buildNewTimeStampAndSequence(timeStampAndSequence);
        return cas(atomicReference, timeStampAndSequence, newTimeStampAndSequence) ? newTimeStampAndSequence : calculate();
    }

    private boolean cas(AtomicReference<TimeStampAndSequence> atomicReference, TimeStampAndSequence oldOne, TimeStampAndSequence newOne) {
        return !oldOne.equals(newOne) && atomicReference.compareAndSet(oldOne, newOne);
    }

    private void initTimeStampAndSequence() {
        if (atomicReference == null) {
            synchronized (TimeAndSequences.class) {
                if (atomicReference == null) {
                    // 远程获取最后的时间戳，从下一位毫秒开始计算sequence
                    long initTimeStamp = initTimeStampFactory.getTimeStamp() + 1;
                    TimeStampAndSequence timeStampAndSequence = new TimeStampAndSequence(initTimeStamp, 0);
                    atomicReference = new AtomicReference<>(timeStampAndSequence);
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

    public static AtomicReference<TimeStampAndSequence> getAtomicReference() {
        return atomicReference;
    }
}
