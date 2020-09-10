package com.joubouti.reactiontest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MemoryQuizAdapter extends ArrayAdapter<Boolean> {

    private Context context;
    private List<Boolean> object;

    public MemoryQuizAdapter(@NonNull Context context, @NonNull List<Boolean> objects) {
        super(context, 0, objects);
        this.context = context;
        this.object = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.item_memory_square, null);
        }

        Boolean item = getItem(position);
        if (item != null) {
//            if (item) {
//                v.findViewById(R.id.squareButton).setBackgroundColor(Color.argb(200, 255, 255, 255));
//            } else {
                v.findViewById(R.id.squareButton).setBackgroundColor(Color.argb(32, 0, 0, 0));
//            }
        }
        return v;
    }
}
