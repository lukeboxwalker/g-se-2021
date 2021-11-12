package de.techfak.gse.lwalkenhorst.model;

public class NoConnectionException extends Exception {

    private static final long serialVersionUID = 42L;

    public NoConnectionException() {
        super();
    }

    public NoConnectionException(final String message) {
        super(message);
    }

    public NoConnectionException(final Throwable cause) {
        super(cause);
    }
}
