package de.techfak.gse.lwalkenhorst;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.model.InvalidTurnException;
import de.techfak.gse.lwalkenhorst.model.PropertyChange;
import de.techfak.gse.lwalkenhorst.model.TilePosition;
import de.techfak.gse.lwalkenhorst.model.Turn;
import de.techfak.gse.lwalkenhorst.model.TurnFactory;
import de.techfak.gse.lwalkenhorst.view.BoardView;
import de.techfak.gse.lwalkenhorst.view.DiceView;
import de.techfak.gse.lwalkenhorst.view.TileDisplay;

public class GameActivity extends AppCompatActivity {

    private final Set<TileDisplay> clickedTiles = new HashSet<>();
    private final TurnFactory turnFactory = new TurnFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Intent intent = getIntent();
        final Game game = (Game) intent.getSerializableExtra("game");

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        BoardView view = findViewById(R.id.boardViewLayout);
        view.init(game.getBoard(), game.getRuleManger(), metrics);

        view.registerClickHandler(tileDisplay -> clickTile(tileDisplay, game));
        final DiceView diceView = findViewById(R.id.diceViewLayout);
        diceView.init(metrics);

        findViewById(R.id.submit).setOnClickListener(v -> submitTurn(game));
        findViewById(R.id.dice).setOnClickListener(v -> throwDice(v, diceView, game));

        game.addListener(PropertyChange.SCORE, event -> updatePoints(view, game));
        game.addListener(PropertyChange.ROUND, event -> {
            updateRound(game);
            findViewById(R.id.dice).setVisibility(View.VISIBLE);
            diceView.hide();
            findViewById(R.id.submit).setEnabled(false);
        });

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

    private void updateRound(final Game game) {
        ((TextView) findViewById(R.id.round)).setText(String.format("Runde: %s", game.getRound()));
    }

    private void updatePoints(final BoardView view, final Game game) {
        ((TextView) findViewById(R.id.points)).setText(String.format("Points: %s", game.getPoints().toString()));
        for (final int column : game.getRuleManger().getFullColumns()) {
            view.markColumnAsFull(column);
        }
    }

    private void clickTile(final TileDisplay tileDisplay, final Game game) {
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

    public void submitTurn(final Game game) {
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

    public void throwDice(View view, DiceView diceView, Game game) {
        view.setVisibility(View.INVISIBLE);
        diceView.updateDice(game.getDiceResult());
        findViewById(R.id.submit).setEnabled(true);

    }
}