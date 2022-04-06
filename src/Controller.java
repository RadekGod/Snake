import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private final Model model;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    private boolean isPaused = false;
    public boolean cancelTimer = false;
    private int previousMove = RIGHT;
    private int presentMove = RIGHT;
    Timer timer;

    public Controller(Model model, View view) throws FileNotFoundException {
        ViewListener viewListener = new ViewListener();
        view.setKeyListener(viewListener);
        this.model = model;
        setNewTimer();

        model.startGame();
    }
    public void setNewTimer() {
        timer = new Timer();
        switch (model.getDifficulty()) {
            case 1 -> timer.scheduleAtFixedRate(timerTask, 1000, 150);
            case 2 -> timer.scheduleAtFixedRate(timerTask, 1000, 100);
            case 3 -> timer.scheduleAtFixedRate(timerTask, 1000, 75);
            case 4 -> timer.scheduleAtFixedRate(timerTask, 1000, 50);
        }
    }
    public void callSnakeMove() throws IOException, InterruptedException {
        if (!isPaused) {
            switch (presentMove) {
                case UP -> model.snakeMove(UP);
                case DOWN -> model.snakeMove(DOWN);
                case LEFT -> model.snakeMove(LEFT);
                case RIGHT -> model.snakeMove(RIGHT);
            }
        }
    }
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                if (cancelTimer) {
                    cancelTimer = false;
                    timer.cancel();
                    timer.purge();
                } else {
                    callSnakeMove();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void analyzeInput(KeyEvent keyEvent) throws InterruptedException {


            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (presentMove != UP && presentMove != DOWN) {
                        previousMove = presentMove;
                        presentMove = UP;

                    }

                    break;
                case KeyEvent.VK_LEFT:
                    if (presentMove != LEFT && presentMove != RIGHT) {
                        previousMove = presentMove;
                        presentMove = LEFT;
                    }

                    break;
                case KeyEvent.VK_DOWN:
                    if (presentMove != DOWN && presentMove != UP) {
                        previousMove = presentMove;
                        presentMove = DOWN;
                    }

                    break;
                case KeyEvent.VK_RIGHT:
                    if (presentMove != RIGHT && presentMove != LEFT) {
                        previousMove = presentMove;
                        presentMove = RIGHT;
                    }
                    break;
                case KeyEvent.VK_P:
                    isPaused = !isPaused;
        }

    }

    public void setPresentMove(int presentMove) {
        this.presentMove = presentMove;
    }

    public void setPreviousMove(int previousMove) {
        this.previousMove = previousMove;
    }

    class ViewListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            try {
                analyzeInput(e);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
