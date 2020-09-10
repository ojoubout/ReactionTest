package com.joubouti.reactiontest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class General {

    public static final int HIGH = 0;
    public static final int LOW = 1;

    public static int saveBestScore(SharedPreferences sharedPref, int score, int order) {
        int bestScore = sharedPref.getInt("BestScore", -1);
        if (bestScore == -1 || (order == HIGH && score > bestScore) || (order == LOW && score < bestScore)) {
            sharedPref.edit().putInt("BestScore", score).apply();
            return score;
        }
        return bestScore;
    }

    public static int getBestScore(SharedPreferences sharedPref) {
        return sharedPref.getInt("BestScore", -1);
    }

    public static void restart(Activity activity) {
        Intent intent = activity.getIntent();
        activity.finish();
        activity.startActivity(intent);
    }
}
