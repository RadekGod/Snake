import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class EndGameScreen extends JFrame {
    public EndGameScreen(int playerScore, Model model) {
        JPanel labelPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        labelPanel.setSize(400, 50);
        buttonPanel.setSize(400, 100);
        JLabel playerScoreLabel = new JLabel("Koniec gry! Twój wynik to: " + playerScore);
        JButton resetButton = new JButton("Zagraj jeszcze raz!");
        resetButton.addActionListener((e) -> {
            try {
                System.out.println("dupa");
                this.dispose();
                model.restartGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        JButton difficultyButton = new JButton("Zmień poziom trudności");
        difficultyButton.addActionListener((e) -> {
            String[] options = {"Łatwy", "Średni", "Trudny", "Koszmar"};
            int choice = JOptionPane.showOptionDialog(this, "Wybierz poziom trudności", "Wybór", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]) + 1;
            model.setDifficulty(choice);
            model.controller.cancelTimer = true;
            try {
                model.setController(new Controller(model, model.getView()));
                model.restartGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            dispose();
        });
        JButton exitButton = new JButton("Wyjdź z gry");
        exitButton.addActionListener((e) -> System.exit(0));
        buttonPanel.setLayout(new FlowLayout());
        setLayout(new FlowLayout());
        labelPanel.add(playerScoreLabel);
        buttonPanel.add(resetButton);
        buttonPanel.add(difficultyButton);
        buttonPanel.add(exitButton);
        buttonPanel.setVisible(true);
        labelPanel.setVisible(true);
        add(labelPanel);
        add(buttonPanel);
        setSize(450, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
