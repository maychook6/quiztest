package com.example.quizkids2.objects;

import java.util.ArrayList;

public class AnimalsData {
    String qustion;
    ArrayList<Answer> answers;

    public String getQustion() {
        return qustion;
    }

    public void setQustion(String qustion) {
        this.qustion = qustion;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
