package com.joubouti.reactiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ClickQuiz extends AppCompatActivity {

    private Button backButton, resetButton;
    private TextView bestScoreText, infoText;
    private ImageView clickScreen;

    private final int TIME = 10 * 1000; // 10 seconds

    private final int IDLE = 0;
    private final int CLICK = 1;
    private final int FINISH = 2;
    private int state = IDLE;
    private int clicks;
    private int bestScore;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_quiz);

        clickScreen = findViewById(R.id.clickScreen);
        bestScoreText = findViewById(R.id.bestScore);
        infoText = findViewById(R.id.infoText);
        backButton = findViewById(R.id.backButton);
        resetButton = findViewById(R.id.resetButton);

        bestScore = General.getBestScore(this);
        refreshBestScore();
        clickScreen.setOnClickListener(view -> {
            if (state == IDLE) {
                clicks = 1;
                infoText.setText(clicks + " clicks");
                timer = new CountDownTimer(TIME, 1000) {
                    @Override
                    public void onTick(long l) {}

                    @Override
                    public void onFinish() {
                        clickScreen.setBackgroundColor(getResources().getColor(R.color.CLICK));
                        state = FINISH;
                        bestScore = General.saveBestScore(ClickQuiz.this, clicks, General.HIGH);
                        refreshBestScore();
                        infoText.setText(clicks + " clicks\n" + clicks / (TIME / 1000f) + " CPS");
                    }
                }.start();
                state = CLICK;
            } else if (state == CLICK){
                clicks++;
                infoText.setText(clicks + " clicks");
            }
        });

        resetButton.setOnClickListener(view -> General.restart(this));
        backButton.setOnClickListener(view -> finish());
    }

    private void refreshBestScore() {
        if (bestScore == -1) {
            bestScoreText.setText("Your best: N/A");
        } else {
            bestScoreText.setText("Your best: "+ Integer.toString(bestScore) + " clicks");
        }
    }



}