package de.techfak.gse.lwalkenhorst;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import de.techfak.gse.lwalkenhorst.controller.Controller;
import de.techfak.gse.lwalkenhorst.event.EndGameEvent;
import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.view.GameView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Game game = ((EncoreApp) getApplication()).getGame(); // Model
        final GameView view = findViewById(R.id.root); // View
        final Controller controller = new Controller(game); // Controller

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //init game view with given display size
        view.init(controller, game, metrics);

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

    @Override
    protected void onDestroy() {
        ((EncoreApp) getApplication()).getGame().event().clear();
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