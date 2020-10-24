package com.company;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

            String question = decodeUTF8(quizDto.getQuestion());
            String correctAnswer = decodeUTF8(quizDto.getCorrectAnswer());
            List<String> incorrectAnswers = Arrays.stream(quizDto.getIncorrectAnswers())
                    .map(this::decodeUTF8)
                    .collect(Collectors.toList());

            choices.add(correctAnswer);
            choices.addAll(incorrectAnswers);
            scrambleChoices(choices);

            mappedQuizzes.add(new Quiz(question, correctAnswer, choices.toArray(String[]::new)));
        }

        return mappedQuizzes.toArray(Quiz[]::new);
    }

    private String decodeUTF8(String encodedString) {
        return URLDecoder.decode(encodedString, StandardCharsets.UTF_8);
    }

    private void scrambleChoices(List<String> quizzes) {
        for (int i = 0; i < quizzes.size(); i++) {
            int randomIndex = (int)(Math.random() * quizzes.size());

            // Swap
            String temp = quizzes.get(randomIndex);
            quizzes.set(randomIndex, quizzes.get(i));
            quizzes.set(i, temp);
        }
    }
}
