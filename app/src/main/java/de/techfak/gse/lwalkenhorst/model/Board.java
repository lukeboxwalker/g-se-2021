package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;

public interface Board extends Iterable<Tile>, Serializable {
    Bounds getBounds();

    Tile getTileAt(final TilePosition tilePosition);

    Tile getTileAt(final int row, final int col);

    int getStartColumn();
}
