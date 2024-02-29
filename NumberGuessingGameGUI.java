import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private JTextField guessField;
    private JLabel feedbackLabel, scoreLabel;
    private JButton guessButton, playAgainButton;
    private int minRange = 1;
    private int maxRange = 100;
    private int attemptsLimit = 5;
    private int score = 0;
    private Random random = new Random();
    private int randomNumber;
    private int attempts;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setLayout(new GridLayout(6, 1));

        feedbackLabel = new JLabel("Guess a number between " + minRange + " and " + maxRange);
        add(feedbackLabel);

        guessField = new JTextField(10);
        add(guessField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());
        add(guessButton);

        scoreLabel = new JLabel("Score: " + score);
        add(scoreLabel);

        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(new PlayAgainButtonListener());
        playAgainButton.setEnabled(false); // Enable after the game ends
        add(playAgainButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);

        startNewGame();
    }

    private void startNewGame() {
        randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;
        attempts = 0;
        guessField.setText("");
        feedbackLabel.setText("Guess a number between " + minRange + " and " + maxRange);
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            int guess;
            try {
                guess = Integer.parseInt(guessField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                return;
            }

            attempts++;

            if (guess == randomNumber) {
                JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number correctly in " + attempts + " attempts.");
                score += attemptsLimit - attempts + 1;
                scoreLabel.setText("Score: " + score);
                guessButton.setEnabled(false);
                playAgainButton.setEnabled(true);
            } else if (guess < randomNumber) {
                feedbackLabel.setText("Too low! Try again.");
            } else {
                feedbackLabel.setText("Too high! Try again.");
            }

            if (attempts >= attemptsLimit) {
                JOptionPane.showMessageDialog(null, "Sorry, you've run out of attempts. The correct number was: " + randomNumber);
                guessButton.setEnabled(false);
                playAgainButton.setEnabled(true);
            }

            guessField.setText("");
        }
    }

    private class PlayAgainButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            startNewGame();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGameGUI();
            }
        });
    }
}
