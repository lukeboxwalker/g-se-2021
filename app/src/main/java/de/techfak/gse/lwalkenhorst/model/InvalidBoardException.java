package de.techfak.gse.lwalkenhorst.model;

public class InvalidBoardException extends Exception {

    private static final long serialVersionUID = 42L;

    public InvalidBoardException(final String message) {
        super(message);
    }
}
