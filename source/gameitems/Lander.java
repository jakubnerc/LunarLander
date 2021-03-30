package gameitems;

import javax.imageio.ImageIO;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import static configuration.GameParameters.*;

/**
 * Rysuje na planszy lądownik i przechowuje jego parametry
 */
public class Lander extends Item {

    /** Przyspieszenie grawitacyjne*/
    private double g;

    /** Wypadkowe przyspieszenie wertykalne*/
    private double a_vertical;

    /** Wypadkowe przyspieszenie horyzontalne*/
    private double a_horizontal;

    /** Wypadkowa prędkość wertykalna*/
    private double vh;

    /** Wypadkowa prędkość horyzontalna*/
    private double vv;

    /**
     * Kontruktor ładujący domyślne ustawienia obiektu
     * @param location_factor współczynniki lokalizacji na planszy po jej otwarciu
     * @param g przyspieszenie grawitacyjne
     * @throws IOException Nie odnaleziono pliku z grafiką
     */
    public Lander(Point2D.Double location_factor, double g) throws IOException {
        super(location_factor);
        this.g = g;
        this.a_vertical = g;
        this.a_horizontal = 0;
        this.vv = 0;
        this.vh = 0;
        this.org_item_image = ImageIO.read(new File("resources\\local\\lander.png"));
        this.resize(PREFERRED_SIZE);
    }

    /**
     * Zmienia rozmiar elementu
     * @param size Wymiary okna, z którego wywołano chęć zmiany rozmiaru elementu
     */
    @Override
    public void resize(Dimension2D size) {
        resizeElement(size, LANDER_SIZE);
    }

    /**
     * Zmiana położenia elementu na planszy
     */
    @Override
    public void move() {
        dy = (vv * delay + (0.5 * a_vertical * delay * delay)) / 30;
        vv = vv + a_vertical * delay;
        current_location_factor.y = current_location_factor.getY() + dy;
        y = (int) (current_location_factor.getY() * size.getHeight());
        dx = vh * delay + (0.5 * a_horizontal * delay * delay) / 30;
        vh = vh + a_horizontal * delay;
        current_location_factor.x = current_location_factor.getX() + dx;
        x = (int) (current_location_factor.getX() * size.getWidth());
    }

    /**
     * Ustalenie wartości przyspieszenia wertykalnego
     * @param a_vertical przyspieszenie wertykalne
     */
    public void setA_vertical(double a_vertical) {
        this.a_vertical = a_vertical;
    }

    /**
     * Ustalenie wartości przyspieszenia wertykalnego
     * @param a_horizontal przyspieszenie wertykalne
     */
    public void setA_horizontal(double a_horizontal) {
        this.a_horizontal = a_horizontal;
    }

    /**
     * Getter prędkości horyzontalnej
     * @return prędkość horyzontalna
     */
    public double getVh() {
        return vh;
    }


    /**
     * Getter prędkości wertykalnej
     * @return prędkość wertykalna
     */
    public double getVv() {
        return vv;
    }
}
