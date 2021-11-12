package de.techfak.gse.lwalkenhorst.model;

public class InvalidParameterException extends Exception {

    private static final long serialVersionUID = 42L;

    public InvalidParameterException(final String message) {
        super(message);
    }

    public InvalidParameterException(final Throwable cause) {
        super(cause);
    }
}

