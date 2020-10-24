package com.company;

public class HttpResponseNotOkException extends Exception {

    public HttpResponseNotOkException(int statusCode) {
        super(String.format("Response status code: %s", statusCode));
    }
}
