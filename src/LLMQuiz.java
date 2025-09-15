/* Quiz on LLMs */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LLMQuiz {

    // Define a Scanner for user input based on System.in
    static Scanner scanner = new Scanner(System.in);

    // Static int to score (only need one instance, hence it is static)
    static int score = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the LLM Quiz!");
        System.out.println("Test your knowledge on Large Language Models.\n");

        // Get a list of questions and shuffle them
        List<Question> questions = generateQuestions();
        Collections.shuffle(questions); // Randomize question order

        // Ask all the questions
        for (Question q : questions) {
            q.ask();
        }

        // Print the results
        System.out.println("\nQuiz Complete!");
        System.out.println("Your final score: " + score + " out of " + questions.size());
    }

    // Question bank
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
                false // They predict the next token, not full sentences
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

    // Abstract base class
    abstract static class Question {
        String prompt;

        Question(String prompt) {
            this.prompt = prompt;
        }

        abstract void ask();
    }

    // Multiple Choice Question
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
            System.out.print("Your answer (1-" + options.length + "): ");
            int answer = scanner.nextInt();
            if (answer - 1 == correctIndex) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("X Incorrect. Correct answer: " + options[correctIndex] + "\n");
            }
        }
    }

    // True/False Question
    static class TrueFalse extends Question {
        boolean correctAnswer;

        TrueFalse(String prompt, boolean correctAnswer) {
            super(prompt);
            this.correctAnswer = correctAnswer;
        }

        void ask() {
            System.out.println(prompt + " (true/false)");
            String answer = scanner.next().toLowerCase();
            boolean userAnswer = answer.equals("true");
            if (userAnswer == correctAnswer) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("X Incorrect. Correct answer: " + correctAnswer + "\n");
            }
        }
    }

    // Fill-in-the-Blank Question
    static class FillInBlank extends Question {
        String correctAnswer;

        FillInBlank(String prompt, String correctAnswer) {
            super(prompt);
            this.correctAnswer = correctAnswer.toLowerCase();
        }

        void ask() {
            System.out.println(prompt);
            System.out.print("Your answer: ");
            String answer = scanner.next().toLowerCase();
            if (answer.equals(correctAnswer)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("X Incorrect. Correct answer: " + correctAnswer + "\n");
            }
        }
    }
}
