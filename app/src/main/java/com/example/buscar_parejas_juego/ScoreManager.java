package com.example.buscar_parejas_juego;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

public class ScoreManager {
    public static void updateScoreGlobal(Context contextActivity, TextView pointsPlayerOne, TextView pointsPlayerTwo, String namePlayerOne, String namePlayerTwo, int turn) {
        SharedPreferences preferences = contextActivity.getSharedPreferences("Scores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.apply();

        String score = preferences.getString("scores", "");
        String names = preferences.getString("name", "");
        String[] stringArray = score.split(",");
        String[] namesArray = names.split(",");

        int[] intArray = new int[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }

        if (turn == 1) {
            Log.i("log", "Winner is " + namePlayerOne + " with " + pointsPlayerOne.getText() + " points");
            int points = Integer.parseInt(pointsPlayerOne.getText().toString());
            for (int i = 0; i < intArray.length; i++) {
                if (points > intArray[i]) {
                    intArray[i] = points; // Change score
                    namesArray[i] = namePlayerOne; // Change name
                    break;
                }
            }
        } else {
            int points = Integer.parseInt(pointsPlayerTwo.getText().toString());
            for (int i = 0; i < intArray.length; i++) {
                if (points > intArray[i]) {
                    intArray[i] = points; // Change score
                    namesArray[i] = namePlayerTwo; // Change name
                    break;
                }
            }
        }

        for (int i = 0; i < intArray.length; i++) {
            Log.i("log", "Name " + namesArray[i] + " --> Score is " + intArray[i]);
        }
    }
}
