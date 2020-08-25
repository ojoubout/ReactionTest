package com.joubouti.reactiontest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class AimQuiz extends AppCompatActivity {

    private ImageView targetImage, background;
    private TextView bestScoreText, infoText, remainingText;
    private Button backButton;
    private boolean isPlaying = false;
    private static final String TAG = "AimQuiz";
    private static final int TARGETS = 13;
    private int targetsLeft;
    private long tick = 0;

    private int bestScore;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aim_quiz);

        targetImage = findViewById(R.id.targetImage);
        bestScoreText = findViewById(R.id.bestScore);
        remainingText = findViewById(R.id.remaining);
        infoText = findViewById(R.id.infoText);
        backButton = findViewById(R.id.backButton);
        background = findViewById(R.id.background);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        bestScore = sharedPref.getInt("BestScore", -1);
        refreshBestScore();
        background.setOnClickListener(view -> {
            if (!isPlaying) {
                changePlayingState(true);
            }
        });
        targetImage.setOnClickListener(view -> {
            if (isPlaying) {
                if (--targetsLeft == 0) {
                    changePlayingState(false);
                    int diff = (int) (SystemClock.elapsedRealtime() - tick) / TARGETS;
                    infoText.setText("AVG Time per target:\n " + diff + " ms\n" +
                            "Tap to Start");
                    if (bestScore == -1 || diff < bestScore) {
                        bestScore = diff;
                        refreshBestScore();
                        sharedPref.edit().putInt("BestScore", diff).apply();
                    }

                } else {
                    targetChangePosition();
                }
            }
        });
        backButton.setOnClickListener(view -> finish());
    }

    private void refreshBestScore() {
        if (bestScore == -1) {
            bestScoreText.setText("Your best: N/A");
        } else {
            bestScoreText.setText("Your best: "+ Integer.toString(bestScore) + " ms");
        }
    }

    private void changePlayingState(boolean state) {
        isPlaying = state;
        if (state) {
            targetImage.setVisibility(View.VISIBLE);
            remainingText.setVisibility(View.VISIBLE);
            infoText.setVisibility(View.INVISIBLE);
            targetsLeft = TARGETS;
            tick = SystemClock.elapsedRealtime();
            targetChangePosition();
        } else {
            targetImage.setVisibility(View.INVISIBLE);
            remainingText.setVisibility(View.INVISIBLE);
            infoText.setVisibility(View.VISIBLE);

        }
    }

    private void targetChangePosition() {
        ConstraintLayout.MarginLayoutParams layoutParams = (ConstraintLayout.MarginLayoutParams) targetImage.getLayoutParams();
        int minX = 10;
        int minY = remainingText.getHeight() + 45;
        int maxX = background.getWidth() - targetImage.getWidth() - 10;
        int maxY = background.getHeight() - targetImage.getHeight() - 10;

        Random r = new Random();
        int ranX = r.nextInt(maxX - minX) + minX;
        int ranY = r.nextInt(maxY - minY) + minY;

        layoutParams.leftMargin = ranX;
        layoutParams.topMargin = ranY;
        targetImage.requestLayout();

        remainingText.setText("Remaining:\n" + targetsLeft);

    }

}