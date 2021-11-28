package de.techfak.gse.lwalkenhorst;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

import de.techfak.gse.lwalkenhorst.client.Client;
import de.techfak.gse.lwalkenhorst.model.DiceTurnValidator;
import de.techfak.gse.lwalkenhorst.model.Game;
import de.techfak.gse.lwalkenhorst.model.GameFactory;
import de.techfak.gse.lwalkenhorst.model.GameStrategy;
import de.techfak.gse.lwalkenhorst.model.InvalidBoardLayoutException;
import de.techfak.gse.lwalkenhorst.model.InvalidFieldException;
import de.techfak.gse.lwalkenhorst.model.MultiPlayerStrategy;
import de.techfak.gse.lwalkenhorst.model.SinglePlayerStrategy;
import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.server.response_body.BoardResponse;
import de.techfak.se.multiplayer.server.response_body.PlayerListResponse;
import de.techfak.se.multiplayer.server.response_body.StatusResponse;


public class LobbyActivity extends AppCompatActivity {

    private static final int ROWS = 7;
    private static final int COLUMNS = 15;
    private final static ObjectMapper JSON_PARSER = new ObjectMapper();

    private Client client;
    private String name;
    private String ip;
    private final Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        final Intent intent = getIntent();
        name = intent.getStringExtra("name");
        ip = intent.getStringExtra("ip");
        ((TextView) findViewById(R.id.name)).setText(String.format("Name: %s", name));
        ((TextView) findViewById(R.id.ip)).setText(String.format("IP: %s", ip));


        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(ip, BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = findViewById(R.id.qrCode);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        client = new Client(ip, this);

        final TimerTask task = new TimerTask() {
            public void run() {
                client.getPlayers(name, LobbyActivity.this::showPlayers);
                client.getStatus(name, LobbyActivity.this::checkStatus);
            }
        };

        timer.schedule(task, 0, 1000L);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    public void showPlayers(final String response) {
        try {
            final PlayerListResponse playerListResponse = JSON_PARSER.readValue(response, PlayerListResponse.class);
            TextView countView = findViewById(R.id.count);
            countView.setText(String.format(Locale.ENGLISH, "Players waiting: %d", playerListResponse.getPlayers().size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkStatus(final String response) {
        try {
            final StatusResponse statusResponse = JSON_PARSER.readValue(response, StatusResponse.class);
            if (statusResponse.getStatus() == GameStatus.RUNNING) {
                client.getBoard(name, this::start);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(final String response) {
        try {
            final BoardResponse boardResponse = JSON_PARSER.readValue(response, BoardResponse.class);

            final Supplier<GameStrategy> gameStrategySupplier = () -> new MultiPlayerStrategy(new Client(ip, this));
            final GameFactory factory = new GameFactory(ROWS, COLUMNS, DiceTurnValidator::new, SinglePlayerStrategy::new);
            final Game game = factory.createGame(boardResponse.getBoard());
            final Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
            intent.putExtra("game", game);

            timer.cancel();
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidBoardLayoutException | InvalidFieldException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void changeStatus(View view) {
        client.updateStatus(GameStatus.RUNNING, name, error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}