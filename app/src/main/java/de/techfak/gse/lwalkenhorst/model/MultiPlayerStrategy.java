package de.techfak.gse.lwalkenhorst.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import de.techfak.gse.lwalkenhorst.client.Client;
import de.techfak.se.multiplayer.server.response_body.DiceResponse;
import de.techfak.se.multiplayer.server.response_body.RoundResponse;

public class MultiPlayerStrategy implements GameStrategy {

    private final Timer timer = new Timer();
    private final static ObjectMapper JSON_PARSER = new ObjectMapper();
    private final Client client;
    private final String name;
    private final AtomicBoolean waiting = new AtomicBoolean(false);

    public MultiPlayerStrategy(final Client client, final String name) {
        this.client = client;
        this.name = name;
    }

    @Override
    public void firstRound(final Consumer<Round> onFinish) {
        this.client.getDiceResult(name, response -> handleDiceResult(response, onFinish, 1));
    }

    @Override
    public void nextRound(final Round round, final Score score, final Consumer<Round> onFinish) {
        if (waiting.get()) {
            return;
        }
        waiting.set(true);
        client.endRound(name, score.getValue());
        final TimerTask task = new TimerTask() {
            public void run() {
                client.getRound(name, r -> handlePlayerResponse(r, onFinish, round.getIntValue()));
            }

            private void handlePlayerResponse(final String response, final Consumer<Round> onFinish, final int round) {
                try {
                    final RoundResponse roundResponse = JSON_PARSER.readValue(response, RoundResponse.class);
                    if (roundResponse.getRound() == round) {
                        return;
                    }
                    cancel();
                    waiting.set(false);
                    client.getDiceResult(name, r -> handleDiceResult(r, onFinish, round + 1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 0, 1000L);
    }

    private void handleDiceResult(final String response, final Consumer<Round> onFinish, final int round) {
        try {
            final DiceResponse diceResponse = JSON_PARSER.readValue(response, DiceResponse.class);
            final List<DiceColorFace> colors = diceResponse.getColors()
                    .stream().map(DiceColorFace::convert).collect(Collectors.toList());
            final List<DiceNumberFace> numbers = diceResponse.getNumbers()
                    .stream().map(DiceNumberFace::convert).collect(Collectors.toList());
            onFinish.accept(new Round(round, new DiceResult(numbers, colors)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
