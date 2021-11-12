package de.techfak.gse.lwalkenhorst.model;

public interface TurnValidatorFactory {
    TurnValidator create(final Board board);
}
