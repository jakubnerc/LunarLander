package gameitems;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import static configuration.GameParameters.DELAY;
import static configuration.GameParameters.PREFERRED_SIZE;

/**
 * Klasa abstrakcyjna. Rysuje i przechowuje paramentry obiektów na planszy, będących grafikami
 */
abstract class Item {

    /**
     * Obiekt przechowujący zbuforowaną grafikę
     */
    protected BufferedImage item_image;

    /**
     * Obiekt przechowujący oryginalną grafikę
     */
    protected Image org_item_image;

    /**
     * Początkowe współczynniki lokalizacji obiektu na planszy
     */
    protected final Point2D.Double location_factor;

    /**
     * Aktualne współrzędne
     */
    protected Point2D.Double current_location_factor;

    /**
     * Aktualne wymiary okna
     */
    protected Dimension2D size;

    /**
     * Lokalizacja na ekranie
     */
    protected int x;

    /**
     * Lokalizacja na ekranie
     */
    protected int y;

    /**
     * Szerokość grafiki
     */
    protected int width;

    /**
     * Wysokość grafiki
     */
    protected int height;

    /** Droga jaką przebywa obiekt podczas jednej klatki na osi OX*/
    protected double dx;

    /** Droga jaką przebywa obiekt podczas jednej klatki na osi OX*/
    protected double dy;

    /** Opóźnienie między klatkami*/
    protected double delay;


    /**
     * Konstruktor obiektu
     *
     * @param location_factor Współczynniki lokalizacji na planszy po jej otwarciu
     */
    Item(Point2D.Double location_factor) {
        this.location_factor = location_factor;
        this.current_location_factor = new Point2D.Double(location_factor.getX(), location_factor.getY());
        this.dx = 0;
        this.dy = 0;
        this.delay = ((double)DELAY)/1000;
    }

    /**
     * Zmienia rozmiar elementu
     * @param object_size rozmiar obiektu
     * @param size Wymiary okna, z którego wywołano chęć zmiany rozmiaru elementu
     */
    public void resizeElement(Dimension2D size, double object_size) {
        this.size = size;
        x = (int) (size.getWidth() * current_location_factor.getX());
        y = (int) (size.getHeight() * current_location_factor.getY());
        this.width = (int)(object_size * size.getWidth() / PREFERRED_SIZE.getWidth());
        this.height = (int)(object_size * size.getHeight() / PREFERRED_SIZE.getHeight());
        if (this.height > 0 && this.width > 0) {
            Image temp_img = org_item_image.getScaledInstance(width, height, Image.SCALE_FAST);
            item_image = new BufferedImage(temp_img.getWidth(null),temp_img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
            Graphics2D graph_img = item_image.createGraphics();
            graph_img.drawImage(temp_img,0,0,null);
            graph_img.dispose();
        }
    }

    /**
     * Modyfikacja lokalizacji obiektu do następnej klatki
     */
    public abstract void move();

    /**
     * Modyfikacja rozmiarów obiektu
     * @param size rozmiar okna
     */
    abstract void resize(Dimension2D size);

    /**
     * Rysowanie obiektu w kontekście graficznym
     * @param g2d kontekst graficzny
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(item_image, x, y, null);
    }

    /**
     * Getter zmiennej x
     * @return zmienna x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter zmiennej y
     * @return zmienna y
     */
    public int getY() {
        return y;
    }

    /**
     * Getter szerokości
     * @return szerokość obiektu
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter wysokości
     * @return wysokość obiektu
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter aktualnych współczynników współrzędnych
     * @return aktualne współczynniki współrzędnych
     */
    public Point2D.Double getCurrent_location_factor() {
        return current_location_factor;
    }
}
