package com.company;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Controller for fetching quizzes from server
 */
public class QuizApiController implements IQuizApiController {
    private final HttpClient client;

    public QuizApiController() {
        client = HttpClient.newHttpClient();
    }

    /**
     * Fetches quizzes from server
     * @param amount amount of quizzes
     * @param category the category of the question
     * @return Quiz root dto
     * @throws Exception if request failed, if failed to parse json
     */
    public QuizRootDto getQuizzes(int amount, int category) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://opentdb.com/api.php?amount=%s&category=%s&type=multiple", amount, category)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Status code was other than '200' (OK)");
        }

        return deserializeJson(response.body());
    }

    private QuizRootDto deserializeJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, QuizRootDto.class);
    }
}
