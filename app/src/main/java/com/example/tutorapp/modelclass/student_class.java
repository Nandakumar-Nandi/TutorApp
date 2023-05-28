package com.example.tutorapp.modelclass;

import androidx.annotation.NonNull;

public class student_class {
    String Name,score,Title,Class;

    public student_class(String name, String score, String title, String aClass) {
        Name = name;
        this.score = score;
        Title = title;
        Class = aClass;
    }


    public String getName() {
        return Name;
    }

    public String getScore() {
        return score;
    }

    public String getTitle() {
        return Title;
    }
    public String getuserClass() {
        return Class;
    }
}
