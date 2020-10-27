package com.company;

import quiz.QuizGameEngine;
import quiz.QuizProviderFailedException;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome!");

        int quizCount = promptUntilInteger("Please enter amount of quizzes: ", "Only integers allowed!");
        System.out.println();

        QuizGameEngine game = null;
        try {
            game = new QuizGameEngine(quizCount);
        } catch (QuizProviderFailedException e) {
            e.printStackTrace();
            System.exit(0);
        }

        while (!game.isGameOver()) {
            System.out.println(game.getQuestion());
            printChoices(game.getChoices());

            int choiceIndex = promptUntilIntegerRange(0, game.getChoices().length, "Please enter a choice: ");
            boolean answeredCorrectly = game.isCorrectAnswer(choiceIndex);

            System.out.println();
            System.out.printf("The correct answer is: \"%s\"\n", game.getCorrectAnswer());

            if (answeredCorrectly) System.out.println("You answered correctly!");
            else System.out.println("You answered incorrectly!");

            System.out.println();

            game.nextRound(answeredCorrectly);
        }

        System.out.printf("Score: %s of %s\n", game.getScore(), game.getQuizLength());

        scanner.close();
    }

    private static void printChoices(String[] choices) {
        for (int i = 0; i < choices.length; i++) {
            System.out.printf("[%s]: %s\n", i, choices[i]);
        }
    }

    private static int promptUntilInteger(String message, String messageOnFail) {
        Integer value = null;

         do {
            try {
                value = Integer.parseInt(prompt(message));
            } catch (NumberFormatException e) {
                System.out.println(messageOnFail);
            }
        } while (value == null);

        return value;
    }

    private static int promptUntilIntegerRange(int lower, int upper /*exclusive*/, String message) {
        boolean isInputAccepted = false;
        int value;

        do {
            value = promptUntilInteger(message, "Only integers allowed!");
            if (value >= lower && value < upper) {
                isInputAccepted = true;
            } else {
                System.out.printf("Value does not fall between %s and %s (exclusive)\n", lower, upper);
            }
        } while (!isInputAccepted);

        return value;
    }

    private static String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
