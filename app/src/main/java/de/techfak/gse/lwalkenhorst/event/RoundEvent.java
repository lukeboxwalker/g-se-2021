package de.techfak.gse.lwalkenhorst.event;

import java.io.Serializable;

public class RoundEvent implements Event, Serializable {

    private final int round;

    public RoundEvent(final int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }
}
