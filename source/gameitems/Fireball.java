package gameitems;

import javax.imageio.ImageIO;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import static configuration.GameParameters.PREFERRED_SIZE;

public class Fireball extends Item {

    /**
     * rozmiar kuli ogniowej
     */
    private final double fireball_size;

    /**
     * Konstrukto obiektu
     * @param location_factor współczynniki lokalizacji
     * @param fireball_size rozmiar kuli ogniowej
     * @throws IOException w przypadku braku pliku
     */
    public Fireball(Point2D.Double location_factor, double fireball_size) throws IOException {
        super(location_factor);
        this.org_item_image = ImageIO.read(new File("resources\\local\\fireball.png"));
        this.fireball_size = fireball_size;
        this.resize(PREFERRED_SIZE);
        this.dy = 0.005;
    }

    /**
     * Zmienia rozmiar elementu
     * @param size Wymiary okna, z którego wywołano chęć zmiany rozmiaru elementu
     */
    public void resize(Dimension2D size) {
        resizeElement(size, fireball_size);
    }

    /**
     * Zmiana położenia elementu na planszy
     */
    @Override
    public void move() {
        if (current_location_factor.getY() < -0.1) {
            current_location_factor.y = location_factor.getY();
        }
        else {
            current_location_factor.y = current_location_factor.getY() - dy;
        }
        y = (int) (current_location_factor.getY() * size.getHeight());
    }
}
