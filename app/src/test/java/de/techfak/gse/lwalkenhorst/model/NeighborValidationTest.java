package de.techfak.gse.lwalkenhorst.model;


import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class NeighborValidationTest {

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
    public void testStartInColH() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H6,I6,I7");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    public void testStartNextToCrossedTile() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("K2,K1");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    public void testInvalidFirstCross() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("B5,C5,D5,E5");
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }
}
