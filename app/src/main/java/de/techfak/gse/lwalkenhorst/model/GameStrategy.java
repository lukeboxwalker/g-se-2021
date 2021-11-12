package de.techfak.gse.lwalkenhorst.model;

import java.io.Serializable;

public interface GameStrategy extends Serializable {

    DiceResult start();

    DiceResult rollDice();
}
