import javax.swing.*;
import java.awt.event.KeyListener;

public class View extends JFrame {
    private GamePanel gamePanel;
    private HeaderPanel headerPanel;
    public void setKeyListener(KeyListener keyListener) {
        addKeyListener(keyListener);
    }
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public void setHeaderPanel(HeaderPanel headerPanel) {
        this.headerPanel = headerPanel;
    }

    public View(int width, int height, GamePanel gamePanel, HeaderPanel headerPanel, Model model) {
        model.setView(this);
        setSize(width, height);
        setTitle("Snake");
        setResizable(true);
        add(headerPanel);
        add(gamePanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
