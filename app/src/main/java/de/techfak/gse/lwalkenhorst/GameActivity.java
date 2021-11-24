package de.techfak.gse.lwalkenhorst;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import de.techfak.gse.lwalkenhorst.controller.Controller;
import de.techfak.gse.lwalkenhorst.event.EndGameEvent;
import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.view.GameView;

public class GameActivity extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Intent intent = getIntent();
        this.game = (Game) intent.getSerializableExtra("game"); // Model
        final GameView view = findViewById(R.id.root); // View
        final Controller controller = new Controller(game); // Controller

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //init game view with given display size
        view.init(controller, this.game, metrics);

        //register event Listeners
        this.game.event().registerListener(view::updatePoints);
        this.game.event().registerListener(view::updateRound);
        this.game.event().registerListener(this::endGame);

        //start game
        this.game.play();
    }

    private void endGame(EndGameEvent event) {
        finish();
    }

    @Override
    protected void onDestroy() {
        game.event().clear();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage(R.string.exit_dialog)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, (dialog, id) -> GameActivity.super.onBackPressed())
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel())
                .create().show();
    }
}