package de.techfak.gse.lwalkenhorst.event;

import java.io.Serializable;
import java.util.List;

public class ScoreEvent implements Event, Serializable {

    private final int score;
    private final List<Integer> fullColumns;

    public ScoreEvent(final int score, final List<Integer> fullColumns) {
        this.score = score;
        this.fullColumns = fullColumns;
    }

    public int getScore() {
        return score;
    }

    public List<Integer> getFullColumns() {
        return fullColumns;
    }
}
