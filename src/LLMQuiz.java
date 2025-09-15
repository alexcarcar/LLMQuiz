/* Quiz on LLMs */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LLMQuiz {

    static Scanner scanner = new Scanner(System.in);
    static int score = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the LLM Quiz!");
        System.out.println("Test your knowledge on Large Language Models.\n");

        List<Question> questions = generateQuestions();
        Collections.shuffle(questions);

        for (Question q : questions) {
            q.ask();
        }

        System.out.println("\nQuiz Complete!");
        System.out.println("Your final score: " + score + " out of " + questions.size());
    }

    public static List<Question> generateQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new MultipleChoice(
                "What does LLM stand for?",
                new String[]{"Large Language Model", "Logical Learning Machine", "Long Lexical Memory", "Linguistic Learning Module"},
                0
        ));

        questions.add(new MultipleChoice(
                "Which dataset is known for curating high-quality web text for LLMs?",
                new String[]{"Common Crawl", "FineWeb", "WikiDump", "OpenText"},
                1
        ));

        questions.add(new TrueFalse(
                "Pretraining involves downloading and preprocessing internet-scale text data.",
                true
        ));

        questions.add(new FillInBlank(
                "The process of breaking text into smaller units for model processing is called ________.",
                "tokenization"
        ));

        questions.add(new MultipleChoice(
                "FineWeb-Edu is a subset of FineWeb focused on what type of content?",
                new String[]{"Medical", "Educational", "Legal", "Financial"},
                1
        ));

        questions.add(new TrueFalse(
                "LLMs are trained to predict the next sentence in a paragraph.",
                false
        ));

        questions.add(new FillInBlank(
                "The open repository often used to source web data for LLMs is called ________.",
                "Common Crawl"
        ));

        questions.add(new MultipleChoice(
                "Which of the following is NOT a typical step in pretraining?",
                new String[]{"Tokenization", "Deduplication", "Image classification", "Filtering"},
                2
        ));

        return questions;
    }

    abstract static class Question {
        String prompt;

        Question(String prompt) {
            this.prompt = prompt;
        }

        abstract void ask();
    }

    static class MultipleChoice extends Question {
        String[] options;
        int correctIndex;

        MultipleChoice(String prompt, String[] options, int correctIndex) {
            super(prompt);
            this.options = options;
            this.correctIndex = correctIndex;
        }

        void ask() {
            System.out.println(prompt);
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }

            int answer = -1;
            while (true) {
                System.out.print("Your answer (1-" + options.length + "): ");
                String input = scanner.nextLine().trim();
                try {
                    answer = Integer.parseInt(input);
                    if (answer >= 1 && answer <= options.length) break;
                    else System.out.println("Please enter a number between 1 and " + options.length + ".");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            if (answer - 1 == correctIndex) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("X Incorrect. Correct answer: " + options[correctIndex] + "\n");
            }
        }
    }

    static class TrueFalse extends Question {
        boolean correctAnswer;

        TrueFalse(String prompt, boolean correctAnswer) {
            super(prompt);
            this.correctAnswer = correctAnswer;
        }

        void ask() {
            System.out.println(prompt + " (true/false)");
            String answer;
            while (true) {
                System.out.print("Your answer: ");
                answer = scanner.nextLine().trim().toLowerCase();
                if (answer.equals("true") || answer.equals("false")) break;
                else System.out.println("Please enter 'true' or 'false'.");
            }

            boolean userAnswer = answer.equals("true");
            if (userAnswer == correctAnswer) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("X Incorrect. Correct answer: " + correctAnswer + "\n");
            }
        }
    }

    static class FillInBlank extends Question {
        String correctAnswer;

        FillInBlank(String prompt, String correctAnswer) {
            super(prompt);
            this.correctAnswer = correctAnswer.toLowerCase();
        }

        void ask() {
            System.out.println(prompt);
            System.out.print("Your answer: ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals(correctAnswer)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("X Incorrect. Correct answer: " + correctAnswer + "\n");
            }
        }
    }
}
