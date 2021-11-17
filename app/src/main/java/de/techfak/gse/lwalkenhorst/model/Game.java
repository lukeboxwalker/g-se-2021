package de.techfak.gse.lwalkenhorst.model;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

public interface Game extends Serializable {

    void applyTurn(final Turn turn) throws InvalidTurnException;

    RuleManager getRuleManger();

    Score getPoints();

    Board getBoard();

    int getRound();

    void play();

    DiceResult getDiceResult();

    void addListener(final PropertyChange propertyChange, final PropertyChangeListener listener);
}
