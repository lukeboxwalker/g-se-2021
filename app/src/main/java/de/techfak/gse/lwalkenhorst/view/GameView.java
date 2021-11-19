package de.techfak.gse.lwalkenhorst.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.techfak.gse.lwalkenhorst.R;
import de.techfak.gse.lwalkenhorst.event.RoundEvent;
import de.techfak.gse.lwalkenhorst.event.ScoreEvent;
import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.model.InvalidTurnException;
import de.techfak.gse.lwalkenhorst.model.TilePosition;
import de.techfak.gse.lwalkenhorst.model.Turn;
import de.techfak.gse.lwalkenhorst.model.TurnFactory;

public class GameView extends LinearLayout {

    private final Set<TileDisplay> clickedTiles = new HashSet<>();
    private final TurnFactory turnFactory = new TurnFactory();
    private BoardView boardView;
    private DiceView diceView;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(final Game game, final DisplayMetrics metrics) {
        boardView = findViewById(R.id.boardViewLayout);
        boardView.init(game.getBoard(), game.getRuleManger(), metrics);

        boardView.registerClickHandler(tileDisplay -> clickTile(tileDisplay, game));
        diceView = findViewById(R.id.diceViewLayout);
        diceView.init(metrics);

        findViewById(R.id.submit).setOnClickListener(v -> submitTurn(game));
        findViewById(R.id.dice).setOnClickListener(v -> throwDice(v, diceView, game));
    }

    public void updatePoints(final ScoreEvent event) {
        ((TextView) findViewById(R.id.points)).setText(String.format("Points: %s", event.getScore()));
        for (final int column : event.getFullColumns()) {
            boardView.markColumnAsFull(column);
        }
    }

    public void updateRound(final RoundEvent event) {
        ((TextView) findViewById(R.id.round)).setText(String.format("Runde: %s", event.getRound()));
        findViewById(R.id.dice).setVisibility(View.VISIBLE);
        diceView.hide();
        findViewById(R.id.submit).setEnabled(false);
    }

    public void throwDice(View view, DiceView diceView, Game game) {
        view.setVisibility(View.INVISIBLE);
        diceView.updateDice(game.getDiceResult());
        findViewById(R.id.submit).setEnabled(true);
    }

    public void submitTurn(final Game game) {
        final List<TilePosition> positions = clickedTiles.stream()
                .map(TileDisplay::getPosition).collect(Collectors.toList());
        final Turn turn = turnFactory.createTurn(positions);

        try {
            game.applyTurn(turn);
            clickedTiles.forEach(TileDisplay::cross);
        } catch (InvalidTurnException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            clickedTiles.forEach(tileDisplay -> tileDisplay.setMarked(false));
        }
        clickedTiles.clear();
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
}