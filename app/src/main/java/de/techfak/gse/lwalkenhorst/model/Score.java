package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;
import java.util.Objects;

public class Score implements Serializable {
    private final int value;

    public Score() {
        this(-1);
    }

    public Score(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Score score = (Score) other;
        return value == score.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
