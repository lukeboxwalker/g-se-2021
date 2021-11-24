package de.techfak.se.multiplayer.game;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.techfak.se.multiplayer.game.exceptions.InvalidBoardLayoutException;
import de.techfak.se.multiplayer.game.exceptions.InvalidFieldException;

/**
 * The board parser implementation, which loads board configurations from files or strings.
 */
public class BoardParserImpl implements BoardParser {

    private static final int LENGTH_X = 15;
    private static final int LENGTH_Y = 7;
    private static final Set<Character> COLOR_CHARS = new HashSet<>(Arrays.asList('r', 'g', 'b', 'y', 'o'));

    /**
     * Initializes a new board.
     *
     * @param board the board String that has the information about the board.
     * @return the parsed board
     * @throws InvalidBoardLayoutException if the field is larger or smaller than the expected 15x7 tiles
     * @throws InvalidFieldException       if the field contains invalid tile colors
     * @throws IOException                 if there is an issue with the file
     */
    @Override
    public Board parse(final String board) throws InvalidBoardLayoutException, InvalidFieldException, IOException {
        return parse(board.split("\n"));
    }

    private Board parse(final String[] lines) throws InvalidBoardLayoutException, InvalidFieldException {
        if (lines.length != LENGTH_Y) {
            throw new InvalidBoardLayoutException("Wrong board size. Y value is not equal to " + LENGTH_Y);
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String line : lines) {
            stringBuilder.append(parseLine(line)).append('\n');
        }
        return new Board(stringBuilder.toString());
    }

    private String parseLine(final String line) throws InvalidBoardLayoutException, InvalidFieldException {
        if (line.length() != LENGTH_X) {
            throw new InvalidBoardLayoutException("Wrong board size. X value is not equal to " + LENGTH_X);
        }
        for (final char character : line.toCharArray()) {
            if (!COLOR_CHARS.contains(Character.toLowerCase(character))) {
                throw new InvalidFieldException("Char not found " + character);
            }
        }
        return line;
    }
}
