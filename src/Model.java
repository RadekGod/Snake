import java.awt.*;
import java.io.*;
import java.util.*;

public class Model {
    private final int gameWidth = 760;
    private final int gameHeight = 760;
    private int difficulty;
    private int applesEaten = 0;

    private int highScore;
    private int currentScore = 0;
    private final int scale = 40;
    private final ArrayDeque<Point> snakeBody = new ArrayDeque<>();
    private Point appleCords;
    private View view;
    private final HeaderPanel headerPanel;
    private final GamePanel gamePanel;
    Controller controller;

    public int getDifficulty() {
        return difficulty;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public View getView() {
        return view;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Model(int difficulty) throws FileNotFoundException {
        this.difficulty = difficulty;
        int headerWidth = 761;
        int headerHeight = 40;
        this.headerPanel = new HeaderPanel(headerWidth, headerHeight, this);
        this.gamePanel = new GamePanel(gameWidth, gameHeight, scale);
        int frameWidth = 777;
        int frameHeight = 800;
        view = new View(frameWidth, frameHeight, gamePanel, headerPanel, this);
        controller = new Controller(this, view);
    }

    public void startGame() throws FileNotFoundException {
        File file = new File("highscore.txt");
        if (file.exists()) {
            Scanner scanner = new Scanner(new File("highscore.txt"));
            if (scanner.hasNextLine()) {
                try {
                    highScore = scanner.nextInt();
                } catch (Exception ignored) {
                }

                System.out.println("highscore: " + highScore);
            }
        }
        applesEaten = 0;
        generateSnakeInTheMiddle();
        appleCords = generateApple();
        setDifficultyText();
        updateScore();
    }
    public void restartGame() throws IOException {
        controller.setPreviousMove(Controller.RIGHT);
        controller.setPresentMove(Controller.RIGHT);
        saveHighScore();
        startGame();
        controller.setPaused(false);
    }

    public void setDifficultyText() {
        switch (difficulty) {
            case 1 -> headerPanel.getDifficultyLabel().setText("Poziom trudnośc: Łatwy");
            case 2 -> headerPanel.getDifficultyLabel().setText("Poziom trudnośc: Średni");
            case 3 -> headerPanel.getDifficultyLabel().setText("Poziom trudnośc: Trudny");
            case 4 -> headerPanel.getDifficultyLabel().setText("Poziom trudnośc: Koszmar");
        }
    }

    public void saveHighScore() throws IOException {
        controller.setPaused(true);
        BufferedWriter out = new BufferedWriter(new FileWriter("highscore.txt"));
        out.write(Integer.toString(highScore));
        out.flush();
        out.close();

    }
    public void snakeGrow() {
        int newX = (int) snakeBody.getLast().getX();
        int newY = (int) snakeBody.getLast().getY();
        snakeBody.addLast(new Point(newX, newY));
        applesEaten++;
        updateScore();
    }
    public void updateScore() {
        switch (difficulty) {
            case 1 -> currentScore = applesEaten * 3;
            case 2 -> currentScore = applesEaten * 5;
            case 3 -> currentScore = applesEaten * 15;
            case 4 -> currentScore = applesEaten * 40;
        }
        System.out.println(currentScore);
        if (currentScore > highScore) {
            highScore = currentScore;
        }
        headerPanel.getCurrentScoreLabel().setText("Wynik: " + currentScore);
        headerPanel.getHighScoreLabel().setText("Rekord: " + highScore);
    }
    public void controlSnakeCords() throws IOException, InterruptedException {
        int headX = (int) snakeBody.getFirst().getX();
        int headY = (int) snakeBody.getFirst().getY();
        ArrayDeque<Point> snakeBodyWithoutHead = snakeBody.clone();
        snakeBodyWithoutHead.removeFirst();
        if (headX == appleCords.getX() && headY == appleCords.getY()) {
            snakeGrow();
            appleCords = generateApple();
        } else if (headX == 0 || headX == 720 || headY == 40 || headY == 720 || snakeBodyWithoutHead.contains(new Point(headX, headY))) {
            synchronized (this) {
                EndGameScreen endGameScreen = new EndGameScreen(currentScore, this);
            }
            saveHighScore();
        }
    }
    public void generateSnakeInTheMiddle() {
        snakeBody.clear();
        int snakeHeadX = gameWidth / 2 - 20;
        int snakeHeadY = gameHeight / 2 - 20;
        int initialSnakeLength = 5;
        for (int i = 0; i < initialSnakeLength; i++) {
            snakeBody.add(new Point(snakeHeadX, snakeHeadY));
            snakeHeadX -= scale;
        }
        gamePanel.setSnakeBody(snakeBody.clone());
    }


    public void snakeMove(int direction) throws IOException, InterruptedException {
        int snakeHeadX = (int) snakeBody.getFirst().getX();
        int snakeHeadY = (int) snakeBody.getFirst().getY();

        switch (direction) {
            case Controller.UP -> {
                snakeBody.removeLast();
                snakeBody.addFirst(new Point(snakeHeadX, snakeHeadY - scale));
            }
            case Controller.DOWN -> {
                snakeBody.removeLast();
                snakeBody.addFirst(new Point(snakeHeadX, snakeHeadY + scale));
            }
            case Controller.RIGHT -> {
                snakeBody.removeLast();
                snakeBody.addFirst(new Point(snakeHeadX + scale, snakeHeadY));
            }
            case Controller.LEFT -> {
                snakeBody.removeLast();
                snakeBody.addFirst(new Point(snakeHeadX - scale, snakeHeadY));
            }
        }
        ArrayDeque<Point> snakeBodyCopy = snakeBody.clone();
        controlSnakeCords();
        gamePanel.setSnakeBody(snakeBodyCopy);
        synchronized (this) {
            gamePanel.repaint();
            headerPanel.repaint();
        }
    }
    public Point generateApple() {
        Point appleCords;
        Random random = new Random();
        int appleX;
        int appleY;
        do {
            appleX = (random.nextInt(17) + 1) * scale;
            appleY = (random.nextInt(16) + 2) * scale;
        } while (snakeBody.contains(new Point(appleX, appleY)));
        appleCords = new Point(appleX, appleY);
        gamePanel.setAppleCords(appleCords);
        return appleCords;
    }


}
