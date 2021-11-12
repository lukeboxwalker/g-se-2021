package de.techfak.gse.lwalkenhorst.model;


import java.util.List;

public class DiceColorValidation implements DiceValidation {

    private final Board board;

    public DiceColorValidation(final Board board) {
        this.board = board;
    }

    @Override
    public void validate(final List<TilePosition> positions, final DiceResult diceResult) throws InvalidTurnException {
        for (final TilePosition position : positions) {
            final TileColor color = board.getTileAt(position).getColor();
            boolean colorMatched = false;
            for (final DiceColorFace colorFace : diceResult.getColors()) {
                if (colorFace.matches(color)) {
                    colorMatched = true;
                }
            }
            if (!colorMatched) {
                throw new InvalidTurnException("No dice is showing the color: " + color);
            }
        }
    }
}
