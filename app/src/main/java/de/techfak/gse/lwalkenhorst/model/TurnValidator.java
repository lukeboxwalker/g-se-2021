package de.techfak.gse.lwalkenhorst.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TurnValidator implements Serializable {

    private final List<TurnValidation> turnValidations = new ArrayList<>();

    private final Board board;

    public TurnValidator(final Board board) {
        this.board = board;
        turnValidations.add(new AllSameColorValidation(board));
        turnValidations.add(new NotCrossedValidation(board));
        turnValidations.add(new CorrectStartCrossValidation(board));
        turnValidations.add(new AllCrossesGroupedValidation());
    }

    public void validateTurn(final Turn turn, final DiceResult diceResult) throws InvalidTurnException {
        final List<TilePosition> tilePositions = turn.getPositionsToCross();
        for (final TilePosition tilePosition : tilePositions) {
            if (!tilePosition.isInside(board.getBounds())) {
                throw new InvalidTurnException("Position: " + tilePosition + "is Not Found on the board");
            }
        }

        for (final TurnValidation turnValidation : turnValidations) {
            turnValidation.validate(tilePositions);
        }
    }
}
