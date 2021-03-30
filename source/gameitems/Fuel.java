package gameitems;

import javax.imageio.ImageIO;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import static configuration.GameParameters.ADD_FUEL_SIZE;
import static configuration.GameParameters.PREFERRED_SIZE;

/**
 * Rysuje i przechowuje parametry zbiorników z dodatkowym paliwem
 */
public class Fuel extends Item {

    /**
     * Czy paliwo zostało już pobrane
     */
    boolean active;

    /**
     * Kontruktor ładujący domyślne ustawienia obiektu
     * @param location_factor współczynniki lokalizacji na planszy po jej otwarciu
     * @throws IOException Nie odnaleziono pliku z grafiką
     */
    public Fuel(Point2D.Double location_factor) throws IOException {
        super(location_factor);
        this.active = true;
        this.org_item_image = ImageIO.read(new File("resources\\local\\fuel.png"));
        this.resize(PREFERRED_SIZE);
        dy = 0.0005;
    }

    /**
     * Zmienia rozmiar elementu
     * @param size Wymiary okna, z którego wywołano chęć zmiany rozmiaru elementu
     */
    public void resize(Dimension2D size) {
        resizeElement(size,ADD_FUEL_SIZE);
    }

    /**
     * Zmiana położenia elementu na planszy
     */
    @Override
    public void move() {
        if (Math.abs(current_location_factor.getY() - location_factor.getY()) >= 0.015) {
            dy = -dy;
        }
        current_location_factor.y = current_location_factor.getY() + dy;
        y = (int) (current_location_factor.getY() * size.getHeight());
    }

    /**
     * Getter zmiennej active
     * @return czy paliwo zostało już pobrane
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Ustalenie zmiennej active
     * @param active czy paliwo zostało pobrane
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
