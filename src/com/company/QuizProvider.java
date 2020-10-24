package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizProvider {
    private final IQuizApiController controller;

    /**
     * @param controller the controller that the provider should use
     */
    public QuizProvider(IQuizApiController controller) {
        this.controller = controller;
    }

    /**
     * @param amount the amount of quizzes to get
     * @param category the category of the questions
     * @return an array of quizzes
     * @throws QuizProviderFailedException if failed to provide quizzes due to an underlying cause
     */
    public Quiz[] getQuizzes(int amount, int category) throws QuizProviderFailedException {
        QuizRootDto responseModel;
        try {
            responseModel = controller.getQuizzes(amount, category);
        } catch (HttpRequestException | HttpResponseNotOkException e) {
            throw new QuizProviderFailedException(e.getMessage(), e);
        }

        List<Quiz> mappedQuizzes = new ArrayList<>();
        for (QuizDto quizDto: responseModel.getResults()) {
            List<String> choices = new ArrayList<>();
            choices.add(quizDto.getCorrectAnswer());
            choices.addAll(Arrays.asList(quizDto.getIncorrectAnswers()));
            mappedQuizzes.add(new Quiz(quizDto.getQuestion(), quizDto.getCorrectAnswer(), choices.toArray(String[]::new)));
        }

        return mappedQuizzes.toArray(Quiz[]::new);
    }
}
