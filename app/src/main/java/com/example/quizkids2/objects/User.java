package com.example.quizkids2.objects;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class User {

    @DocumentId
    private String id;
    private String email;
    private String nickname;
    private Integer score;

    public User(String email, String nickname, Integer score, ArrayList<String> questionsCorrectIds) {
        this.email = email;
        this.nickname = nickname;
        this.score = score;
        QuestionsCorrectIds = questionsCorrectIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ArrayList<String> getQuestionsCorrectIds() {
        return QuestionsCorrectIds;
    }

    public void setQuestionsCorrectIds(ArrayList<String> questionsCorrectIds) {
        QuestionsCorrectIds = questionsCorrectIds;
    }

    private ArrayList<String> QuestionsCorrectIds;
}
