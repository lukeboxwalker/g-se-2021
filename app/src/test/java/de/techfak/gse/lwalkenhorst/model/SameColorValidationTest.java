package de.techfak.gse.lwalkenhorst.model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class SameColorValidationTest {

    private static final List<String> TEST_BOARD = Arrays.asList(
            "gggyyyygbbboyyy",
            "ogygyyooRbboogg",
            "bgrGGGGRRRYyogg",
            "brrgoobbggYyorb",
            "roooorbbooorrrr",
            "rbbrrrryyorbbbo",
            "yybbbbryyygggoo");

    private final TurnFactory turnFactory = new TurnFactory();
    private Game game;

    @Before
    public void setUp() throws InvalidBoardLayoutException, InvalidFieldException {
        final GameFactory factory = new GameFactory(7, 15, TurnValidator::new);
        game = factory.createGame(TEST_BOARD);
        game.play();
    }

    @Test
    public void testAllSameColor() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H4,H5,G5,G4");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    public void testNotAllSameColor() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F1,F2,G1,G2");
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }

}
