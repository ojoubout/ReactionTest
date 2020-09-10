package com.joubouti.reactiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MemoryQuiz extends AppCompatActivity {

    private List<Boolean> squares = new ArrayList<>();
    private GridView gridView;
    private Button backButton, resetButton;
    private TextView bestScoreText, infoText, currentLevel;
    private ImageView clickScreen;

    private final int IDLE = 0;
    private final int SHOW = 1;
    private final int PLAY = 2;
    private final int FINISH = 3;

    private int state = IDLE;
    private static final String TAG = "MemoryQuiz";
    private int wrongClick;
    private int correctClick;
    private int level;
    private CountDownTimer timer;
    MemoryQuizAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_quiz);

        clickScreen = findViewById(R.id.clickScreen);
        bestScoreText = findViewById(R.id.bestScore);
        currentLevel = findViewById(R.id.currentLevel);
        infoText = findViewById(R.id.infoText);
        backButton = findViewById(R.id.backButton);
        resetButton = findViewById(R.id.resetButton);

        gridView = findViewById(R.id.memorySquares);

        adapter = new MemoryQuizAdapter(this, squares);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (state == PLAY) {
                Button item = (Button) view.findViewById(R.id.squareButton);
                if (squares.get(i)) {
                    item.setBackgroundColor(Color.argb(200, 255, 255, 255));
                    correctClick++;
                    if (correctClick >= (level + 2)) {
                        level++;
                        createLevel(level);
                    }
                } else {
                    item.setBackgroundColor(Color.argb(200, 0, 0, 0));
                    wrongClick++;
                    if (wrongClick >= 3) {
                        gridView.setVisibility(View.INVISIBLE);
                        infoText.setVisibility(View.VISIBLE);
                        infoText.setText("Level: " + level);
                        state = FINISH;
                    }
                }
            }
        });

        clickScreen.setOnClickListener(view -> {
            if (state == IDLE) {
                createLevel(1);
                gridView.setVisibility(View.VISIBLE);
                infoText.setVisibility(View.INVISIBLE);
            }
        });
        resetButton.setOnClickListener(view -> General.restart(this));
        backButton.setOnClickListener(view -> finish());
    }

    private void createLevel(int lvl) {
        int sNum = lvl + 2;
        int gridSize = 3 + lvl / 3;

        state = SHOW;
        level = lvl;
        currentLevel.setText("Level: " + level);
        correctClick = 0;
        wrongClick = 0;
        Set<Integer> positions = new HashSet<>();
        Random r = new Random();
        while (positions.size() < sNum) {
            positions.add(r.nextInt(gridSize * gridSize));
        }

        Log.e(TAG, "positions: " + positions.toString());

        squares.clear();
        gridView.setNumColumns(gridSize);
        for (int i = 0; i < gridSize * gridSize; i++) {
            if (positions.contains(i)) {
                squares.add(true);
//                adapter.notifyDataSetChanged();
            } else {
                squares.add(false);
            }
        }
        adapter.notifyDataSetChanged();
        new Handler().postDelayed(() -> {
            for (int i : positions) {
                Log.e(TAG, "count1: " + gridView.getChildCount());
                (gridView.getChildAt(i)).findViewById(R.id.squareButton).setBackgroundColor(Color.argb(200, 255, 255, 255));
            }

        }, 500);

        timer = new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                for (int i : positions) {
                    (gridView.getChildAt(i)).findViewById(R.id.squareButton).setBackgroundColor(Color.argb(32, 0, 0, 0));
                }
                state = PLAY;
            }
        }.start();



    }
}