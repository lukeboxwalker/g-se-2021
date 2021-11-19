package de.techfak.gse.lwalkenhorst.event;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class EventSupport implements EventRegister, Serializable {

    private final Set<EventHandler<RoundEvent>> roundEventHandlers = new HashSet<>();
    private final Set<EventHandler<ScoreEvent>> scoreEventHandlers = new HashSet<>();
    private final Set<EventHandler<EndGameEvent>> endGameEventHandlers = new HashSet<>();

    public void fireEvent(final RoundEvent event) {
        roundEventHandlers.forEach(handler -> handler.handle(event));
    }

    public void registerListener(final RoundEventHandler handler) {
        roundEventHandlers.add(handler);
    }

    public void fireEvent(final ScoreEvent event) {
        scoreEventHandlers.forEach(handler -> handler.handle(event));
    }

    public void registerListener(final ScoreEventHandler handler) {
        scoreEventHandlers.add(handler);
    }

    public void fireEvent(final EndGameEvent event) {
        endGameEventHandlers.forEach(handler -> handler.handle(event));
    }

    public void registerListener(final EndGameEventHandler handler) {
        endGameEventHandlers.add(handler);
    }
}

