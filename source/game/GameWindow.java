package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Dimension2D;
import java.io.IOException;

import static configuration.Constants.GAMENEXTLEVEL;
import static configuration.GameParameters.*;

/**
 * Klasa okna gry, w którym rysuje się plansza
 */
public class GameWindow extends JFrame {
    /**
     * obiekt generujący okna dialogowe dla danego poziomu
     */
    public GameDialogs gameDialogs;

    /**
     * plansza, na której rysowana jest gra
     */
    private GameLevelAnimation glp;

    /**
     * rozmiar planszy, na której rysowana jest gra
     */
    public Dimension2D glpSize;

    /**
     * Konstruktor okna
     * @param title tytuł okna
     */
    public GameWindow(String title) {
        super(title);
        glpSize = PREFERRED_SIZE;
        gameDialogs = new GameDialogs(this);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            {
                glp.pauseGame();
            }
        });
    }

    /**
     * rozpoczęcie nowego poziomu
     * @param levelNumber numer poziomu
     * @param lives pozostała liczba żyć
     * @throws IOException w przypadku probelmów z przetwarzaniem plików
     */
    public void startLevel(int levelNumber, int lives) throws IOException {
        glp = new GameLevelAnimation(this, levelNumber, lives, glpSize);
        this.add(glp, BorderLayout.CENTER);
        glp.repaint();
        pack();
        setVisible(true);
        gameDialogs.showDialog(GAMENEXTLEVEL);
        this.addKeyListener(glp);
        glp.startAnimation();
        synchronized (this) {
            this.notify();
        }
    }

    public GameLevelAnimation getGlp() {
        return glp;
    }

    public int getPoints() {
        return glp.countPoints();
    }

    public int getLives() {
        return glp.getLives();
    }
}
