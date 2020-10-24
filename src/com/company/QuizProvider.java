package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizProvider {
    private final IQuizApiController controller;

    /**
     * @param controller the quiz api controller to use
     */
    public QuizProvider(IQuizApiController controller) {
        this.controller = controller;
    }

    /**
     * @param amount the amount of quizzes to get
     * @param category the category of the questions
     * @param type
     * @return an array of quizzes
     * @throws Exception
     */
    public Quiz[] getQuizzes(int amount, int category, String type) throws Exception {
        QuizRootResponseModel responseModel = controller.getQuizzes(amount, category, type);

        List<Quiz> mappedQuizzes = new ArrayList<>();
        for (QuizResponseModel quizDto: responseModel.getResults()) {
            List<String> choices = new ArrayList<>();
            choices.add(quizDto.getCorrectAnswer());
            choices.addAll(Arrays.asList(quizDto.getIncorrectAnswers()));
            mappedQuizzes.add(new Quiz(quizDto.getQuestion(), quizDto.getCorrectAnswer(), choices.toArray(String[]::new)));
        }

        return mappedQuizzes.toArray(Quiz[]::new);
    }
}
