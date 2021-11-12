package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;
import java.util.List;

public interface TurnValidation extends Serializable {

    void validate(final List<TilePosition> tilePositions) throws InvalidTurnException;
}
