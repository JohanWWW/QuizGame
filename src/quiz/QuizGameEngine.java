package quiz;

import java.util.List;

/**
 * Keeps track of game states
 */
public class QuizGameEngine {
    private final Quiz[] quizzes;
    private int score = 0;
    private int round = 0;

    /**
     * Creates a new game with given settings
     * @param count amount of quizzes to create
     * @param category category of the quizzes
     * @throws QuizProviderFailedException if failed to provide quizzes due to an underlying cause
     */
    public QuizGameEngine(int count, int category) throws QuizProviderFailedException {
        var quizProvider = new QuizProvider(new QuizApiController());
        quizzes = quizProvider.getQuizzes(count, category);
        scrambleChoices();
    }

    /**
     * Creates a new game with given settings and provider
     * @param count amount of quizzes to create
     * @param category category of the quizzes
     * @param quizProvider the provider to use
     * @throws QuizProviderFailedException if failed to provide quizzes due to an underlying cause
     */
    QuizGameEngine(int count, int category, QuizProvider quizProvider) throws QuizProviderFailedException {
        quizzes = quizProvider.getQuizzes(count, category);
        scrambleChoices();
    }

    /**
     * Returns the question for the current round
     * @return the question for the current round
     */
    public String getQuestion() {
        return getQuizOfCurrentRound().getQuestion();
    }

    /**
     * Returns an array of choices for the current round
     * @return an array of choices for the current round
     */
    public String[] getChoices() {
        return getQuizOfCurrentRound().getChoices();
    }

    /**
     * Returns the correct answer for the current round
     * @return the correct answer for the current round
     */
    public String getCorrectAnswer() {
        return getQuizOfCurrentRound().getCorrectAnswer();
    }

    /**
     * Returns true or false whether the answer is correct or not
     * @param answerIndex the index of the choice
     * @return true if correct, otherwise false
     */
    public boolean isCorrectAnswer(int answerIndex) {
        return getCorrectAnswer().equals(
                getQuizOfCurrentRound().getChoices()[answerIndex]);
    }

    /**
     * Modifies game state to load data for next round
     * @param iterateScore whether or not to add 1 to the total score
     */
    public void nextRound(boolean iterateScore) {
        round++;
        if (iterateScore) {
            score++;
        }
    }

    /**
     * Returns the total score for current game
     * @return the total score for current game
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns true or false whether the game is over not
     * @return true if game is over, otherwise false
     */
    public boolean isGameOver() {
        return round == quizzes.length;
    }

    /**
     * Returns the count of quizzes for current game
     * @return the count of quizzes for current game
     */
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
