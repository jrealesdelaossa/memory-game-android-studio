package com.example.buscar_parejas_juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnPlay;

    private void uploadPreferences() {
        SharedPreferences preferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String namePlayerOne = preferences.getString("namePlayerOne", "Player One");
        String namePlayerTwo = preferences.getString("namePlayerTwo", "Player Two");
        EditText inputPlayer1 = findViewById(R.id.id_name1);
        EditText inputPlayer2 = findViewById(R.id.id_name2);
        inputPlayer1.setText(namePlayerOne);
        inputPlayer2.setText(namePlayerTwo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get shared preferences
        uploadPreferences();
        TextView inputPlayer1 = findViewById(R.id.id_name1);
        TextView inputPlayer2 = findViewById(R.id.id_name2);

        btnPlay = findViewById(R.id.Btnjugar);

        btnPlay.setOnClickListener(view -> {
            // Create shared preferences
            SharedPreferences preferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            // Get names of players and save them in SharedPreferences
            String namePlayerOne = inputPlayer1.getText().toString();
            String namePlayerTwo = inputPlayer2.getText().toString();
            editor.putString("namePlayerOne", namePlayerOne);
            editor.putString("namePlayerTwo", namePlayerTwo);
            editor.apply();

            playSound(R.raw.button);

            startActivity(new Intent(MainActivity.this, SelectLevel.class)
                    .putExtra("namePlayerOne", namePlayerOne)
                    .putExtra("namePlayerTwo", namePlayerTwo));
        });
    }

    private void playSound(int sound) {
        MediaPlayer player = MediaPlayer.create(MainActivity.this, sound);
        player.start();
    }
}