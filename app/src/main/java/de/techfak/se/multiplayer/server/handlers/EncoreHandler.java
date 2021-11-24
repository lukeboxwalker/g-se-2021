package de.techfak.se.multiplayer.server.handlers;

import org.jetbrains.annotations.NotNull;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Handler to response with plain text "Encore".
 */
public class EncoreHandler implements Handler {
    @Override
    public void handle(@NotNull final Context context) {
        context.result("Encore");
    }
}
