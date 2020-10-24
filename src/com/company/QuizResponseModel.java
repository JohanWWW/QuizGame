package com.company;

public class QuizResponseModel {
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private String[] incorrectAnswer;

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getIncorrectAnswer() {
        return incorrectAnswer;
    }
}
