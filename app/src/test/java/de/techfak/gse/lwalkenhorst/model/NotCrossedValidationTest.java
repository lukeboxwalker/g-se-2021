package de.techfak.gse.lwalkenhorst.model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class NotCrossedValidationTest {

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
    public void testAllNotCrossed() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H6,I6,I7");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    public void testTilesAlreadyCrossed() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("D3,D4,E3");
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        assertFalse(game.getBoard().getTileAt(3, 3).isCrossed());
    }
}
