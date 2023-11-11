package com.example.buscar_parejas_juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class settingsHome extends AppCompatActivity {

    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_home);

        backBtn = findViewById(R.id.backButton);

        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(settingsHome.this, MainActivity.class));
            finish();
        });
    }
}