package com.example.paolo.spaceinvaders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;

public class MainActivity extends AppCompatActivity {

    PlayerView playerView;
    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerView = new PlayerView(this);

        setContentView(playerView);
    }
}
