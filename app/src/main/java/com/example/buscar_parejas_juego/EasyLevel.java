package com.example.buscar_parejas_juego;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EasyLevel extends AppCompatActivity {
    int shift;
    boolean cards = false;
    TextView playerOnTurn;
    Button restart;
    ImageView card1;
    ImageView card2;
    ImageView card3;
    ImageView card4;
    ImageView card5;
    ImageView card6;
    ImageView card7;
    ImageView card8;
    int imgCardOne = 0;
    int imgCardTwo = 0;
    ImageView carta_1;
    ImageView carta_2;
    TextView player1;
    TextView player2;
    TextView playerOnePoints;
    TextView playerTwoPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_level_activity);

        player1 = findViewById(R.id.showPlayerOne);
        player2 = findViewById(R.id.showPlayerTwo);
        playerOnTurn = findViewById(R.id.playerOnTurn);
        restart = findViewById(R.id.restartButton);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        card7 = findViewById(R.id.card7);
        card8 = findViewById(R.id.card8);
        playerOnePoints = findViewById(R.id.pointsPlayerOne);
        playerTwoPoints = findViewById(R.id.pointsPlayerTwo);

        String namePlayerOne = getIntent().getStringExtra("namePlayerOne");
        String namePlayerTwo = getIntent().getStringExtra("namePlayerTwo");

        ImageView[] imageViewsCards = {card1, card2, card3, card4, card5, card6, card7, card8};

        player1.setText(namePlayerOne);
        player2.setText(namePlayerTwo);

        // define turn
        shift = (int) (Math.random() * 2) + 1;
        switch (shift) {
            case 1:
                playerOnTurn.setText(namePlayerOne);
                break;
            case 2:
                playerOnTurn.setText(namePlayerTwo);
                break;
        }

        // Generate list of images aleatory
        List<Integer> cardList = mixImages();
        for (int i = 0; i < imageViewsCards.length; i++) {
            imageViewsCards[i].setImageResource(cardList.get(i));
        }
        new Handler().postDelayed(() -> {
            for (ImageView imageView : imageViewsCards) {
                imageView.setImageResource(android.R.color.transparent);
            }
        }, 1001);

        AtomicInteger matchCount = new AtomicInteger();
        for (int i = 0; i < imageViewsCards.length; i++) {
            final int pos = i;

            imageViewsCards[i].setOnClickListener(v -> {
                // calling sound
                playSound(R.raw.flip);

                animateLetter(imageViewsCards[pos], cardList.get(pos));
                if (!cards) {
                    carta_1 = imageViewsCards[pos];
                    imgCardOne = cardList.get(pos);
                    cards = true;
                } else {
                    carta_2 = imageViewsCards[pos];
                    imgCardTwo = cardList.get(pos);
                    cards = false;
                    boolean match = (imgCardOne == imgCardTwo);

                    if (!match) {
                        new Handler().postDelayed(() -> {
                            animateLetter(carta_1, android.R.color.transparent);
                            animateLetter(carta_2, android.R.color.transparent);
                        }, 1000);

                        if (shift == 1) {
                            subtractPoints(playerOnePoints, playerTwoPoints, shift);
                            playerOnTurn.setText(namePlayerTwo);
                            shift = 2;
                        } else if (shift == 2) {
                            subtractPoints(playerOnePoints, playerTwoPoints, shift);
                            playerOnTurn.setText(namePlayerOne);
                            shift = 1;
                        }
                    } else {
                        scorePoints(playerOnePoints, playerTwoPoints, shift);
                        Log.i("log", "Player in turn " + shift);
                        carta_1.setEnabled(false);
                        carta_2.setEnabled(false);
                        matchCount.getAndIncrement();
                        if (matchCount.get() == 4) {
                            // get context by send to updateScoreGlobal
                            Context context = getApplicationContext();

                            ScoreManager.updateScoreGlobal(context, playerOnePoints, playerTwoPoints, namePlayerOne, namePlayerTwo, shift);
                            startActivity(new Intent(EasyLevel.this, MainActivity.class));
                            finish();
                        }
                    }
                }
            });
        }

        restart.setOnClickListener(v -> {
            startActivity(new Intent(EasyLevel.this, EasyLevel.class));
            finish();
        });

    }

    private List<Integer> mixImages() {
        int[] images = {R.drawable.blue, R.drawable.green, R.drawable.red, R.drawable.white};
        List<Integer> list = new ArrayList<>();
        while (list.size() < 8) {
            int randomImage = images[(int) (Math.random() * 4)];
            int cont = 0;
            for (int i = 0; i < list.size(); i++) {
                if (cont >= 2) {
                    break;
                }
                if (list.get(i) == randomImage) {
                    cont++;
                }
            }
            if (cont < 2) {
                list.add(randomImage);
            }
        }
        return list;
    }

    private void playSound(int sound) {
        MediaPlayer player = MediaPlayer.create(EasyLevel.this, sound);
        player.start();
    }

    private void subtractPoints(TextView playerOne, TextView playerTwo, int turn) {
        if (turn == 1) {
            int points = Integer.parseInt(playerOne.getText().toString());
            if (points >= 50) {
                points -= 50;
                playerOne.setText(String.valueOf(points));
            }
        } else {
            int points = Integer.parseInt(playerTwo.getText().toString());
            if (points >= 50) {
                points -= 50;
                playerTwo.setText(String.valueOf(points));
            }
            playerTwo.setText(String.valueOf(points));
        }
    }

    private void scorePoints(TextView playerOne, TextView playerTwo, int turn) {
        if (turn == 1) {
            int points = Integer.parseInt(playerOne.getText().toString());
            points += 100;
            playerOne.setText(String.valueOf(points));
        } else {
            int points = Integer.parseInt(playerTwo.getText().toString());
            points += 100;
            playerTwo.setText(String.valueOf(points));
        }
    }

    private void animateLetter(ImageView img, int image) {
        ViewPropertyAnimator animator = img.animate().withLayer().rotationY(90).setDuration(50);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (img.getRotationY() == 90) {
                    img.setImageResource(image);
                    img.animate().rotationY(0).setDuration(50).setListener(null);
                }
            }
        });

    }
}
