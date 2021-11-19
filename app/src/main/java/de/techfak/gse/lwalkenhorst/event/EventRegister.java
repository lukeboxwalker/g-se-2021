package de.techfak.gse.lwalkenhorst.event;

public interface EventRegister {

    void registerListener(final RoundEventHandler handler);

    void registerListener(final ScoreEventHandler handler);

    void registerListener(final EndGameEventHandler handler);
}
