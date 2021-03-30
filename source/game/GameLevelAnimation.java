package game;

import configuration.LevelsParameters;
import static configuration.GameParameters.*;
import static configuration.Constants.*;

import gameitems.*;
import application.RulesDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Dimension2D;
import java.io.IOException;

/**
 * Rysuje elementy charakterystyczne dla danego poziomu:
 * ukształtowanie terenu, przeszkody, elementy dodatkowe, lądownik
 */
public class GameLevelAnimation extends JPanel implements KeyListener {
    /**
     * Czy poziom jest przegrany
     */
    public boolean fail;

    /**
     * Czy poziom jest wygrany
     */
    public boolean win;

    /**
     * Czy poziom zakończył się
     */
    public boolean end;

    /**
     * true - zakończenie gry i powrót do menu, false - nic nie rób
     */
    public boolean backToMenu;

    /**
     * numer poziomu
     */
    private final int levelNumber;

    /**
     * Generator zdarzeń odpowiadających za rysowanie planszy
     */
    public final Timer timer;

    /**
     * Czas gry
     */
    private double time;

    /**
     * Opóźnienie między klatkami w sekundach
     */
    private final double delay;

    /**
     * Poziom paliwa
     */
    private int fuelLevel;

    /**
     * Pole tekstowe pokazujące czas gry oraz aktualne prędkości
     */
    private JLabel timeLabel;

    /**
     * Aktualne wymiary okna
     */
    public Dimension2D size;

    /**
     * Pasek pozostałego paliwa
     */
    private JProgressBar fuelBar;

    /**
     * Życia wyświetlane w postaci serduszek w prawym górnym rogu ekranu
     */
    private Life[] livesIcons;

    /**
     * Liczba pozostałych żyć
     */
    private int lives;

    /**
     * Ukształtowanie terenu i jego parametry
     */
    private Terrain terrain;

    /**
     * Wulkany i ich parametry
     */
    private Volcano[] volcanoes;

    /**
     * Lądownik i jego parametry
     */
    private Lander lander;

    /**
     * Dodatkowe życia ich parametrów
     */
    private Heart[] add_lives;

    /**
     * Zbiorniki uzupełniające paliwo i ich parametry
     */
    private Fuel[] add_fuels;

    /**
     * Kule ognia z wulkanów
     */
    private Fireball[] fireballs;

    /**
     * Skrót do stałych dla danego poziomu
     */
    private final LevelsParameters lp;

    /**
     * okno JFrame, w którym wyświetlana jest gra
     */
    private final GameWindow parent;

