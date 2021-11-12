package de.techfak.gse.lwalkenhorst.model;

public class SinglePlayerStrategy implements GameStrategy {

    private final Dice dice = new Dice();

    @Override
    public DiceResult start() {
        return this.rollDice();
    }

    @Override
    public DiceResult rollDice() {
        return dice.roll();
    }
}
