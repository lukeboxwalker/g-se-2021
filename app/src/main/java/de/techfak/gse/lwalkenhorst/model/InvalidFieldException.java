package de.techfak.gse.lwalkenhorst.model;

public class InvalidFieldException extends InvalidBoardException {

    private static final long serialVersionUID = 42L;

    public InvalidFieldException(final String message) {
        super(message);
    }
}
