package com.joubouti.reactiontest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class TypingQuiz extends AppCompatActivity {

    private ImageView background;
    private EditText editText;
    private TextView bestScoreText, infoText, sequenceText, wpmText;
    private Button backButton;
    private boolean isPlaying = false;
    private SpannableString sequence;
    private String[] sequences;
    private final int SEQUENCES_COUNT = 16;
    private int sequenceID;
    private long tick;

    private int lastSize = 0;
    private static final String TAG = "TypingQuiz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing_quiz);

        bestScoreText = findViewById(R.id.bestScore);
        wpmText = findViewById(R.id.wpmText);
        infoText = findViewById(R.id.infoText);
        sequenceText = findViewById(R.id.sequenceText);
        backButton = findViewById(R.id.backButton);
        background = findViewById(R.id.background);
        editText = findViewById(R.id.editText);
        sequences = getResources().getStringArray(R.array.typing_sentences);

        background.setOnClickListener(view -> {
            setKeyboardVisible(true);
            if (!isPlaying) {
                isPlaying = true;
                sequenceText.setVisibility(View.VISIBLE);
                infoText.setVisibility(View.INVISIBLE);
                wpmText.setVisibility(View.VISIBLE);
                editText.setText("");
                tick = SystemClock.elapsedRealtime();
                Random r = new Random();
                sequenceID = 0; // r.nextInt(SEQUENCES_COUNT)
                sequence = new SpannableString(sequences[sequenceID]);
                sequenceText.setText(sequence);
            }
        });
//        BackgroundColorSpan bgcRed = new BackgroundColorSpan(Color.RED);
//        BackgroundColorSpan bgcGreen = new BackgroundColorSpan(Color.GREEN);
        BackgroundColorSpan bgcNone = new BackgroundColorSpan(Color.TRANSPARENT);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wpmText.setText(s);
                sequence = new SpannableString(sequences[sequenceID]);
                if (s.toString().equals(sequenceText.getText().toString())) {
                    Log.e(TAG, "YOU WIN");
                    wpmText.setText("You WIN");
                    return;
                }
                for (int i = 0; i < sequenceText.length() && (i < s.length() || i < lastSize); i++) {
                    Log.e(TAG, "" + i);
                    if (i >= s.length()) {
                        sequence.setSpan(bgcNone, i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    } else if (sequence.charAt(i) == s.charAt(i)) {
                        sequence.setSpan(new BackgroundColorSpan(Color.GREEN), i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    } else {
                        sequence.setSpan(new BackgroundColorSpan(Color.RED), i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                }
                lastSize = s.length();
                sequenceText.setText(sequence);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public static int countWordsUsingSplit(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }


    private void setKeyboardVisible(boolean state) {
        if (state) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
//            editText.setInputType(InputType.TYPE_NULL);
        }
    }
}