package de.techfak.gse.lwalkenhorst.controller;

import java.util.List;

import de.techfak.gse.lwalkenhorst.model.DiceResult;
import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.model.InvalidTurnException;
import de.techfak.gse.lwalkenhorst.model.TilePosition;
import de.techfak.gse.lwalkenhorst.model.Turn;
import de.techfak.gse.lwalkenhorst.model.TurnFactory;

public class Controller {

    private final TurnFactory turnFactory = new TurnFactory();
    private final Game game;

    public Controller(final Game game) {
        this.game = game;
    }

    public void crossTiles(final List<TilePosition> positions) throws InvalidTurnException {
        final Turn turn = turnFactory.createTurn(positions);
        this.game.applyTurn(turn);
    }

    public boolean checkTileCrossed(final TilePosition position) {
        return this.game.getBoard().getTileAt(position).isCrossed();
    }

    public DiceResult getDiceRolled() {
        return this.game.getDiceResult();
    }
}
