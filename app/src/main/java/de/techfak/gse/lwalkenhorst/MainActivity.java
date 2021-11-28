package de.techfak.gse.lwalkenhorst;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import de.techfak.gse.lwalkenhorst.model.DiceTurnValidator;
import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.model.GameFactory;
import de.techfak.gse.lwalkenhorst.model.InvalidBoardLayoutException;
import de.techfak.gse.lwalkenhorst.model.InvalidFieldException;
import de.techfak.se.multiplayer.game.Board;
import de.techfak.se.multiplayer.game.BoardParser;
import de.techfak.se.multiplayer.game.BoardParserImpl;
import de.techfak.se.multiplayer.game.exceptions.ServerParseException;

public class MainActivity extends AppCompatActivity {

    private static final int ROWS = 7;
    private static final int COLUMNS = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void start(final View view) {
        final TextInputEditText boardInput = findViewById(R.id.board_select);
        final Editable text = boardInput.getText();

        if (text == null) {
            return;
        }

        try {
            final GameFactory factory = new GameFactory(ROWS, COLUMNS, DiceTurnValidator::new);

            final Game game = factory.createGame(text.toString());
            final Intent intent = new Intent(MainActivity.this, GameActivity.class);
            ((EncoreApp) getApplication()).setGame(game);

            startActivity(intent);
        } catch (InvalidBoardLayoutException | InvalidFieldException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void host(View view) {
        final TextInputEditText boardInput = findViewById(R.id.board_select);
        final Editable text = boardInput.getText();

        if (text == null) {
            return;
        }

        try {
            final BoardParser boardParser = new BoardParserImpl();
            final Board board = boardParser.parse(text.toString());

            final Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
            intent.putExtra("board", board);

            startActivity(intent);
        } catch (ServerParseException | IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void join(View view) {
        final Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
        startActivity(intent);
    }
}