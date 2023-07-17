package com.example.quizkids2.objects;

import java.util.ArrayList;

public class QA {
    private String question;
    private ArrayList<Answer> answers;

    public QA (String question, ArrayList<Answer> answers) {
        this.question = question;
        this.answers = answers;
    }
    public QA () {

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

}
