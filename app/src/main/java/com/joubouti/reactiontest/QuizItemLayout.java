package com.joubouti.reactiontest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class QuizItemLayout extends LinearLayout {
    public QuizItemLayout(Context context) {
        super(context);
    }

    public QuizItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuizItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }

}
