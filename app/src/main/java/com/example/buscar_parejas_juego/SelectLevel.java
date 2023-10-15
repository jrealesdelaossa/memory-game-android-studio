package com.example.buscar_parejas_juego;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SelectLevel extends AppCompatActivity {
    Button easyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level_activity);

        easyButton = findViewById(R.id.easyButton);
        String namePlayerOne = getIntent().getStringExtra("namePlayerOne");
        String namePlayerTwo = getIntent().getStringExtra("namePlayerTwo");

        // Start level
        easyButton.setOnClickListener(view1 -> {
            playSound(R.raw.button);
            startActivity(new Intent(SelectLevel.this, EasyLevel.class)
                    .putExtra("namePlayerOne", namePlayerOne)
                    .putExtra("namePlayerTwo", namePlayerTwo));
        });
    }

    private void playSound(int sound) {
        MediaPlayer player = MediaPlayer.create(SelectLevel.this, sound);
        player.start();
    }
}
