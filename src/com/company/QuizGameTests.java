package com.company;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuizGameTests {

    @Test
    void apiControllerGetQuizzesReturnsCorrectObject() throws Exception {
        // Arrange
        IQuizApiController fakeController = createFakeQuizApiController("testData/test_response.json");

        // Act
        QuizRootDto responseQuizRoot = fakeController.getQuizzes(0, 0); // <- the arguments are ignored
        QuizDto[] responseQuizzes = responseQuizRoot.getResults();

        // Assert
        assertEquals(0, responseQuizRoot.getResponseCode());

        // Quiz 1
        assertQuizDto(responseQuizzes[0],
                "Science%3A%20Computers",
                "multiple",
                "medium",
                "Which%20programming%20language%20was%20developed%20by%20Sun%20Microsystems%20in%201995%3F",
                "Java",
                // Incorrect answers
                "Python",
                "Solaris%20OS",
                "C%2B%2B");

        // Quiz 2
        assertQuizDto(responseQuizzes[1],
                "Science%3A%20Computers",
                "multiple",
                "hard",
                "According%20to%20DeMorgan's%20Theorem%2C%20the%20Boolean%20expression%20(AB)'%20is%20equivalent%20to%3A",
                "A'%20%2B%20B'",
                // Incorrect answers
                "A'B%20%2B%20B'",
                "A'B'",
                "AB'%20%2B%20AB");

        // Quiz 3
        assertQuizDto(responseQuizzes[2],
                "Science%3A%20Computers",
                "multiple",
                "hard",
                "!%22#%C2%A4%25&/()=?%C2%B4%60%5E*@%C2%A3$%E2%82%AC%7B%5B%5D%7D%5C~%C3%A5%C3%A4%C3%B6%C3%85%C3%84%C3%96",
                "None%20of%20the%20above",
                // Incorrect answers
                "%C3%A5%C3%A6%C3%B8%C3%85%C3%86%C3%98",
                "%C3%A1%C3%81%C3%A9%C3%89%C3%AD%C3%8D%C3%B3%C3%93%C3%BA%C3%9A%C3%BD%C3%9D%C3%BE%C3%9E%C3%B0%C3%90%C3%B6%C3%96",
                "%C3%A5%C3%A4%C3%B6%C3%85%C3%84%C3%96");
    }

    @Test
    void quizProviderGetQuizzesReturnsCorrectObject() throws Exception {
        // Arrange
        IQuizApiController fakeController = createFakeQuizApiController("testData/test_response.json");
        var provider = new QuizProvider(fakeController);

        // Act
        Quiz[] quizzes = provider.getQuizzes(0, 0); // <- the arguments are ignored

        // Assert
        assertQuiz(quizzes[0],
                "Which programming language was developed by Sun Microsystems in 1995?",
                "Java",
                // Choices
                "Java",
                "Python",
                "Solaris OS",
                "C++");

        assertQuiz(quizzes[1],
                "According to DeMorgan's Theorem, the Boolean expression (AB)' is equivalent to:",
                "A' + B'",
                // Choices
                "A' + B'",
                "A'B + B'",
                "A'B'",
                "AB' + AB");

        assertQuiz(quizzes[2],
                "!\"#¤%&/()=?´`^*@£$€{[]}\\~åäöÅÄÖ",
                "None of the above",
                // Choices
                "åäöÅÄÖ",
                "åæøÅÆØ",
                "áÁéÉíÍóÓúÚýÝþÞðÐöÖ",
                "None of the above");
    }

    private void assertQuizDto(QuizDto response, String... expectedValues) {
        assertEquals(expectedValues[0], response.getCategory());
        assertEquals(expectedValues[1], response.getType());
        assertEquals(expectedValues[2], response.getDifficulty());
        assertEquals(expectedValues[3], response.getQuestion());
        assertEquals(expectedValues[4], response.getCorrectAnswer());

        assertEquals(expectedValues[5], response.getIncorrectAnswers()[0]);
        assertEquals(expectedValues[6], response.getIncorrectAnswers()[1]);
        assertEquals(expectedValues[7], response.getIncorrectAnswers()[2]);
    }

    private void assertQuiz(Quiz quiz, String... expectedValues) {
        assertEquals(expectedValues[0], quiz.getQuestion());
        assertEquals(expectedValues[1], quiz.getCorrectAnswer());

        for (int i = 2; i <= 5; i++) {
            String expectedChoice = expectedValues[i];
            assertTrue(Arrays.asList(quiz.getChoices()).contains(expectedChoice));
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
