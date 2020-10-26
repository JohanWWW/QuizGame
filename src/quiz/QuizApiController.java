package quiz;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Controller for fetching quizzes from server
 */
class QuizApiController implements IQuizApiController {
    private final HttpClient client;

    public QuizApiController() {
        client = HttpClient.newHttpClient();
    }

    /**
     * {@inheritDoc}
     */
    public QuizRootDto getQuizzes(int amount, int category) throws HttpRequestException, HttpResponseNotOkException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("https://opentdb.com/api.php?amount=%s&category=%s&type=multiple&encode=url3986", amount, category)))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new HttpRequestException("An error occurred when sending or receiving", e);
        } catch (InterruptedException e) {
            throw new HttpRequestException("The request was interrupted", e);
        }

        if (response.statusCode() != 200) {
            throw new HttpResponseNotOkException(response.statusCode());
        }

        return deserializeJson(response.body());
    }

    private QuizRootDto deserializeJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, QuizRootDto.class);
    }
}
