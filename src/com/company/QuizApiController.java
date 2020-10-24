package com.company;

import com.google.gson.Gson;
import com.sun.jdi.request.ExceptionRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QuizApiController implements IQuizApiController{
    private final HttpClient client;

    public QuizApiController() {
        client = HttpClient.newHttpClient();
    }

    public QuizRootResponseModel getQuizzes(int amount, int category, String type) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://opentdb.com/api.php?amount=%s&category=%s&type=%s", amount, category, type)))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Status code was other than '200' (OK)");
        }

        return deserializeJson(response.body());
    }

    private QuizRootResponseModel deserializeJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, QuizRootResponseModel.class);
    }
}
