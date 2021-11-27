package de.techfak.gse.lwalkenhorst.model;

import de.techfak.gse.lwalkenhorst.client.Client;

public class MultiPlayerStrategy implements GameStrategy {

    private final Client client;

    public MultiPlayerStrategy(final Client client) {
        this.client = client;
    }

    @Override
    public DiceResult start() {
        return null;
    }

    @Override
    public DiceResult rollDice() {
        return null;
    }
}
