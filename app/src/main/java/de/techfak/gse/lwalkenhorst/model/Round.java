package de.techfak.gse.lwalkenhorst.model;

import java.util.Objects;

public class Round {

    private final int current;
    private final DiceResult diceResult;

    public Round(final int current, final DiceResult diceResult) {
        this.current = current;
        this.diceResult = diceResult;
    }


    public int getIntValue() {
        return current;
    }

    public DiceResult getDiceResult() {
        return diceResult;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Round round = (Round) other;
        return current == round.current && Objects.equals(diceResult, round.diceResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(current, diceResult);
    }
}
