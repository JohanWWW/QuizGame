package quiz;

interface IQuizApiController {
    /**
     * Fetches quizzes over HTTP
     * @param amount amount of quizzes
     * @param category the category of the question
     * @return Quiz root dto
     * @throws HttpRequestException if the the request failed due to an underlying cause
     * @throws HttpResponseNotOkException if request responded with status code other than '200'
     */
    QuizRootDto getQuizzes(int amount, int category) throws HttpRequestException, HttpResponseNotOkException;
}
