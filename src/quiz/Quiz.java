package quiz;

public class Quiz {
    private String question;
    private String correctAnswer;
    private String[] choices;

    public Quiz(String question, String correctAnswer, String[] choices) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getChoices() {
        return choices;
    }
}
