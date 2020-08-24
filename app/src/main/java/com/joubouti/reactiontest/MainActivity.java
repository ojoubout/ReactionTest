package com.joubouti.reactiontest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    ArrayList<QuizItem> quizList = new ArrayList<>();

    String[] quizNames;
    String[] quizDescriptions;
    TypedArray quizIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.quizGridView);
        quizList.add(new QuizItem("First Quiz", R.drawable.ic_reaction, "This quiz is about testing your reaction time. the screen is gonna be blue when it become yellow you should click as fast as possible"));
        quizList.add(new QuizItem("Second Quiz", R.drawable.ic_reaction, ""));
        quizList.add(new QuizItem("Third Quiz", R.drawable.ic_reaction, ""));
        quizList.add(new QuizItem("Fourth Quiz", R.drawable.ic_reaction, ""));
        quizList.add(new QuizItem("Fifth Quiz", R.drawable.ic_reaction, ""));
        quizList.add(new QuizItem("Sixth Quiz", R.drawable.ic_reaction, ""));
        quizList.add(new QuizItem("Seventh Quiz", R.drawable.ic_reaction, ""));
        quizList.add(new QuizItem("Eight Quiz", R.drawable.ic_reaction, ""));
        quizList.add(new QuizItem("Nine Quiz", R.drawable.ic_reaction, ""));
        quizList.add(new QuizItem("tenth Quiz", R.drawable.ic_reaction, ""));

        QuizListAdapter  adapter = new QuizListAdapter(this, quizList);
        gridView.setAdapter(adapter);

        quizIcons = getResources().obtainTypedArray(R.array.icons);
        quizNames = getResources().getStringArray(R.array.quiz_names);
        quizDescriptions = getResources().getStringArray(R.array.quiz_description);


        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            createQuizInfo(i);
        });
    }

    private void createQuizInfo(int quizId) {
        final Dialog dialog = new Dialog(this);
        final QuizItem quiz = quizList.get(quizId);
        dialog.setContentView(R.layout.quiz_info_popup);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_cornered_layout);
        TextView infoTitle = dialog.findViewById(R.id.infoTitle);
        TextView infoDescription = dialog.findViewById(R.id.infoDescription);
        ImageView infoIcon = dialog.findViewById(R.id.infoIcon);
        Button playButton = dialog.findViewById(R.id.playButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);

        infoTitle.setText(quiz.getName());
        infoDescription.setText(quiz.getDescription());
        infoIcon.setImageResource(quiz.getImageId());
        cancelButton.setOnClickListener(view -> {
            dialog.dismiss();
        });
        playButton.setOnClickListener(view -> {
            if (quizId == 0) {
                Intent intent = new Intent(MainActivity.this, ReactionQuiz.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}