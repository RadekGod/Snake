import javax.swing.*;
import java.awt.*;
import java.util.Deque;

public class GamePanel extends JPanel {
    private final int width;
    private final int height;
    private final int scale;
    private Deque<Point> snakeBody;
    private Point appleCords;
    Graphics2D g2D;
    public GamePanel(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        setSize(width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        g2D = (Graphics2D) g;
        //g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        paintGamePanel();
        paintSnake();
        paintApple();
        paintLines();
    }

    public void setSnakeBody(Deque<Point> snakeBody) {
        this.snakeBody = snakeBody;
    }
    public void setAppleCords(Point appleCords) {
        this.appleCords = appleCords;
    }

    public void paintGamePanel() {
        //kolorowanie pola
        g2D.setColor(new Color(98, 129, 161));
        g2D.fillRect(0, 40, width, height - scale);

        // kolorowanie przeszkod
        for (int i = 0; i < width; i += scale) {
            for (int j = 40; j < height; j += scale) {
                if (i == 0 || j == 40 || i == height - scale || j == width - scale) {
                    g2D.setColor(new Color(38, 42, 46));
                    g2D.fillRect(i, j, scale, scale);
                }
            }
        }
        // generacja linii

    }
    public void paintLines() {
        for (int i = 0; i <= width; i += scale) {
            g2D.setColor(new Color(21, 83, 148));
            g2D.drawLine(i, 40, i, height);
            if (i >= scale) {
                g2D.drawLine(0, i, width, i);
            }
        }
    }
    public void paintSnake() {
        if (snakeBody != null && snakeBody.size() != 0) {
            int xHead = (int) snakeBody.getFirst().getX();
            int yHead = (int) snakeBody.getFirst().getY();
            snakeBody.removeFirst();
            g2D.setColor(Color.BLACK);
            g2D.fillRect(xHead, yHead, scale, scale);
            g2D.setColor(Color.GREEN);
            for (Point point : snakeBody) {
                int x = (int) point.getX();
                int y = (int) point.getY();
                g2D.fillRect(x, y, scale, scale);
            }
        }
    }
    public void paintApple() {
        if (appleCords != null) {
            g2D.setColor(Color.RED);
            int xApple = (int) appleCords.getX();
            int yApple = (int) appleCords.getY();
            g2D.fillRect(xApple, yApple, scale, scale);
        }
    }
}
