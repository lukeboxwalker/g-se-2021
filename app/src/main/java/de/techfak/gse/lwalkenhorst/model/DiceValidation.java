package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;
import java.util.List;

public interface DiceValidation extends Serializable {

    void validate(final List<TilePosition> positions, final DiceResult diceResult) throws InvalidTurnException;
}
