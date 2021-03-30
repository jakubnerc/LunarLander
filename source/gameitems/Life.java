package gameitems;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import static configuration.GameParameters.LIFE_SIZE;
import static configuration.GameParameters.PREFERRED_SIZE;

/**
 * Rysuje i przechowuje parametry wskaźników pozostałych żyć
 */
public class Life {

    /** Grafika pełnego życia*/
    protected Image full;

    /** Grafika straconego życia*/
    protected Image empty;

    /** Współczynniki lokalizacji obiektu na planszy*/
    protected Point2D.Double location_factor;

    /** Aktualne wymiary okna*/
    protected Dimension2D size;

    /** Lokalizacja na ekranie*/
    protected int x;

    /** Lokalizacja na ekranie*/
    protected int y;

    /** Szerokość grafiki*/
    protected int width;

    /** Wysokość grafiki*/
    protected int height;

    /** Czy życie zostało stracone*/
    private boolean lost;

    /**
     * Kontruktor ładujący domyślne ustawienia obiektu
     * @param location_factor współczynniki lokalizacji na planszy po jej otwarciu
     * @throws IOException Nie odnaleziono pliku z grafiką
     */
    public Life(Point2D.Double location_factor) throws IOException {
        this.location_factor = location_factor;
        this.empty = ImageIO.read(new File("resources\\local\\lost_life.png"));
        this.full = ImageIO.read(new File("resources\\local\\life.png"));
        this.resize(PREFERRED_SIZE);
        this.lost = false;
    }

    /**
     * Rysuje grafikę na planszy
     * @param g2d Obiekt renderujący planszę
     */
    public void draw(Graphics2D g2d) {
        if(!lost) g2d.drawImage(full, x, y, LIFE_SIZE, LIFE_SIZE, null);
        else g2d.drawImage(empty, x, y, LIFE_SIZE, LIFE_SIZE, null);
    }

    /**
     * Zmienia rozmiar elementu
     * @param size Wymiary okna, z którego wywołano chęć zmiany rozmiaru elementu
     */
    public void resize(Dimension2D size) {
        this.size = size;
        this.x = (int)(size.getWidth() - location_factor.getX());
        this.y = (int)location_factor.getY();
    }

    /**
     * Ustawienie obiektu jako reprezentującego pozostałe życie lub życie stracone
     * @param lost Życie stracone (true) lub nie (false)
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }
}
