package quiz;

interface IQuizApiController {
    /**
     * Fetches quizzes over HTTP
     * @param amount amount of quizzes
     * @return Quiz root dto
     * @throws HttpRequestException if the the request failed due to an underlying cause
     * @throws HttpResponseNotOkException if request responded with status code other than '200'
     */
    QuizRootDto getQuizzes(int amount) throws HttpRequestException, HttpResponseNotOkException;
}
