package com.example.quizkids2.objects;

import com.google.firebase.firestore.DocumentId;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class User {

    @DocumentId
    private String id;
    private String email;
    private String nickname;
    private Integer score;
    private boolean canPlay;
    private long timeToPlay;
    private ArrayList<String> questionsCorrectIds;

    public User() {
    }

    public User(String email, String nickname, Integer score, long timeToPlay, boolean canPlay, ArrayList<String> questionsCorrectIds) {
        this.email = email;
        this.nickname = nickname;
        this.score = score;
        this.questionsCorrectIds = questionsCorrectIds;
        this.timeToPlay = timeToPlay;
        this.canPlay = canPlay;
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
    public long getTimeToPlay() {
        return timeToPlay;
    }

    public void setTimeToPlay(long timeToPlay) {
        this.timeToPlay = timeToPlay;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public ArrayList<String> getQuestionsCorrectIds() {
        return questionsCorrectIds;
    }

    public void setQuestionsCorrectIds(ArrayList<String> questionsCorrectIds) {
        this.questionsCorrectIds = questionsCorrectIds;
    }

    public void addQuestionCorrectId (String questionCorrectId) {
        this.questionsCorrectIds.add(questionCorrectId);
    }

}