    /**
     * Konstruktor. Tworzy obiekty i ustawia domyślne parametry
     * @param parent główne okno gry
     * @param levelNumber numer poziomu
     * @param lives liczba pozostałych żyć
     * @param size rozmiar planszy
     * @throws IOException w przypadku problemów z plikami
     */
    public GameLevelAnimation(GameWindow parent, int levelNumber, int lives, Dimension2D size) throws IOException {
        this.levelNumber = levelNumber;
        this.setLayout(null);
        this.setPreferredSize((Dimension) size);
        this.end = false;
        this.win = false;
        this.fail = false;
        this.backToMenu = false;
        this.size = size;
        this.lives = lives;
        this.delay = ((double) DELAY) / 1000;
        this.time = 0;
        this.fuelLevel = 100;
        this.lp = LEVELS_PARAMETERS[levelNumber - 1];
        this.setBackground(lp.BACKGROUND);
        this.addItems();
        this.resizeItems();
        this.parent = parent;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeItems();
            }
        });

        ActionListener move = e -> {
            repaint();
            moveItems();
            collisions();
            time += delay;
            timeLabel.setText(String.format("<html>Czas: %.2f s<br>Prędkość wert. %.2f m/s<br>Prędkość horyz. %.2f m/s</html>",
                    time, lander.getVv(), lander.getVh()));
        };

        timer = new Timer(DELAY, move);
    }

    /**
     * Dodaje obiekty charakterystyczne dla poziomu: ukształtowanie terenu, wulkany, dodatkowe życia,
     * dodatkowe paliwo oraz lądownik
     *
     * @throws IOException w przypadku problemów z grafikami
     */
    private void addItems() throws IOException {
        livesIcons = new Life[LIVES_MAX];
        for (int i = 0; i < LIVES_MAX; i++) {
            livesIcons[i] = new Life(LIVES[i]);
        }

        timeLabel = new JLabel();
        timeLabel.setBounds(PARAMETERS.x, PARAMETERS.y, 200, 100);
        timeLabel.setForeground(Color.white);
        add(timeLabel);

        fuelBar = new JProgressBar(0, 100);
        fuelBar.setStringPainted(true);
        fuelBar.setValue(100);
        fuelBar.setForeground(Color.green);
        fuelBar.setBorderPainted(false);
        add(fuelBar);

        JLabel levelNumberLabel = new JLabel();
        levelNumberLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, LEVEL_FONT_SIZE));
        levelNumberLabel.setText(String.valueOf(levelNumber));
        levelNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelNumberLabel.setVerticalAlignment(SwingConstants.CENTER);
        levelNumberLabel.setForeground(Color.white);
        levelNumberLabel.setBackground(lp.THEME_COLOR);
        levelNumberLabel.setOpaque(true);
        int h = LEVEL_INDICATOR_WIDTH;
        levelNumberLabel.setBounds(LEVEL_INDICATOR.x, LEVEL_INDICATOR.y, h, h);
        add(levelNumberLabel);

        this.terrain = new Terrain(lp.TERRAIN);

        this.fireballs = new Fireball[lp.FIREBALL_NUMBER];
        for (int i = 0; i < lp.FIREBALL_NUMBER; i++) {
            fireballs[i] = new Fireball(lp.FIREBALL[i], lp.FIREBALL_SIZE);
        }

        this.volcanoes = new Volcano[lp.VOLCANO_NUMBER];
        for (int i = 0; i < lp.VOLCANO_NUMBER; i++) {
            volcanoes[i] = new Volcano(lp.VOLCANO[i], lp.VOLCANO_WIDTH[i], lp.VOLCANO_HEIGHT[i]);
        }

        this.add_lives = new Heart[lp.ADD_LIFE_NUMBER];
        for (int i = 0; i < lp.ADD_LIFE_NUMBER; i++) {
            add_lives[i] = new Heart(lp.ADD_LIFE[i]);
        }

        this.add_fuels = new Fuel[lp.ADD_FUEL_NUMBER];
        for (int i = 0; i < lp.ADD_FUEL_NUMBER; i++) {
            add_fuels[i] = new Fuel(lp.ADD_FUEL[i]);
        }

        this.lander = new Lander(lp.START_POINT, lp.GRAVITY);
    }

    /**
     * Rozpocznij upływ czasu i rysowanie kolejnych klatek
     */
    public void startAnimation() {
        timer.start();
    }

    /**
     * Rysowanie elementów gry
     *
     * @param g2d kontekst graficzny
     */
    protected void paintItems(Graphics2D g2d) {
        g2d.setColor(lp.THEME_COLOR);
        terrain.draw(g2d);
        //paintLevelIndicator(g2d);
        g2d.setColor(lp.VOLCANO_COLOR);
        for (Fireball f : fireballs) f.draw(g2d);
        for (Volcano v : volcanoes) v.draw(g2d);
        for (Heart h : add_lives) {
            if (h.isActive()) h.draw(g2d);
        }
        for (Fuel f : add_fuels) {
            if (f.isActive()) f.draw(g2d);
        }
        lander.draw(g2d);
        for (int i = lives; i < LIVES_MAX; i++) livesIcons[i].setLost(true);
        for (int i = 0; i < lives; i++) livesIcons[i].setLost(false);
        for (Life l : livesIcons) l.draw(g2d);
        int x = (int) (size.getWidth() - (FUEL_BAR.getX() + FUEL_BAR_WIDTH));
        int y = (int) (FUEL_BAR.getY());
        fuelBar.setBounds(x, y, (int) FUEL_BAR_WIDTH, (int) FUEL_BAR_HEIGHT);
    }

    /**
     * Pod wpływem zmiany rozmiaru okna skaluje elementy
     */
    protected void resizeItems() {
        for (Life l : livesIcons) l.resize(size);
        terrain.resize(size);
        for (Volcano v : volcanoes) v.resize(size);
        for (Heart h : add_lives) {
            if (h.isActive()) h.resize(size);
        }
        for (Fuel f : add_fuels) {
            if (f.isActive()) f.resize(size);
        }
        for (Fireball f : fireballs) f.resize(size);
        lander.resize(size);
    }

    /**
     * Zmień współrzędne obiektów
     */
    protected void moveItems() {
        for (Heart h : add_lives) {
            if (h.isActive()) h.move();
        }
        for (Fuel f : add_fuels) {
            if (f.isActive()) f.move();
        }
        for (Fireball f : fireballs) f.move();
        lander.move();
    }

    @Override
    public void paintChildren(Graphics g) {
        super.paintChildren(g);
    }

    @Override
    public void paintBorder(Graphics g) {
        super.paintBorder(g);
    }

    /**
     * Rysuje elementy wspólne dla wszystkich poziomów
     *
     * @param g Obiekt renderujący planszę
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        size = getSize();
        paintItems(g2d);
    }

    /**
     * Wykrywanie kolizji / końca poziomu
     */
    public void collisions() {
        Rectangle l = new Rectangle(lander.getX(), lander.getY(), lander.getWidth(), lander.getHeight());

        for (Heart h : add_lives) {
            if (h.isActive()) {
                Rectangle heart = new Rectangle(h.getX(), h.getY(), h.getWidth(), h.getHeight());
                if (l.intersects(heart)) {
                    lives++;
                    if (lives > LIVES_MAX) lives = LIVES_MAX;
                    h.setActive(false);
                }
            }
        }

        for (Fuel f : add_fuels) {
            if (f.isActive()) {
                Rectangle fuel = new Rectangle(f.getX(), f.getY(), f.getWidth(), f.getHeight());
                if (l.intersects(fuel)) {
                    fuelLevel = 100;
                    f.setActive(false);
                }
            }
        }

        for (Fireball f : fireballs) {
            Rectangle b = new Rectangle(f.getX(), f.getY(), f.getWidth(), f.getHeight());
            if (l.intersects(b)) {
                timer.stop();
                end = true;
                fail = true;
                synchronized (this) {
                    this.notify();
                }
                break;
            }
        }

        for (Volcano v : volcanoes) {
            if(v.getVolcano().intersects(l)) {
                timer.stop();
                end = true;
                fail = true;
                synchronized (this) {
                    this.notify();
                }
                break;
            }
        }

        if (terrain.getTerrain().intersects(l)) {
            if (lander.getCurrent_location_factor().getX() > lp.LANDING_PAD[0] && lander.getCurrent_location_factor().getX() < lp.LANDING_PAD[1]) {
                if (lander.getVv() <= V_V_MAX && lander.getVh() <= V_H_MAX) {
                    win = true;
                    fail = false;
                } else {
                    fail = true;
                    win = false;
                }
            } else {
                fail = true;
                win = false;
            }
            timer.stop();
            end = true;
            parent.glpSize = size;
            synchronized (this) {
                this.notify();
            }
        }

        if (lander.getCurrent_location_factor().getY() > 1.1) {
            timer.stop();
            fail = true;
            win = false;
            end = true;
            parent.glpSize = size;
            synchronized (this) {
                this.notify();
            }
        }
    }

    /**
     * Zliczanie punktów
     *
     * @return zliczone punkty
     */
    public int countPoints() {
        int points = 0;
        points += POINTS_FUEL * fuelLevel / 100;
        if(POINTS_TIME * (POINTS_TIME_MAX - time) > 0) points += POINTS_TIME * (POINTS_TIME_MAX - time) / POINTS_TIME_MAX;
        points += POINTS_V_V * (V_V_MAX - lander.getVv()) / V_V_MAX;
        points += POINTS_V_H * (V_H_MAX - lander.getVh()) / V_H_MAX;
        if (points < 0) points = 0;
        return points;
    }

    public int getLives() {
        return lives;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN && fuelLevel > 0 && timer.isRunning()) {
            lander.setA_vertical(lp.GRAVITY - ENGINE_ACCELERATION);
            fuelLevel = fuelLevel - 2;
            fuelBar.setValue(fuelLevel);
            if(fuelLevel <= 10) fuelBar.setForeground(Color.red);
            else if(fuelLevel <= 40) fuelBar.setForeground(new Color(240, 212, 0));
            else fuelBar.setForeground(Color.green);

        } else {
            lander.setA_vertical(lp.GRAVITY);
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && fuelLevel > 0 && timer.isRunning()) {
            lander.setA_horizontal(-ENGINE_ACCELERATION / 50);
            fuelLevel = fuelLevel - 2;
            fuelBar.setValue(fuelLevel);
            if(fuelLevel <= 10) fuelBar.setForeground(Color.red);
            else if(fuelLevel <= 40) fuelBar.setForeground(new Color(240, 212, 0));
            else fuelBar.setForeground(Color.green);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && fuelLevel > 0 && timer.isRunning()) {
            lander.setA_horizontal(ENGINE_ACCELERATION / 50);
            fuelLevel = fuelLevel - 2;
            fuelBar.setValue(fuelLevel);
            if(fuelLevel <= 10) fuelBar.setForeground(Color.red);
            else if(fuelLevel <= 40) fuelBar.setForeground(new Color(240, 212, 0));
            else fuelBar.setForeground(Color.green);
        } else {
            lander.setA_horizontal(0);
        }

        if (timer.isRunning() && (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ESCAPE)) {
            pauseGame();
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN && timer.isRunning()) {
            lander.setA_vertical(lp.GRAVITY);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && timer.isRunning()) {
            lander.setA_horizontal(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && timer.isRunning()) {
            lander.setA_horizontal(0);
        }
    }

    public void pauseGame() {
        if (timer.isRunning()) {
            timer.stop();
            int o = parent.gameDialogs.showDialog(GAMEPAUSE);


            if(o == JOptionPane.YES_OPTION) {
                resumeGame();
            }
            else if(o == JOptionPane.NO_OPTION) {
                new RulesDialog().showRules(parent);
            }
            else {
                backToMenu = true;
                end = true;
                synchronized (this) {
                    this.notify();
                }
            }
        }
    }

    /**
     * wznowienie gry, po zamnięciu okna pauzy
     */
    public void resumeGame() {
        if(!timer.isRunning()) timer.start();
    }
}
