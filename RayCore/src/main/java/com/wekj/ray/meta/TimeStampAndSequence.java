package com.wekj.ray.meta;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author liuna
 */
public class TimeStampAndSequence {

    private long timestamp;

    private long sequence;

    public TimeStampAndSequence(long timestamp, long sequence) {
        this.timestamp = timestamp;
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TimeStampAndSequence.class.getSimpleName() + "[", "]")
                .add("timestamp=" + timestamp)
                .add("sequence=" + sequence)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeStampAndSequence that = (TimeStampAndSequence) o;
        return timestamp == that.timestamp &&
                sequence == that.sequence;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, sequence);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getSequence() {
        return sequence;
    }

}
