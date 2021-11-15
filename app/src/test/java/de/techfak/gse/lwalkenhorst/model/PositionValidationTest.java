package de.techfak.gse.lwalkenhorst.model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import java.util.Arrays;

import java.util.Collections;
import java.util.List;

public class PositionValidationTest {

    private static final List<String> TEST_BOARD = Arrays.asList(
        "gggyyyygbbboyYy",
        "OgygyyooRbbooGG",
        "BGRGGGGRRRYYOGG",
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
    public void testPositionsInBounds() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H6,I6,I7");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    public void testPositionXToLarge() {
        final Turn turn = turnFactory.createTurn(Collections.singletonList(new TilePosition(1, 15)));
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
    }

    @Test
    public void testPositionXToLow() {
        final Turn turn = turnFactory.createTurn(Collections.singletonList(new TilePosition(1, -1)));
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
    }

    @Test
    public void testPositionYToLarge() {
        final Turn turn = turnFactory.createTurn(Collections.singletonList(new TilePosition(7, 7)));
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
    }

    @Test
    public void testPositionYToLow() {
        final Turn turn = turnFactory.createTurn(Collections.singletonList(new TilePosition(-1, 7)));
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
    }
}
