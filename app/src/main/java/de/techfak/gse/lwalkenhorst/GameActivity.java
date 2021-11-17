package de.techfak.gse.lwalkenhorst;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameActivity extends AppCompatActivity {

    private Game game;
    private final Set<TileDisplay> clickedTiles = new HashSet<>();
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

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                final AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setMessage(R.string.exit_dialog)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, (dialog, id) -> GameActivity.this.finish())
                        .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());
                builder.create().show();
            }
        });
    }

    private void updatePoints() {
        ((TextView) findViewById(R.id.points)).setText(String.format("Points: %s", game.getPoints().toString()));
        for (final int column : game.getRuleManger().getFullColumns()) {
            view.markColumnAsFull(column);
        }
    }

    private void clickTile(final TileDisplay tileDisplay) {
        if (!game.getBoard().getTileAt(tileDisplay.getPosition()).isCrossed()) {
            if (tileDisplay.isMarked()) {
                clickedTiles.remove(tileDisplay);
                tileDisplay.setMarked(false);
            } else {
                clickedTiles.add(tileDisplay);
                tileDisplay.setMarked(true);
            }

        }
    }

    public void submitTurn(View view) {
        final List<TilePosition> positions = clickedTiles.stream()
                .map(TileDisplay::getPosition).collect(Collectors.toList());
        final Turn turn = turnFactory.createTurn(positions);

        try {
            game.applyTurn(turn);
            clickedTiles.forEach(TileDisplay::cross);
        } catch (InvalidTurnException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            clickedTiles.forEach(tileDisplay -> tileDisplay.setMarked(false));
        }
        clickedTiles.clear();
    }

}