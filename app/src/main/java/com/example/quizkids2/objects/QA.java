package com.example.quizkids2.objects;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class QA {

    @DocumentId
    private String id;
    private String question;
    private String category;
    private ArrayList<Answer> answers;

    public QA() {

    }

    public QA(String question, ArrayList<Answer> answers, String category) {
        this.question = question;
        this.answers = answers;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }
    public void setId(String documentId) {
        this.id = documentId;
    }
}
