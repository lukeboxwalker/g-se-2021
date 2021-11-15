package de.techfak.gse.lwalkenhorst.model;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupValidationTest {

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
    public void testValidGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,D5");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    public void testEmptyGroup() throws InvalidTurnException {
        TurnValidation validation = new AllCrossesGroupedValidation();
        validation.validate(new ArrayList<>());
    }

    @Test
    public void testSinglePositionValidGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("H4");
        game.applyTurn(turn);
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertTrue(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    public void testOnePositionNotInGroup() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,G2");
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }

    @Test
    public void testTwoSeparateGroups() throws InvalidTurnException {
        final Turn turn = turnFactory.parseTurn("F4,E4,E5,I5,J5,K5");
        assertThrows(InvalidTurnException.class, () -> game.applyTurn(turn));
        for (final TilePosition position : turn.getPositionsToCross()) {
            assertFalse(game.getBoard().getTileAt(position).isCrossed());
        }
    }
}
