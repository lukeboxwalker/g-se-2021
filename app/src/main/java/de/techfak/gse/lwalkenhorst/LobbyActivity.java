package de.techfak.gse.lwalkenhorst;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.BaseGameImpl;
import de.techfak.se.multiplayer.game.Board;
import de.techfak.se.multiplayer.game.SynchronizedGame;
import de.techfak.se.multiplayer.server.Server;

public class LobbyActivity extends AppCompatActivity {

    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        final Intent intent = getIntent();
        final Board board = (Board) intent.getSerializableExtra("game");
        final BaseGame baseGame = new BaseGameImpl(board);
        final SynchronizedGame game = new SynchronizedGame(baseGame);
        server = new Server(game);
        server.start(8080);
    }

    @Override
    protected void onDestroy() {
        server.stop();
        super.onDestroy();
    }
}