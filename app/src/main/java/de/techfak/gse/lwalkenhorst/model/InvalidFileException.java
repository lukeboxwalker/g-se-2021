package de.techfak.gse.lwalkenhorst.model;

public class InvalidFileException extends InvalidParameterException {

    private static final long serialVersionUID = 42L;

    public InvalidFileException(final String message) {
        super(message);
    }

    public InvalidFileException(final Throwable cause) {
        super(cause);
    }
}
