package de.techfak.gse.lwalkenhorst;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import de.techfak.gse.lwalkenhorst.event.EndGameEvent;
import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.view.GameView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Intent intent = getIntent();
        final Game game = (Game) intent.getSerializableExtra("game");

        //add alert dialog to back press
        addBackPressCallback();

        final GameView view = findViewById(R.id.root);

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //init game view with given display size
        view.init(game, metrics);

        //register event Listeners
        game.event().registerListener(view::updatePoints);
        game.event().registerListener(view::updateRound);
        game.event().registerListener(this::endGame);

        //start game
        game.play();
    }

    private void endGame(EndGameEvent event) {
        finish();
    }

    private void addBackPressCallback() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setMessage(R.string.exit_dialog)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, (dialog, id) -> GameActivity.this.finish())
                        .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel())
                        .create().show();
            }
        });
    }
}