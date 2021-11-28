package de.techfak.gse.lwalkenhorst.model;

import de.techfak.se.multiplayer.game.Number;

public enum DiceNumberFace {
    ONE() {
        @Override
        public boolean matches(final int number) {
            return number == 1;
        }
    },
    TWO() {
        @Override
        public boolean matches(final int number) {
            return number == 2;
        }
    },
    THREE() {
        @Override
        public boolean matches(final int number) {
            return number == 3;
        }
    },
    FOUR() {
        @Override
        public boolean matches(final int number) {
            return number == 4;
        }
    },
    FIVE() {
        @Override
        public boolean matches(final int number) {
            return number == 5;
        }
    },
    JOKER() {
        @Override
        public boolean matches(final int number) {
            return number > 0 && number < 5;
        }
    };

    public abstract boolean matches(final int number);

    public static DiceNumberFace convert(final Number color) {
        switch (color) {
            case ONE: return DiceNumberFace.ONE;
            case TWO: return DiceNumberFace.TWO;
            case THREE: return DiceNumberFace.THREE;
            case FOUR: return DiceNumberFace.FOUR;
            case FIVE: return DiceNumberFace.FIVE;
            default: return DiceNumberFace.JOKER;
        }
    }
}
