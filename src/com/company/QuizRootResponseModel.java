package com.company;

public class QuizRootResponseModel {
    private int responseCode;
    private QuizResponseModel[] results;

    public int getResponseCode() {
        return responseCode;
    }

    public QuizResponseModel[] getResults() {
        return results;
    }
}