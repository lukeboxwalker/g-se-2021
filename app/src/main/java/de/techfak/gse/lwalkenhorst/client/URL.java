package de.techfak.gse.lwalkenhorst.client;

import java.net.URI;

public class URL {

    private final URI base;

    public URL(final URI base) {
        this.base = base;
    }

    public URL append(final String uri) {
        return new URL(URI.create(base.toString() + uri));
    }

    public URI toURI() {
        return base;
    }
}
