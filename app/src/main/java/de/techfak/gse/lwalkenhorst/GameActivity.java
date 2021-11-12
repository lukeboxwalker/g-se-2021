package de.techfak.gse.lwalkenhorst;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.model.InvalidTurnException;
import de.techfak.gse.lwalkenhorst.model.PropertyChange;
import de.techfak.gse.lwalkenhorst.model.TilePosition;
import de.techfak.gse.lwalkenhorst.model.Turn;
import de.techfak.gse.lwalkenhorst.model.TurnFactory;
import de.techfak.gse.lwalkenhorst.view.BoardView;
import de.techfak.gse.lwalkenhorst.view.DiceView;
import de.techfak.gse.lwalkenhorst.view.TileDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private final List<TileDisplay> clickedTiles = new ArrayList<>();
    private final TurnFactory turnFactory = new TurnFactory();

    private BoardView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Intent intent = getIntent();

        game = (Game) intent.getSerializableExtra("game");

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        view = findViewById(R.id.boardViewLayout);
        view.init(game.getBoard(), game.getRuleManger(), metrics);

        view.registerClickHandler(this::clickTile);
        final DiceView diceView = findViewById(R.id.diceViewLayout);
        diceView.init(metrics);

        game.addListener(PropertyChange.SCORE, event -> updatePoints());
        game.addListener(PropertyChange.ROUND, event -> diceView.updateDice(game.getDiceResult()));
        game.addListener(PropertyChange.FINISHED, event -> finish());

        game.play();

        final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setMessage(R.string.exit_dialog)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, (dialog, id) -> GameActivity.this.finish())
                        .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
                builder.create().show();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @SuppressLint("SetTextI18n")
    private void updatePoints() {
        ((TextView) findViewById(R.id.points)).setText("Points: " + game.getPoints().toString());
        for (final int column : game.getRuleManger().getFullColumns()) {
            view.markColumnAsFull(column);
        }
    }

    private void clickTile(final TileDisplay tileDisplay) {
        if (!game.getBoard().getTileAt(tileDisplay.getPosition()).isCrossed()) {
            clickedTiles.add(tileDisplay);
            tileDisplay.setCrossed(true);
        }
    }

    public void submitTurn(View view) {
        final List<TilePosition> positions = clickedTiles.stream()
                .map(TileDisplay::getPosition).collect(Collectors.toList());
        final Turn turn = turnFactory.createTurn(positions);

        try {
            game.applyTurn(turn);
        } catch (InvalidTurnException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            clickedTiles.forEach(tileDisplay -> tileDisplay.setCrossed(false));
        }
        clickedTiles.clear();
    }

}