package com.company;

import quiz.QuizGameEngine;
import quiz.QuizProviderFailedException;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int quizCount = tryPromptInteger("Please enter amount of quizzes: ", "Only integers allowed!");

        QuizGameEngine game = null;
        try {
            game = new QuizGameEngine(quizCount, 18);
        } catch (QuizProviderFailedException e) {
            e.printStackTrace();
            System.exit(0);
        }

        while (!game.isGameOver()) {
            System.out.println(game.getQuestion());

            for (int i = 0; i < game.getChoices().length; i++) {
                System.out.printf("[%s]: %s\n", i, game.getChoices()[i]);
            }

            int choiceIndex = 0;
            boolean isInputAccepted = false;
            while (!isInputAccepted) {
                choiceIndex = tryPromptInteger("Please enter a choice: ", "Only integers allowed");
                if (choiceIndex < 0 || choiceIndex >= game.getChoices().length) {
                    System.out.println("Only integers between 0 and 3 allowed");
                    continue;
                }
                isInputAccepted = true;
            }

            boolean answeredCorrectly = game.isCorrectAnswer(choiceIndex);

            if (answeredCorrectly) {
                System.out.println("You answered correctly!");
            } else {
                System.out.println("You answered incorrectly!");
            }
            System.out.printf("The correct answer is: \"%s\"\n", game.getCorrectAnswer());

            game.nextRound(answeredCorrectly);
        }

        System.out.printf("Score: %s/%s\n", game.getScore(), game.getQuizLength());
    }

    private static int tryPromptInteger(String message, String messageOnFail) {
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

    private static String readLine() {
        return scanner.nextLine();
    }

    private static String prompt(String message) {
        System.out.print(message);
        return readLine();
    }
}
