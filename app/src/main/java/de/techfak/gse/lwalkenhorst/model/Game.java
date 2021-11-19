package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;

import de.techfak.gse.lwalkenhorst.event.EventRegister;

public interface Game extends Serializable {

    void applyTurn(final Turn turn) throws InvalidTurnException;

    RuleManager getRuleManger();

    Score getPoints();

    Board getBoard();

    int getRound();

    void play();

    DiceResult getDiceResult();

    EventRegister event();
}
