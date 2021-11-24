package de.techfak.gse.lwalkenhorst.client;

@FunctionalInterface
public interface ErrorCallback {
    void onError(final RequestError error);
}
