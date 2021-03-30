package gameitems;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import static configuration.GameParameters.*;

/**
 * Rysuje na planszy wulkany i przechowuje ich parametry
 */
public class Volcano {
    /** Aktualne wymiary okna*/
    Dimension2D size;

    /** Lokalizacja na ekranie */
    double x;

    /** Lokalizacja na ekranie*/
    double y;

    /** Współczynnik szerokości kształtu*/
    private final double volcano_width;

    /** Współczynnik wysokości kształtu*/
    private final double volcano_height;

    /** Szerokość wulkanu*/
    private double width;

    /** Wysokość wulkanu*/
    private double height;

    /** Obiekt rysujący kształt wulkanu*/
    private Path2D.Double volcano;

    /** Tablica współczynników współrzędnych punktów wulkanu*/
    private final Point2D.Double location_factor;

    /**
     * Kontruktor ładujący domyślne ustawienia obiektu
     * @param location_factor Współczynniki lokalizacji na planszy po jej otwarciu
     * @param volcano_width Współczynnik szerokości kształtu
     * @param volcano_height Współczynnik wysokości kształtu
     */
    public Volcano(Point2D.Double location_factor, double volcano_width, double volcano_height)  {
        this.location_factor = location_factor;
        this.volcano_width = volcano_width;
        this.volcano_height = volcano_height;
        this.resize(PREFERRED_SIZE);
    }

    /**
     * Rysuje kształt na planszy
     * @param g2d Obiekt renderujący planszę
     */
    public void draw(Graphics2D g2d) {
        g2d.fill(volcano);
    }

    /**
     * Zmienia rozmiar elementu
     * @param size Wymiary okna, z którego wywołano chęć zmiany rozmiaru elementu
     */
    public void resize(Dimension2D size) {
        this.size = size;
        volcano = new Path2D.Double();
        volcano.moveTo(x, y);
        volcano.lineTo(x + 0.35 * width,y - height);
        volcano.lineTo(x + 0.65 * width,y - height);
        volcano.lineTo(x + width, y);
        volcano.closePath();
        this.x = location_factor.getX() * size.getWidth();
        this.y = location_factor.getY() * size.getHeight();
        this.width = volcano_width * size.getWidth();
        this.height = volcano_height * size.getHeight();
    }

    public Path2D.Double getVolcano() {
        return volcano;
    }
}
