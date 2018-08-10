package com.codsit.appquestion.model;

public class Question {
    private  String id_q;
    private  String question;
    private  String state;

    public Question() {
    }

    public Question(String id_q, String question, String state) {
        this.id_q = id_q;
        this.question = question;
        this.state = state;
    }

    public String getId_q() {
        return id_q;
    }

    public void setId_q(String id_q) {
        this.id_q = id_q;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
