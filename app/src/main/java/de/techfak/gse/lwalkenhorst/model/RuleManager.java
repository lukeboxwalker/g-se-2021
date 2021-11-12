package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;
import java.util.List;

public interface RuleManager extends Serializable {

    List<TileColor> getFullColors();

    List<Integer> getFullColumns();

    int getPointsForCol(final int column);

    boolean isColumnFull(final int column);

    boolean isGameFinished();
}
