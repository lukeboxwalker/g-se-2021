package de.techfak.gse.lwalkenhorst.model;

import java.util.List;

public class CorrectStartCrossValidation implements TurnValidation {

    private final Board board;

    public CorrectStartCrossValidation(final Board board) {
        this.board = board;
    }

    private boolean isNeighborCrossed(final TilePosition tilePosition) {
        return tilePosition.isInside(board.getBounds()) && board.getTileAt(tilePosition).isCrossed();
    }

    @Override
    public void validate(final List<TilePosition> tilePositions) throws InvalidTurnException {
        if (tilePositions.isEmpty()) {
            return;
        }
        boolean hasCrossedNeighbor = false;
        for (TilePosition tilePosition : tilePositions) {
            if (tilePosition.getColumn() == board.getStartColumn()) {
                return;
            }
            hasCrossedNeighbor |= isNeighborCrossed(tilePosition.left());
            hasCrossedNeighbor |= isNeighborCrossed(tilePosition.right());
            hasCrossedNeighbor |= isNeighborCrossed(tilePosition.above());
            hasCrossedNeighbor |= isNeighborCrossed(tilePosition.down());
        }

        if (!hasCrossedNeighbor) {
            throw new InvalidTurnException("No Tile is in column 7 or next to a crossed tile");
        }
    }
}
