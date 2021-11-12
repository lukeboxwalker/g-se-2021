package de.techfak.gse.lwalkenhorst.model;

public class InvalidTurnException extends Exception {
    private static final long serialVersionUID = 42L;

    public InvalidTurnException(final String message) {
        super(message);
    }

}
