package com.joubouti.reactiontest;

public class QuizItem {
    private String mName;
    private int mImage;
    private String mDescription;

    public QuizItem(String name, int imageId, String description) {
        mName = name;
        mImage = imageId;
        mDescription = description;
    }

    public String getName() {
        return mName;
    }

    public int getImageId() {
        return mImage;
    }

    public String getDescription() {
        return mDescription;
    }
}
