package com.company;

import java.util.Scanner;

public class QuizGame {
    private final QuizProvider provider;
    private final Scanner scanner;
    private int score;

    public QuizGame() {
        provider = new QuizProvider(new QuizApiController());
        scanner = new Scanner(System.in);
    }

    public void start() {
        int amountOfQuizzes = tryPromptInteger("Enter amount of quizzes: ", "Only integers allowed!");

        Quiz[] quizzes;
        try {
            quizzes = provider.getQuizzes(amountOfQuizzes, 18);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (Quiz quiz: quizzes) {
            System.out.println(quiz.getQuestion());
            for (int i = 0; i < quiz.getChoices().length; i++) {
                String choice = quiz.getChoices()[i];
                System.out.printf("[%s]: %s\n", i, choice);
            }

            Integer choiceIndex = null;
            while (choiceIndex == null || choiceIndex < 0 || choiceIndex >= 4) {
                choiceIndex = tryPromptInteger("Please enter a choice: ", "Only integers allowed");
                if (choiceIndex < 0 || choiceIndex >= 4) {
                    System.out.println("Only integers between 0 and 3 allowed");
                }
            }

            if (quiz.getChoices()[choiceIndex].equals(quiz.getCorrectAnswer())) {
                score++;
                System.out.println("You answered correctly!");
            } else {
                System.out.printf("Your answer was incorrect. Correct answer is: %s\n", quiz.getCorrectAnswer());
            }
            System.out.println();
        }

        System.out.printf("Score: %s/%s\n", score, quizzes.length);
    }

    private int tryPromptInteger(String message, String messageOnFail) {
        Integer value = null;
        while (value == null) {
            try {
                value = Integer.parseInt(prompt(message));
            } catch (NumberFormatException e) {
                System.out.println(messageOnFail);
            }
        }
        return value;
    }

    private String readLine() {
        return scanner.nextLine();
    }

    private String prompt(String message) {
        System.out.print(message);
        return readLine();
    }
}
