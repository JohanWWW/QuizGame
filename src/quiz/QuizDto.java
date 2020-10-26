package quiz;

import com.google.gson.annotations.SerializedName;

class QuizDto {
    private final String category;
    private final String type;
    private final String difficulty;
    private final String question;
    @SerializedName("correct_answer")
    private final String correctAnswer;
    @SerializedName("incorrect_answers")
    private final String[] incorrectAnswers;

    QuizDto(String category, String type, String difficulty, String question, String correctAnswer, String[] incorrectAnswers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

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

    public String[] getIncorrectAnswers() {
        return incorrectAnswers;
    }
}
