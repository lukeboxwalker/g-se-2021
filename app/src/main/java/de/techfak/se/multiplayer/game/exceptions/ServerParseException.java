package de.techfak.se.multiplayer.game.exceptions;

public class ServerParseException extends Exception {

    private static final long serialVersionUID = 7250134072307999749L;

    public ServerParseException(final String message) {
        super(message);
    }
}
