package com.company;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class QuizGameTests {

    @Test
    @DisplayName("Validate that the Quiz controller deserializes response data to dto:s correctly")
    void apiControllerGetQuizzesReturnsCorrectObject() throws Exception {
        // Arrange
        IQuizApiController fakeController = createFakeQuizApiController("testData/test_response.json");

        // Act
        QuizRootDto responseQuizRoot = fakeController.getQuizzes(0, 0); // <- the arguments are ignored
        QuizDto[] responseQuizzes = responseQuizRoot.getResults();

        // Assert
        assertEquals(0, responseQuizRoot.getResponseCode());

        // Quiz 1
        assertQuizDto(new QuizDto(
                "Science%3A%20Computers",
                "multiple",
                "medium",
                "Which%20programming%20language%20was%20developed%20by%20Sun%20Microsystems%20in%201995%3F",
                "Java",
                // Incorrect answers
                new String[] {
                    "Python",
                    "Solaris%20OS",
                    "C%2B%2B"
                }),
            responseQuizzes[0]);

        // Quiz 2
        assertQuizDto(new QuizDto(
                "Science%3A%20Computers",
                "multiple",
                "hard",
                "According%20to%20DeMorgan's%20Theorem%2C%20the%20Boolean%20expression%20(AB)'%20is%20equivalent%20to%3A",
                "A'%20%2B%20B'",
                // Incorrect answers
                new String[] {
                    "A'B%20%2B%20B'",
                    "A'B'",
                    "AB'%20%2B%20AB"
                }),
            responseQuizzes[1]);

        // Quiz 3
        assertQuizDto(new QuizDto(
                "Science%3A%20Computers",
                "multiple",
                "hard",
                "!%22#%C2%A4%25&/()=?%C2%B4%60%5E*@%C2%A3$%E2%82%AC%7B%5B%5D%7D%5C~%C3%A5%C3%A4%C3%B6%C3%85%C3%84%C3%96",
                "None%20of%20the%20above",
                // Incorrect answers
                new String[] {
                    "%C3%A5%C3%A6%C3%B8%C3%85%C3%86%C3%98",
                    "%C3%A1%C3%81%C3%A9%C3%89%C3%AD%C3%8D%C3%B3%C3%93%C3%BA%C3%9A%C3%BD%C3%9D%C3%BE%C3%9E%C3%B0%C3%90%C3%B6%C3%96",
                    "%C3%A5%C3%A4%C3%B6%C3%85%C3%84%C3%96"
                }),
            responseQuizzes[2]);
    }

    @Test
    @DisplayName("Validate that the Quiz provider provides quizzes that are correctly mapped from the dto:s")
    void quizProviderGetQuizzesReturnsCorrectMapping() throws Exception {
        // Arrange
        IQuizApiController fakeController = createFakeQuizApiController("testData/test_response.json");
        var provider = new QuizProvider(fakeController);

        // Act
        Quiz[] quizzes = provider.getQuizzes(0, 0); // <- the arguments are ignored

        // Assert
        // Quiz 1
        assertQuiz(new Quiz(
                "Which programming language was developed by Sun Microsystems in 1995?",
                "Java",
                new String[] {
                    "Java",
                    "Python",
                    "Solaris OS",
                    "C++"
                }),
            quizzes[0]);

        // Quiz 2
        assertQuiz(new Quiz(
                "According to DeMorgan's Theorem, the Boolean expression (AB)' is equivalent to:",
                "A' + B'",
                new String[] {
                    "A' + B'",
                    "A'B + B'",
                    "A'B'",
                    "AB' + AB"
                }),
            quizzes[1]);

        // Quiz 3
        assertQuiz(new Quiz(
                "!\"#¤%&/()=?´`^*@£$€{[]}\\~åäöÅÄÖ",
                "None of the above",
                new String[] {
                    "åäöÅÄÖ",
                    "åæøÅÆØ",
                    "áÁéÉíÍóÓúÚýÝþÞðÐöÖ",
                    "None of the above"
                }),
            quizzes[2]);
    }

    private void assertQuizDto(QuizDto expectedDto, QuizDto actualDto) {
        assertEquals(expectedDto.getCategory(), actualDto.getCategory());
        assertEquals(expectedDto.getType(), actualDto.getType());
        assertEquals(expectedDto.getDifficulty(), actualDto.getDifficulty());
        assertEquals(expectedDto.getQuestion(), actualDto.getQuestion());
        assertEquals(expectedDto.getCorrectAnswer(), actualDto.getCorrectAnswer());

        assertArrayEquals(expectedDto.getIncorrectAnswers(), actualDto.getIncorrectAnswers());
    }

    private void assertQuiz(Quiz expectedQuiz, Quiz actualQuiz) {
        assertEquals(expectedQuiz.getQuestion(), actualQuiz.getQuestion());
        assertEquals(expectedQuiz.getCorrectAnswer(), actualQuiz.getCorrectAnswer());

        for (String choice: expectedQuiz.getChoices()) {
            assertTrue(Arrays.asList(actualQuiz.getChoices()).contains(choice));
        }
    }

    private IQuizApiController createFakeQuizApiController(String testFilePath) {
        return (amount, category) -> {
            try {
                String jsonString = Files.readString(Path.of(testFilePath));
                return new Gson().fromJson(jsonString, QuizRootDto.class);
            } catch (IOException e) {
                throw new HttpRequestException("Something fake happened", e);
            }
        };
    }
}
