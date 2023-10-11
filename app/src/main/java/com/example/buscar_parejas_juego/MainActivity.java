package com.example.buscar_parejas_juego;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int shift;
    boolean cards = false;
    Button btnPlay;
    Button easyButton;
    TextView playerOnTurn;
    ImageButton goBack;
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
    Integer tur;
    TextView playerOnePoints;
    TextView playerTwoPoints;

    private void uploadPreferences() {
        SharedPreferences preferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        String namePlayerOne = preferences.getString("namePlayerOne", "Player One");
        String namePlayerTwo = preferences.getString("namePlayerTwo", "Player Two");
        Log.i("log", "namePlayerOne: " + namePlayerOne);
        Log.i("log", "namePlayerTwo: " + namePlayerTwo);
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

            setContentView(R.layout.niveles);
            easyButton = findViewById(R.id.easyButton);

            // Start level
            easyButton.setOnClickListener(view1 -> {
                setContentView(R.layout.nivelfacil);
                player1= findViewById(R.id.showPlayerOne);
                player2= findViewById(R.id.showPlayerTwo);
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

                ImageView[] imagview = {card1, card2, card3, card4, card5, card6, card7, card8};

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
                for (int i= 0; i < imagview.length; i++){
                    imagview[i].setImageResource(cardList.get(i));
                }
                new Handler().postDelayed(() -> {
                    for (ImageView imageView : imagview) {
                        imageView.setImageResource(android.R.color.transparent);
                    }
                }, 1001);

                for (int i= 0; i<imagview.length; i++){
                    //imagview[i].setImageResource(lista.get(i));//
                    final int pos =i;

                    imagview[i].setOnClickListener(v -> {
                        animarcartas(imagview[pos], cardList.get(pos));
                        //validar//
                        if(!cards){
                            carta_1= imagview[pos];
                            imgCardOne = cardList.get(pos);
                            cards = true;
                        }else {
                            carta_2= imagview[pos];
                            imgCardTwo = cardList.get(pos);
                            cards = false;
                            boolean igual= (imgCardOne == imgCardTwo);
                            if(!igual){
                                new Handler().postDelayed(() -> {
                                    animarcartas(carta_1,android.R.color.transparent);
                                    animarcartas(carta_2,android.R.color.transparent);
                                    if (tur == 1) {
                                        playerOnTurn.setText(inputPlayer2.getText());
                                        tur = 2;
                                        int puntosActuales= Integer.parseInt(playerOnePoints.getText().toString());
                                        if(puntosActuales != 0){
                                            puntosActuales = puntosActuales - 50;
                                            String points;
                                            points = Integer.toString(puntosActuales);
                                            playerOnePoints.setText(points);
                                        }

                                    } else if (tur == 2) {
                                        playerOnTurn.setText(inputPlayer1.getText());
                                        tur = 1;
                                        int puntosActuales= Integer.parseInt(playerTwoPoints.getText().toString());
                                        if(puntosActuales != 0){
                                            puntosActuales = puntosActuales - 50;
                                            String points;
                                            points = Integer.toString(puntosActuales);
                                            playerTwoPoints.setText(points);
                                        }

                                    }

                                },1500);
                            }else{
                                // sumar puntos
                                if(tur == 1){
                                    int puntosActuales= Integer.parseInt(playerOnePoints.getText().toString());
                                    puntosActuales = puntosActuales + 100;
                                    String points;
                                    points = Integer.toString(puntosActuales);
                                    playerOnePoints.setText(points);
                                } else if (tur == 2) {
                                    int puntosActuales= Integer.parseInt(playerTwoPoints.getText().toString());
                                    puntosActuales = puntosActuales + 100;
                                    String points;
                                    points = Integer.toString(puntosActuales);
                                    playerTwoPoints.setText(points);
                                }
                            }
                        }
                    });
                }
                /*


                goBack.setOnClickListener(view11 -> startActivity(new Intent( MainActivity.this,MainActivity.class)));
                restart.setOnClickListener(v -> {
                    int tur = (int) (Math.random() * 2) + 1;
                    if (tur == 1) {
                        playerOnTurn.setText(inputPlayer1.getText());
                        shift = 1;
                    } else {
                        if (tur == 2) {
                            playerOnTurn.setText(inputPlayer2.getText());
                            shift = 2;
                        }
                    }
                    List<Integer> lista1 = aleatorio();
                    for (int i= 0; i<imagview.length; i++){
                        imagview[i].setImageResource(lista1.get(i));

                    }
                    new Handler().postDelayed(() -> {
                        for (ImageView imageView : imagview) {
                            imageView.setImageResource(android.R.color.transparent);
                        }
                    }, 1001);

                    for (int i= 0; i<imagview.length; i++){
                        //imagview[i].setImageResource(lista.get(i));//
                        final int pos =i;

                        imagview[i].setOnClickListener(v1 -> {
                            animarcartas(imagview[pos], lista1.get(pos));
                            //validar//
                            if(!cards){
                                carta_1= imagview[pos];
                                imgCardOne = lista1.get(pos);
                                cards = true;
                            }else {
                                carta_2= imagview[pos];
                                imgCardTwo = lista1.get(pos);
                                cards = false;
                                boolean igual= (imgCardOne == imgCardTwo);
                                if(!igual){
                                    new Handler().postDelayed(() -> {
                                        animarcartas(carta_1,android.R.color.transparent);
                                        animarcartas(carta_2,android.R.color.transparent);
                                    },1500);
                                }
                            }
                        });
                    }
                });


                 */
            });
        });
    }
    private void animarcartas(ImageView img,int image){
        ViewPropertyAnimator animator= img.animate().withLayer().rotationY(90).setDuration(400);
        animator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation){
                super.onAnimationEnd(animation);
                if(img.getRotationY()==90){
                    img.setImageResource(image);
                    img.animate().rotationY(0).setDuration(400).setListener(null);
                }
            }
        });

    }
    private List<Integer> mixImages(){
        int[] imgs ={R.drawable.dark, R.drawable.divine, R.drawable.fire, R.drawable.water};
        List<Integer> lista = new ArrayList<>();
        while (lista.size() < 8){
            int imgAleatoria= imgs[(int) (Math.random()*4)];
            int cont = 0;
            for (int i= 0; i < lista.size(); i++){
                if (cont >= 2){
                    break;
                }
                if (lista.get(i)== imgAleatoria){
                    cont++;
                }
            }
            if (cont < 2){
                lista.add(imgAleatoria);
            }
        }
        return lista;
    }
}