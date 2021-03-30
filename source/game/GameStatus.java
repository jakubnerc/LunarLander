package game;

import static configuration.Constants.*;

/**
 * wątek sprawdzający stan gry
 */
public class GameStatus implements Runnable {
    final GameWindow gameWindow;
    GameLevelAnimation glp;
    int status;
    public GameStatus(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        status = 0;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        synchronized (gameWindow) {
            try {
                gameWindow.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.glp = gameWindow.getGlp();
        while (!glp.end) {
            try {
                synchronized (glp) {
                    glp.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(glp.backToMenu) status = MENU;
        else {
            if(glp.fail) status = LOSE;
            if(glp.win) status = WIN;
        }
    }

    /**
     * zwracanie statusu gry
     * @return stan gry
     */
    public int getStatus() {
        return status;
    }
}
