package de.techfak.gse.lwalkenhorst.event;

public interface EventHandler<T extends Event> {

    void handle(final T event);
}
