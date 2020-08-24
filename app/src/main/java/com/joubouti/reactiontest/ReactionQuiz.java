package com.joubouti.reactiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class ReactionQuiz extends AppCompatActivity {

    private Button backButton;
    private TextView bestScoreText, infoText;
    private ImageView clickScreen;

    private final int IDLE = 0;
    private final int WAITING = 1;
    private final int CLICK = 2;

    private CountDownTimer timer;
    private long tick = 0;
    private int bestScore;

    SharedPreferences sharedPref;


    private int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction_quiz);
        clickScreen = findViewById(R.id.clickScreen);
        bestScoreText = findViewById(R.id.bestScore);
        infoText = findViewById(R.id.infoText);
        backButton = findViewById(R.id.backButton);

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        bestScore = sharedPref.getInt("BestScore", -1);
        bestScoreText.setText("Your best: "+ (bestScore >= 0 ? Integer.toString(bestScore) + " ms" : "N/A"));

        backButton.setOnClickListener(view -> finish());
        clickScreen.setOnClickListener(view -> {
            if (state == IDLE) {
                clickScreen.setBackgroundColor(getResources().getColor(R.color.WAITING));
                state = WAITING;
                infoText.setText("WAITING...");
                final Random r = new Random();
                int time = r.nextInt(4000) + 3000;
                timer = new CountDownTimer(time, 1000) {
                    @Override
                    public void onTick(long l) {}

                    @Override
                    public void onFinish() {
                        clickScreen.setBackgroundColor(getResources().getColor(R.color.CLICK));
                        state = CLICK;
                        infoText.setText("CLICK");
                        tick = SystemClock.elapsedRealtime();

                    }
                }.start();

            } else if (state == WAITING) {
                clickScreen.setBackgroundColor(getResources().getColor(R.color.IDLE));
                state = IDLE;
                infoText.setText("Too Soon");
                timer.cancel();
            } else if (state == CLICK) {
                clickScreen.setBackgroundColor(getResources().getColor(R.color.IDLE));
                state = IDLE;
                int diff = (int) (SystemClock.elapsedRealtime() - tick);
                infoText.setText("Your best: " + diff + " ms");
                if (bestScore == -1 || diff < bestScore) {
                    bestScore = diff;
                    bestScoreText.setText("Your best: " + diff + " ms");
                    sharedPref.edit().putInt("BestScore", diff).apply();
                }
            }
        });
    }
}