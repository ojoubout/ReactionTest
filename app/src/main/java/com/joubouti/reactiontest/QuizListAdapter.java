package com.joubouti.reactiontest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class QuizListAdapter extends ArrayAdapter<QuizItem> {
    private Context mContext;
    private List<QuizItem> quizList;

    public QuizListAdapter(@NonNull Context context, @NonNull List<QuizItem> objects) {
        super(context, 0, objects);
        mContext = context;
        quizList = objects;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.quiz_item_layout, null);
        }
        QuizItem quiz = getItem(position);
        if (quiz != null) {
            ImageView icon = view.findViewById(R.id.quizItemImage);
            TextView name = view.findViewById(R.id.quizName);

            icon.setImageResource(quiz.getImageId());
            name.setText(quiz.getName());
        }
        return view;
    }
}
