package com.company;

public class QuizGameEngine {
    private final Quiz[] quizzes;
    private int score = 0;
    private int round = 0;

    public QuizGameEngine(int count, int category) throws QuizProviderFailedException {
        var quizProvider = new QuizProvider(new QuizApiController());
        quizzes = quizProvider.getQuizzes(count, category);
    }

    // For testing
    QuizGameEngine(int count, int category, QuizProvider quizProvider) throws QuizProviderFailedException {
        quizzes = quizProvider.getQuizzes(count, category);
    }

    public String getQuestion() {
        return getQuizOfCurrentRound().getQuestion();
    }

    public String[] getChoices() {
        return getQuizOfCurrentRound().getChoices();
    }

    public boolean submitAnswer(int answerIndex) {
        if (getCorrectAnswer().equals(getQuizOfCurrentRound().getChoices()[0])) {
            score++;
            return true;
        }
        round++;
        return false;
    }

    public boolean isGameOver() {
        return round == quizzes.length;
    }

    private int getQuizLength() {
        return quizzes.length;
    }

    private Quiz getQuizOfCurrentRound() {
        return quizzes[round];
    }

    private String getCorrectAnswer() {
        return getQuizOfCurrentRound().getCorrectAnswer();
    }
}
