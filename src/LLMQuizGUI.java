import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LLMQuizGUI {

    private int score = 0;
    private JFrame frame;
    private JPanel panel;
    private JTextArea questionArea;
    private JTextField answerField;
    private JButton submitButton;
    private JLabel feedbackLabel;
    private int currentIndex = 0;
    private List<Question> questions;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LLMQuizGUI().startQuiz());
    }

    public void startQuiz() {
        questions = generateQuestions();
        Collections.shuffle(questions);

        frame = new JFrame("LLM Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionArea = new JTextArea();
        questionArea.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);
        questionArea.setOpaque(false);
        questionArea.setFocusable(false);
        questionArea.setPreferredSize(new Dimension(500, 80));

        answerField = new JTextField();
        answerField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        answerField.setPreferredSize(new Dimension(500, 30));

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        submitButton.setBackground(new Color(100, 149, 237));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> handleTextAnswer());

        feedbackLabel = new JLabel("");
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        feedbackLabel.setForeground(Color.DARK_GRAY);
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feedbackLabel.setPreferredSize(new Dimension(500, 30));

        frame.add(panel);
        frame.setVisible(true);

        displayQuestion();
    }

    private void displayQuestion() {
        panel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        if (currentIndex >= questions.size()) {
            questionArea.setText("🎉 Quiz Complete! Final Score: " + score + " out of " + questions.size());
            gbc.gridy = 0;
            panel.add(questionArea, gbc);
            frame.pack();
            frame.setLocationRelativeTo(null);
            return;
        }

        Question q = questions.get(currentIndex);
        questionArea.setText(q.getPrompt());
        gbc.gridy = 0;
        panel.add(questionArea, gbc);

        if (q instanceof MultipleChoice mc) {
            for (int i = 0; i < mc.options.length; i++) {
                JButton optionButton = new JButton((i + 1) + ". " + mc.options[i]);
                optionButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
                optionButton.setBackground(new Color(230, 230, 250));
                optionButton.setPreferredSize(new Dimension(500, 30));
                int finalI = i;
                optionButton.addActionListener(e -> handleButtonAnswer(mc.checkAnswer(finalI), mc.getCorrectAnswer()));
                gbc.gridy++;
                panel.add(optionButton, gbc);
            }
        } else if (q instanceof TrueFalse tf) {
            JButton trueButton = new JButton("True");
            JButton falseButton = new JButton("False");

            for (JButton b : new JButton[]{trueButton, falseButton}) {
                b.setFont(new Font("SansSerif", Font.PLAIN, 14));
                b.setBackground(new Color(230, 230, 250));
                b.setPreferredSize(new Dimension(500, 30));
                gbc.gridy++;
                panel.add(b, gbc);
            }

            trueButton.addActionListener(e -> handleButtonAnswer(tf.checkAnswer("true"), tf.getCorrectAnswer()));
            falseButton.addActionListener(e -> handleButtonAnswer(tf.checkAnswer("false"), tf.getCorrectAnswer()));
        } else if (q instanceof FillInBlank) {
            gbc.gridy++;
            panel.add(answerField, gbc);
            gbc.gridy++;
            panel.add(submitButton, gbc);
            answerField.setText("");
        }

        gbc.gridy++;
        panel.add(feedbackLabel, gbc);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    private void handleTextAnswer() {
        String input = answerField.getText().trim();
        if (input.isEmpty()) {
            feedbackLabel.setText("⚠️ Please enter an answer.");
            return;
        }

        Question q = questions.get(currentIndex);
        boolean correct = q.checkAnswer(input);
        showFeedback(correct, q.getCorrectAnswer());
    }

    private void handleButtonAnswer(boolean correct, String correctAnswer) {
        showFeedback(correct, correctAnswer);
    }

    private void showFeedback(boolean correct, String correctAnswer) {
        if (correct) {
            score++; // ✅ Move this outside the Timer
            feedbackLabel.setText("✅ Correct!");
        } else {
            feedbackLabel.setText("❌ Incorrect. Correct answer: " + correctAnswer);
        }

        Timer timer = new Timer(correct ? 1000 : 3000, e -> {
            feedbackLabel.setText("");
            currentIndex++;
            displayQuestion();
        });
        timer.setRepeats(false);
        timer.start();
    }

    public List<Question> generateQuestions() {
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

    // Question classes
    abstract static class Question {
        String prompt;
        String correctAnswer;

        Question(String prompt, String correctAnswer) {
            this.prompt = prompt;
            this.correctAnswer = correctAnswer;
        }

        String getPrompt() {
            return prompt;
        }

        abstract boolean checkAnswer(String input);

        String getCorrectAnswer() {
            return correctAnswer;
        }
    }

    static class MultipleChoice extends Question {
        String[] options;
        int correctIndex;

        MultipleChoice(String prompt, String[] options, int correctIndex) {
            super(prompt, options[correctIndex]);
            this.options = options;
            this.correctIndex = correctIndex;
        }

        boolean checkAnswer(int index) {
            return index == correctIndex;
        }

        boolean checkAnswer(String input) {
            try {
                int choice = Integer.parseInt(input);
                return choice - 1 == correctIndex;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    static class TrueFalse extends Question {
        boolean correctBool;

        TrueFalse(String prompt, boolean correctBool) {
            super(prompt + " (true/false)", String.valueOf(correctBool));
            this.correctBool = correctBool;
        }

        boolean checkAnswer(String input) {
            return input.equalsIgnoreCase("true") == correctBool;
        }
    }

    static class FillInBlank extends Question {
        FillInBlank(String prompt, String correctAnswer) {
            super(prompt, correctAnswer.toLowerCase());
        }

        boolean checkAnswer(String input) {
            return input.trim().equalsIgnoreCase(correctAnswer);
        }
    }
}
