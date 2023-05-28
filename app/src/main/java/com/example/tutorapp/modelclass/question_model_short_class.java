package com.example.tutorapp.modelclass;

import java.util.ArrayList;

public class question_model_short_class {
    String Question,Answer;
    ArrayList<String> keywords;

    public question_model_short_class(String question, String answer, ArrayList<String> keywords) {
        Question = question;
        Answer = answer;
        this.keywords = keywords;
    }

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }
}
