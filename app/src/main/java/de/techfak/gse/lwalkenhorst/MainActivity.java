package de.techfak.gse.lwalkenhorst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import de.techfak.gse.lwalkenhorst.model.DiceTurnValidator;
import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.model.GameFactory;
import de.techfak.gse.lwalkenhorst.model.InvalidBoardLayoutException;
import de.techfak.gse.lwalkenhorst.model.InvalidFieldException;
import de.techfak.gse.lwalkenhorst.model.TurnValidator;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

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
            final boolean diceActivated = ((SwitchMaterial)findViewById(R.id.diceSwitch)).isChecked();
            final GameFactory factory = new GameFactory(ROWS, COLUMNS, diceActivated ? DiceTurnValidator::new : TurnValidator::new);

            final Game game = factory.createGame(text.toString());
            final Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("game", game);

            startActivity(intent);
        } catch (InvalidBoardLayoutException | InvalidFieldException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }
}