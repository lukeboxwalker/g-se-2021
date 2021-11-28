package de.techfak.gse.lwalkenhorst.model;

import de.techfak.se.multiplayer.game.Color;

public enum DiceColorFace {
    GREEN() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.GREEN;
        }
    },
    YELLOW() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.YELLOW;
        }
    },
    RED() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.RED;
        }
    },
    BLUE() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.BLUE;
        }
    },
    ORANGE() {
        @Override
        public boolean matches(final TileColor color) {
            return color == TileColor.ORANGE;
        }
    },
    JOKER() {
        @Override
        public boolean matches(final TileColor color) {
            return true;
        }
    };

    public abstract boolean matches(final TileColor color);

    public static DiceColorFace convert(final Color color) {
        switch (color) {
            case GREEN: return DiceColorFace.GREEN;
            case YELLOW: return DiceColorFace.YELLOW;
            case RED: return DiceColorFace.RED;
            case BLUE: return DiceColorFace.BLUE;
            case ORANGE: return DiceColorFace.ORANGE;
            default: return DiceColorFace.JOKER;
        }
    }
}
