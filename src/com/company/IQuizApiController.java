package com.company;

public interface IQuizApiController {
    QuizRootDto getQuizzes(int amount, int category) throws Exception;
}
