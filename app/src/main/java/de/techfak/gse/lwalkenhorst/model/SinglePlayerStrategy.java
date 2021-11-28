package de.techfak.gse.lwalkenhorst.model;

import java.util.function.Consumer;

public class SinglePlayerStrategy implements GameStrategy {

    private final Dice dice = new Dice();

    @Override
    public void firstRound(final Consumer<Round> onFinish) {
        onFinish.accept(new Round(1, dice.roll()));
    }

    @Override
    public void nextRound(final Round round, final Score score, final Consumer<Round> onFinish) {
        onFinish.accept(new Round(round.getIntValue() + 1, dice.roll()));
    }
}
