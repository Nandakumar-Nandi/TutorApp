package com.example.tutorapp.modelclass;


public class score_class {
    String Question,Correct_answer,User_answer;

    public String getQuestion() {
        return Question;
    }

    public String getCorrect_answer() {
        return Correct_answer;
    }

    public String getUser_answer() {
        return User_answer;
    }

    public score_class(String question, String correct_answer, String user_answer) {
        Question = question;
        Correct_answer = correct_answer;
        User_answer = user_answer;
    }
}
