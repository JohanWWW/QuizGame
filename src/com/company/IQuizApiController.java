package com.company;

public interface IQuizApiController {
    QuizRootResponseModel getQuizzes(int amount, int category, String type) throws Exception;
}
