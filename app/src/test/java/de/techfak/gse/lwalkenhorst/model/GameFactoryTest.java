package de.techfak.gse.lwalkenhorst.model;


import static org.junit.Assert.*;

import org.junit.Test;


public class GameFactoryTest {

    private static final int ROWS = 7;
    private static final int COLUMNS = 15;

    @Test
    public void testCorrectBoard() throws InvalidBoardLayoutException, InvalidFieldException {
        final String board = "gggyyyygbbboyyy ogygyyoorbboogg bgrggggrrryyogg brrgoobbggyyorb roooorbbooorrrr rbbrrrryyorbbbo yybbbbryyygggoo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertNotNull(gameFactory.createGame(board));
    }

    @Test
    public void testToManyRows() {
        final String board = "gggyyyygbbboyyy ogygyyoorbboogg bgrggggrrryyogg brrgoobbggyyorb roooorbbooorrrr rbbrrrryyorbbbo yybbbbryyygggoo bgrggggrrryyogg";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidBoardLayoutException.class ,() -> gameFactory.createGame(board));
    }

    @Test
    public void testNotEnoughRows() {
        final String board = "gggyyyygbbboyyy ogygyyoorbboogg bgrggggrrryyogg brrgoobbggyyorb roooorbbooorrrr rbbrrrryyorbbbo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidBoardLayoutException.class ,() -> gameFactory.createGame(board));
    }

    @Test
    public void testToManyCols() {
        final String board = "gggyyyygbbboyyy ogygyyoorbbooggo bgrggggrrryyogg brrgoobbggyyorb roooorbbooorrrr rbbrrrryyorbbbo yybbbbryyygggoo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidBoardLayoutException.class ,() -> gameFactory.createGame(board));
    }

    @Test
    public void testNotEnoughCols() {
        final String board = "gggyyyygbbboyyy ogygyyoorbboogg bgrggggrrryyog brrgoobbggyyorb roooorbbooorrrr rbbrrrryyorbbbo yybbbbryyygggoo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidBoardLayoutException.class ,() -> gameFactory.createGame(board));
    }

    @Test
    public void testWrongColor() {
        final String board = "gggyyyygbbboyyy ogygyjoorbboogg bgrggggrrryyogg brrgoobbggyyorb roooorbbooorrrr rbbrrrryyorbbbo yybbbbryyygggoo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidFieldException.class ,() -> gameFactory.createGame(board));
    }

}