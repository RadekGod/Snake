import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HeaderPanel extends JPanel {
    private final int width;
    private final int height;
    Graphics2D g2D;
    JLabel currentScoreLabel;
    JLabel highScoreLabel;
    JLabel difficultyLabel;
    public JLabel getCurrentScoreLabel() {
        return currentScoreLabel;
    }

    public JLabel getDifficultyLabel() {
        return difficultyLabel;
    }

    public JLabel getHighScoreLabel() {
        return highScoreLabel;
    }

    public HeaderPanel(int width, int height, Model model) {
        this.width = width;
        this.height = height;
        setSize(width, height);
        highScoreLabel = new JLabel("highscore");
        highScoreLabel.setForeground(Color.WHITE);
        currentScoreLabel = new JLabel("Wynik: ");
        currentScoreLabel.setForeground(Color.WHITE);
        difficultyLabel = new JLabel();
        difficultyLabel.setForeground(Color.WHITE);
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            try {
                model.restartGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        resetButton.setFocusable(false);
        add(highScoreLabel);
        add(currentScoreLabel);
        add(difficultyLabel);
        add(resetButton);
        setVisible(true);
        //setBackground(Color.BLACK);
    }
    @Override
    public void paintComponent(Graphics g) {
        g2D = (Graphics2D) g;
        //g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        paintHeaderPanel();
    }

    public void paintHeaderPanel() {
        //System.out.println("siema header");
        g2D.setColor(Color.BLACK);
        g2D.fillRect(0,0, width, height);
    }
}
