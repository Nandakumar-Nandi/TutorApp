package com.example.tutorapp.modelclass;

import android.content.Context;

public class question_model_class{
    String Question,Answer,Choice1,Choice2,Choice3,type;
    Context context;

    public question_model_class(String question, String answer, String choice1, String choice2, String choice3, String type, Context context) {
        Question = question;
        Answer = answer;
        Choice1 = choice1;
        Choice2 = choice2;
        Choice3 = choice3;
        this.type = type;
        this.context = context;
    }

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public String getChoice1() {
        return Choice1;
    }

    public String getChoice2() {
        return Choice2;
    }

    public String getChoice3() {
        return Choice3;
    }

    public Context getcontext() {
        return context;
    }

    public String getType() {
        return type;
    }
}
