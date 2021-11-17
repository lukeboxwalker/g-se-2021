package de.techfak.gse.lwalkenhorst.model;


import static org.junit.Assert.*;

import org.junit.Test;


public class GameFactoryTest {

    private static final int ROWS = 7;
    private static final int COLUMNS = 15;

    @Test
    public void testCorrectBoard() throws InvalidBoardLayoutException, InvalidFieldException {
        final String board = "gggyyyygbbboyyy\nogygyyoorbboogg\nbgrggggrrryyogg\nbrrgoobbggyyorb\nroooorbbooorrrr\nrbbrrrryyorbbbo\nyybbbbryyygggoo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertNotNull(gameFactory.createGame(board));
    }

    @Test
    public void testToManyRows() {
        final String board = "gggyyyygbbboyyy\nogygyyoorbboogg\nbgrggggrrryyogg\nbrrgoobbggyyorb\nroooorbbooorrrr\nrbbrrrryyorbbbo\nyybbbbryyygggoo\nbgrggggrrryyogg";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidBoardLayoutException.class ,() -> gameFactory.createGame(board));
    }

    @Test
    public void testNotEnoughRows() {
        final String board = "gggyyyygbbboyyy\nogygyyoorbboogg\nbgrggggrrryyogg\nbrrgoobbggyyorb\nroooorbbooorrrr\nrbbrrrryyorbbbo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidBoardLayoutException.class ,() -> gameFactory.createGame(board));
    }

    @Test
    public void testToManyCols() {
        final String board = "gggyyyygbbboyyy\nogygyyoorbbooggo\nbgrggggrrryyogg\nbrrgoobbggyyorb\nroooorbbooorrrr\nrbbrrrryyorbbbo\nyybbbbryyygggoo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidBoardLayoutException.class ,() -> gameFactory.createGame(board));
    }

    @Test
    public void testNotEnoughCols() {
        final String board = "gggyyyygbbboyyy\nogygyyoorbboogg\nbgrggggrrryyog\nbrrgoobbggyyorb\nroooorbbooorrrr\nrbbrrrryyorbbbo\nyybbbbryyygggoo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidBoardLayoutException.class ,() -> gameFactory.createGame(board));
    }

    @Test
    public void testWrongColor() {
        final String board = "gggyyyygbbboyyy\nogygyjoorbboogg\nbgrggggrrryyogg\nbrrgoobbggyyorb\nroooorbbooorrrr\nrbbrrrryyorbbbo\nyybbbbryyygggoo";
        final GameFactory gameFactory = new GameFactory(ROWS, COLUMNS, TurnValidator::new);
        assertThrows(InvalidFieldException.class ,() -> gameFactory.createGame(board));
    }

}