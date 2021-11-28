package de.techfak.gse.lwalkenhorst;

import android.app.Application;

import de.techfak.gse.lwalkenhorst.model.Game;

public class EncoreApp extends Application {

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private Game game;


}
