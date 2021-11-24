package de.techfak.se.multiplayer.server;

import java.io.IOException;
import java.util.concurrent.Callable;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.BaseGameImpl;
import de.techfak.se.multiplayer.game.BoardParser;
import de.techfak.se.multiplayer.game.BoardParserImpl;
import de.techfak.se.multiplayer.game.SynchronizedGame;
import de.techfak.se.multiplayer.game.exceptions.InvalidBoardLayoutException;
import de.techfak.se.multiplayer.game.exceptions.InvalidFieldException;

/**
 * Server Launcher.
 */
public class Launcher implements Callable<Integer> {

    private static final int INVALID_FILE_EXIT_CODE = 100;
    private static final int INVALID_BOARD_EXIT_CODE = 101;
    private static final int DEFAULT_PORT = 8080;

    @Override
    public Integer call() {

        try {
            final BoardParser boardParser = new BoardParserImpl();
            final BaseGame baseGame = new BaseGameImpl(boardParser.parse("file"));
            final SynchronizedGame game = new SynchronizedGame(baseGame);
            final Server server = new Server(game);
            server.start(DEFAULT_PORT);

            Thread.currentThread().join();
        } catch (InvalidBoardLayoutException | InvalidFieldException e) {
            return INVALID_BOARD_EXIT_CODE;
        } catch (IOException e) {
            return INVALID_FILE_EXIT_CODE;
        } catch (InterruptedException e) {
            return -1;
        }
        return 0;
    }
}
