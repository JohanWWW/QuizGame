package com.company;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizGameTests {

    @Test
    void apiControllerGetQuizzesReturnsCorrectObject() throws Exception {
        // Arrange
        var fakeController = new FakeQuizApiController("testData/test_response.json");

        // Act
        QuizRootDto responseQuizRoot = fakeController.getQuizzes(0, 0);
        QuizDto[] responseQuizzes = responseQuizRoot.getResults();

        // Assert
        assertEquals(0, responseQuizRoot.getResponseCode());

        // Quiz 1
        assertQuizResponse(responseQuizzes[0],
                "Science: Computers",
                "multiple",
                "medium",
                "Which programming language was developed by Sun Microsystems in 1995?",
                "Java",
                // Incorrect answers
                "Python",
                "Solaris OS",
                "C++");

        // Quiz 2
        assertQuizResponse(responseQuizzes[1],
                "Science: Computers",
                "multiple",
                "hard",
                "According to DeMorgan&#039;s Theorem, the Boolean expression (AB)&#039; is equivalent to:",
                "A&#039; + B&#039;",
                // Incorrect answers
                "A&#039;B + B&#039;A",
                "A&#039;B&#039;",
                "AB&#039; + AB");
    }

    private void assertQuizResponse(QuizDto response, String... expectedValues) {
        assertEquals(expectedValues[0], response.getCategory());
        assertEquals(expectedValues[1], response.getType());
        assertEquals(expectedValues[2], response.getDifficulty());
        assertEquals(expectedValues[3], response.getQuestion());
        assertEquals(expectedValues[4], response.getCorrectAnswer());

        assertEquals(expectedValues[5], response.getIncorrectAnswers()[0]);
        assertEquals(expectedValues[6], response.getIncorrectAnswers()[1]);
        assertEquals(expectedValues[7], response.getIncorrectAnswers()[2]);
    }
}

class FakeQuizApiController implements IQuizApiController {
    private final String jsonString;

    public FakeQuizApiController(String testFilePath) throws IOException {
        jsonString = Files.readString(Path.of(testFilePath));
    }

    public QuizRootDto getQuizzes(int amount, int category) {
        return new Gson().fromJson(jsonString, QuizRootDto.class);
    }
}
