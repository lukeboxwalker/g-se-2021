package de.techfak.gse.lwalkenhorst;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.BaseGameImpl;
import de.techfak.se.multiplayer.game.Board;
import de.techfak.se.multiplayer.game.SynchronizedGame;
import de.techfak.se.multiplayer.server.Server;

public class ConnectActivity extends AppCompatActivity {

    private Server server;
    public boolean host = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        final Intent intent = getIntent();
        if (intent.hasExtra("board")) {
            findViewById(R.id.addressInput).setVisibility(View.INVISIBLE);
            this.host = true;
            startServer(intent);
        }
    }

    public void start(View view) {
        final TextInputEditText ip_input = findViewById(R.id.ip_select);
        final Editable ip_text = ip_input.getText();
        final TextInputEditText name_select = findViewById(R.id.name_select);
        final Editable name_text = name_select.getText();

        if (name_text == null) {
            return;
        }

        final String playerName = name_text.toString();
        final String serverIp;
        if (host) {
            serverIp = "127.0.0.1:8080";
        } else if (ip_text == null) {
            return;
        } else {
            serverIp = ip_text.toString();
        }

        

    }

    private void startServer(final Intent intent) {
        final Board board = (Board) intent.getSerializableExtra("board");
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