package gameitems;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import static configuration.GameParameters.*;

/**
 * Klasa obiektu przechowującego parametry ukształtowania terenu i rysującego go
 */
public class Terrain {
    /** Aktualne wymiary okna*/
    Dimension2D size;

    /** Obiekt rysujący ukształtowanie terenu*/
    private Path2D.Double terrain;

    /** Liczba punktów ukształtowania terenu*/
    private final int points_number;

    /** Tablica współczynników współrzędnych punktów terenu*/
    private final Point2D.Double[] location_factor;

    /**
     * Kontruktor ładujący domyślne ustawienia obiektu
     * @param location_factor współczynniki lokalizacji na planszy po jej otwarciu
     */
    public Terrain(Point2D.Double[] location_factor) {
        this.location_factor = location_factor;
        this.resize(PREFERRED_SIZE);
        this.points_number = location_factor.length;
    }

    /**
     * Rysuje kształt na planszy
     * @param g2d Obiekt renderujący planszę
     */
    public void draw(Graphics2D g2d) {
        g2d.fill(terrain);
    }

    /**
     * Zmienia rozmiar elementu
     * @param size Wymiary okna, z którego wywołano chęć zmiany rozmiaru elementu
     */
    public void resize(Dimension2D size) {
        terrain = new Path2D.Double();
        terrain.moveTo(size.getWidth() * location_factor[0].getX(),size.getHeight() * location_factor[0].getY());
        for(int i = 1; i < points_number; i++) {
            terrain.lineTo(size.getWidth() * location_factor[i].getX(),size.getHeight() * location_factor[i].getY());
        }
        terrain.closePath();
        this.size = size;
    }

    /**
     * Getter współczynników współrzędnych punktów formujących ukształtowanie terenu
     * @return punkty terenu
     */
    public Path2D.Double getTerrain() {
        return terrain;
    }
}
