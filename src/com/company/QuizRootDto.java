package com.company;

import com.google.gson.annotations.SerializedName;

/**
 * The quiz root dto
 */
public class QuizRootDto {
    @SerializedName("response_code")
    private int responseCode;
    private QuizDto[] results;

    // Instantiated by reflection
    private QuizRootDto() {

    }

    public int getResponseCode() {
        return responseCode;
    }

    public QuizDto[] getResults() {
        return results;
    }
}
