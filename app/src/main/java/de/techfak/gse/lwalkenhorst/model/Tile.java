package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;

public interface Tile extends Serializable {

    TileColor getColor();

    boolean isCrossed();
}
