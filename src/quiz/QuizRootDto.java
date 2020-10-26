package quiz;

import com.google.gson.annotations.SerializedName;

/**
 * The quiz root dto
 */
class QuizRootDto {
    @SerializedName("response_code")
    private final int responseCode;
    private final QuizDto[] results;

    QuizRootDto(int responseCode, QuizDto[] results) {
        this.responseCode = responseCode;
        this.results = results;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public QuizDto[] getResults() {
        return results;
    }
}
