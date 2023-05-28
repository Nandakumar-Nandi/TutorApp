package com.example.tutorapp.modelclass;

public class Question_set {
    String Id;
    String Title;
    String Count;
    String Difficulty;
    String type;
    public Question_set(String id, String title, String count, String difficulty, String type) {
        Id = id;
        Title = title;
        Count = count;
        Difficulty = difficulty;
        this.type = type;
    }

    public String getTitle() {
        return Title;
    }

    public String getCount() {
        return Count;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public String getId() {
        return Id;
    }

    public String getType() {
        return type;
    }
}
