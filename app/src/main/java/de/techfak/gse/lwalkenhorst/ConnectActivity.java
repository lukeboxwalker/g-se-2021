package de.techfak.gse.lwalkenhorst;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.techfak.gse.lwalkenhorst.client.Client;
import de.techfak.se.multiplayer.game.BaseGame;
import de.techfak.se.multiplayer.game.BaseGameImpl;
import de.techfak.se.multiplayer.game.Board;
import de.techfak.se.multiplayer.game.SynchronizedGame;
import de.techfak.se.multiplayer.server.Server;

public class ConnectActivity extends AppCompatActivity {

    private static final Random random = new Random();

    private Server server;
    private boolean host = false;
    private String playerName;
    private String serverIp;
    private boolean exit = false;
    private final String[] names = new String[]{
            "Freddy",
            "Sahra",
            "Preston",
            "Konnor",
            "Alanis",
            "Jon",
            "Eva",
            "Peyton",
            "Jenna",
            "Laibah",
            "Theia",
            "Rahma",
            "Dante",
            "Hibba",
            "Krisha",
            "Sofia",
            "Cristina",
            "Otis",
            "Hayley",
            "Vikram",
    };

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    ((EditText) findViewById(R.id.ip_select)).setText(result.getContents());
                    start();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        findViewById(R.id.button).setOnClickListener(v -> start());
        ((EditText) findViewById(R.id.name_select)).setText(names[random.nextInt(names.length)]);

        final Intent intent = getIntent();
        if (intent.hasExtra("board")) {
            findViewById(R.id.addressInput).setVisibility(View.INVISIBLE);
            findViewById(R.id.scan).setVisibility(View.INVISIBLE);
            this.host = true;
            startServer(intent);
        }
    }

    public String getIpAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : interfaces) {
                List<InetAddress> addresses = Collections.list(networkInterface.getInetAddresses());
                for (InetAddress address : addresses) {
                    if (!address.isLoopbackAddress()) {
                        String hostAddress = address.getHostAddress();
                        assert hostAddress != null;
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (isIPv4)
                            return hostAddress;
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return "127.0.0.1";
    }

    public void start() {
        final TextInputEditText ip_input = findViewById(R.id.ip_select);
        final Editable ip_text = ip_input.getText();
        final TextInputEditText name_select = findViewById(R.id.name_select);
        final Editable name_text = name_select.getText();

        if (name_text == null) {
            return;
        }

        playerName = name_text.toString();
        if (host) {
            serverIp = "http://" + getIpAddress() + ":8080";
        } else if (ip_text == null) {
            return;
        } else {
            serverIp = ip_text.toString();
        }

        Client client = new Client(serverIp, this);
        client.connect(this::connectSucceed, error -> Toast.makeText(this, "No connection found!", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (this.exit) {
            finish();
        }
    }

    public void connectSucceed(final String response) {
        if ("Encore".equals(response)) {
            Client client = new Client(serverIp, this);
            final String name = playerName == null ? "" : playerName;
            client.register(name, this::registerSucceed, error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Request didn't match 'Encore'", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerSucceed(final String response) {
        Intent intent = new Intent(this, LobbyActivity.class);
        intent.putExtra("name", playerName);
        intent.putExtra("ip", serverIp);
        intent.putExtra("host", host);

        this.exit = true;
        startActivity(intent);
    }

    private void startServer(final Intent intent) {
        final Board board = (Board) intent.getSerializableExtra("board");
        final BaseGame baseGame = new BaseGameImpl(board);
        final SynchronizedGame game = new SynchronizedGame(baseGame);
        server = new Server(game);
        server.start(8080);
    }

    @Override
    protected void onDestroy() {
        if (server != null) {
            server.stop();
        }
        super.onDestroy();
    }

    public void scan(View view) {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(false);
        barcodeLauncher.launch(options);
    }
}