package quiz;

import java.util.List;

public class QuizGameEngine {
    private final Quiz[] quizzes;
    private int score = 0;
    private int round = 0;

    public QuizGameEngine(int count, int category) throws QuizProviderFailedException {
        var quizProvider = new QuizProvider(new QuizApiController());
        quizzes = quizProvider.getQuizzes(count, category);
        scrambleChoices();
    }

    // For testing
    QuizGameEngine(int count, int category, QuizProvider quizProvider) throws QuizProviderFailedException {
        quizzes = quizProvider.getQuizzes(count, category);
        scrambleChoices();
    }

    public String getQuestion() {
        return getQuizOfCurrentRound().getQuestion();
    }

    public String[] getChoices() {
        return getQuizOfCurrentRound().getChoices();
    }

    public String getCorrectAnswer() {
        return getQuizOfCurrentRound().getCorrectAnswer();
    }

    public boolean isCorrectAnswer(int answerIndex) {
        return getCorrectAnswer().equals(
                getQuizOfCurrentRound().getChoices()[answerIndex]);
    }

    public void nextRound(boolean iterateScore) {
        round++;
        if (iterateScore) {
            score++;
        }
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return round == quizzes.length;
    }

    public int getQuizLength() {
        return quizzes.length;
    }

    private Quiz getQuizOfCurrentRound() {
        return quizzes[round];
    }

    private void scrambleChoices() {
        for (Quiz quiz: quizzes) {
            String[] choices = quiz.getChoices();
            for (int i = 0; i < choices.length; i++) {
                int randomIndex = (int)(Math.random() * choices.length);

                // Swap
                String temp = choices[randomIndex];
                choices[randomIndex] = choices[i];
                choices[i] = temp;
            }
        }
    }
}
