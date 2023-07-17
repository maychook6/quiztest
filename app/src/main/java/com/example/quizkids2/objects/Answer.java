package com.example.quizkids2.objects;

import java.util.ArrayList;

public class Answer {

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }
    public Answer() {
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    private String answer;
    private boolean isCorrect;



}


