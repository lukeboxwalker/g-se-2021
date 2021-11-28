package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;
import java.util.function.Consumer;

public interface GameStrategy extends Serializable {

    void firstRound(final Consumer<Round> onFinish);

    void nextRound(Round round, Score score, Consumer<Round> onFinish);
}
