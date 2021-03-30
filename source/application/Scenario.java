package application;

import game.*;

import javax.swing.*;
import java.io.IOException;

import static configuration.GameParameters.*;
import static configuration.Constants.*;

/**
 * wątek realizujący kolejne etapy gry i generację kolejnych poziomów zgodnie ze scenariuszem
 */
public class Scenario implements Runnable{
    public GameWindow gameWindow;
    //private GameDialogs gameDialogs;
    private String nick;
    private int lives;
    private int totalpoints;
    private boolean gameEnd;
    private int i;


    /**
     * uruchomienie gry
     */
    public void run() {
        gameWindow = new GameWindow("Lunar Lander");
        gameWindow.setIconImage(new ImageIcon("resources\\lander.png").getImage());
        while(true) {
            if(resetGame()) return;

            for (i = 1; i <= LEVELS; i++) {
                gameWindow.setTitle("Poziom " + i + " – Lunar Lander. Jakub Nerć");
                startLevel(i);
                switch (checkStatus()) {
                    case WIN:
                        if (levelWin()) return;
                        break;
                    case LOSE:
                        if (levelFail()) return;
                        break;
                    case MENU:
                        backToMenu();
                        return;
                    default:
                        System.out.println("Coś poszło nie tak");
                }
            }
            if (gameWin()) return;
        }
    }


    /**
     * rozpoczęcie generacji poziomu
     * @param levelNumber numer poziomu
     */
    private void startLevel(int levelNumber) {
        gameWindow.gameDialogs.setLevelNumber(levelNumber);
        gameWindow.gameDialogs.setLives(lives);

        SwingUtilities.invokeLater(new Runnable() {
            private int anonymousLevelNumber;
            @Override
            public void run() {
                try {
                    gameWindow.startLevel(anonymousLevelNumber, lives);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            private Runnable init(int levelNumber) {
                anonymousLevelNumber = levelNumber;
                return this;
            }
        }.init(levelNumber));
    }


    /**
     * generacja wątku sprawdzającego stan gry
     * @return stan gry
     */
    private int checkStatus() {
        GameStatus gs = new GameStatus(gameWindow);
        Thread t = new Thread(gs);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return gs.getStatus();
    }


    /**
     * czynności do wykonania w przypadku wygrania całej gry
     * @return true - poziom wygrany, false - niewygrany
     */
    private boolean levelWin() {
        gameWindow.remove(gameWindow.getGlp());
        lives = gameWindow.getLives();
        int points = gameWindow.getPoints();
        totalpoints = totalpoints + points;
        gameWindow.gameDialogs.setPoints(points);
        gameWindow.gameDialogs.setTotalpoints(totalpoints);
        gameWindow.gameDialogs.setLives(lives);
        int o = gameWindow.gameDialogs.showDialog(GAMELEVELSUMMARY);

        if(o == JOptionPane.OK_OPTION) return false;
        else {
            backToMenu();
            return true;
        }
    }


    /**
     * czynności do wykonania w przypadku przegrania gry
     * @return true - poziom przegrany, false - poziom nieprzegrany
     */
    private boolean levelFail() {
        int o;
        gameWindow.remove(gameWindow.getGlp());



        if(lives > 1) {
            lives = gameWindow.getLives() - 1;
            gameWindow.gameDialogs.setLives(lives);
            o = gameWindow.gameDialogs.showDialog(GAMELEVELREPEAT);


            if(o == JOptionPane.OK_OPTION) {
                i -= 1;
                return false;
            }
            else {
                backToMenu();
                return true;
            }
        }
        else {
            o = gameWindow.gameDialogs.showDialog(GAMEOVER);


            if(o == JOptionPane.OK_OPTION) {
                resetGame();
                i = 0;
                return false;
            }
            else {
                backToMenu();
                return true;
            }
        }
    }


    /**
     * resetowanie parametrów gry, liczby żyć i punktów, tak by można ją było zacząć od początku
     * @return true - zakończenie gry i powrót do menu, false - rozpoczęcie gry od nowa
     */
    private boolean resetGame() {
        gameWindow.gameDialogs = new GameDialogs(gameWindow);
        gameWindow.gameDialogs.setNick(nick);
        int o = gameWindow.gameDialogs.showDialog(GAMESTART);


        if(o == JOptionPane.OK_OPTION) {
            gameEnd = false;
            i = 1;
            totalpoints = 0;
            lives = LIVES_MAX;
            gameWindow.gameDialogs.setTotalpoints(totalpoints);
            gameWindow.gameDialogs.setLives(lives);
            gameWindow.gameDialogs.setPoints(0);
            return false;
        }
        else{
            backToMenu();
            return true;
        }

    }


    /**
     * czynności do wykonania w przypadku wygrania gry
     * @return true - powrót do menu, false - rozpoczęcie gry od nowa
     */
    private boolean gameWin() {
        Results r = new Results();
        int p = r.checkResult(nick, totalpoints);
        if(p != -1) {
            gameWindow.gameDialogs.setBest(true);
            gameWindow.gameDialogs.setRank(p);
        }
        else {
            gameWindow.gameDialogs.setBest(false);
            gameWindow.gameDialogs.setRank(-1);
        }

        int o = gameWindow.gameDialogs.showDialog(GAMEWIN);


        if(o == JOptionPane.OK_OPTION) {
            return false;
        }
        else {
            backToMenu();
            return true;
        }
    }

    /**
     * powrót do menu główneo
     */
    private void backToMenu() {
        gameEnd = true;
        gameWindow.setVisible(false);
        gameWindow.dispose();
    }

    /**
     * ustawienie nazwy użytkownika
     * @param nick nazwa użytkownika
     */
    public void setNick(String nick) {
        this.nick = nick;
    }
}
