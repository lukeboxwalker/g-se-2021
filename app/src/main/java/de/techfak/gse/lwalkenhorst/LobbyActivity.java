package de.techfak.gse.lwalkenhorst;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class LobbyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}