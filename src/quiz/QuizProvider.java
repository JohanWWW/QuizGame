package quiz;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class QuizProvider {
    private final IQuizApiController controller;

    /**
     * @param controller the controller that the provider should use
     */
    public QuizProvider(IQuizApiController controller) {
        this.controller = controller;
    }

    /**
     * @param amount the amount of quizzes to get
     * @return an array of quizzes
     * @throws QuizProviderFailedException if failed to provide quizzes due to an underlying cause
     */
    public Quiz[] getQuizzes(int amount) throws QuizProviderFailedException {
        QuizRootDto responseRootDto;
        try {
            responseRootDto = controller.getQuizzes(amount);
        } catch (HttpRequestException | HttpResponseNotOkException e) {
            throw new QuizProviderFailedException(e.getMessage(), e);
        }
        return map(responseRootDto.getResults());
    }

    private String decodeUTF8(String encodedString) {
        return URLDecoder.decode(encodedString, StandardCharsets.UTF_8);
    }

    private Quiz[] map(QuizDto[] dtos) {
        List<Quiz> mappedQuizzes = new ArrayList<>();
        for (QuizDto quizDto: dtos) {
            List<String> choices = new ArrayList<>();

            String question = decodeUTF8(quizDto.getQuestion());
            String correctAnswer = decodeUTF8(quizDto.getCorrectAnswer());
            List<String> incorrectAnswers = Arrays.stream(quizDto.getIncorrectAnswers())
                    .map(this::decodeUTF8)
                    .collect(Collectors.toList());

            choices.add(correctAnswer);
            choices.addAll(incorrectAnswers);

            mappedQuizzes.add(new Quiz(question, correctAnswer, choices.toArray(String[]::new)));
        }

        return mappedQuizzes.toArray(Quiz[]::new);
    }
}
