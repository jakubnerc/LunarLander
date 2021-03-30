package configuration;

import java.awt.*;
import java.awt.geom.Point2D;


/**
 * Klasa parametrów dla danego poziomu
 */
public class LevelsParameters {

    /** Identyfikator poziomu*/
    public final String LEVEL_ID;

    /** Nazwa poziomu*/
    public final String LEVEL_NAME;

    /** Przyspieszenie grawitacyjne*/
    public final double GRAVITY;

    /** Punkt startowy lądownika*/
    public final Point2D.Double START_POINT;

    /** Kolor tła*/
    public final Color BACKGROUND;

    /** Kolor przewodni (ukształtowania terenu)*/
    public final Color THEME_COLOR;

    /** Liczba punktów ukształtowania terenu*/
    public final int TERRAIN_POINTS;

    /** Współczynniki lokalizacji punktów ukształtowania terenu*/
    public final Point2D.Double[] TERRAIN;

    /** Liczba wulkanów*/
    public final int VOLCANO_NUMBER;

    /** Współczynniki lokalizacji punktów wulkanów*/
    public final Point2D.Double[] VOLCANO;

    /** Współczynnik szerokości wulkanu*/
    public final double[] VOLCANO_WIDTH;

    /** Współczynnik wysokości wulkanu*/
    public final double[] VOLCANO_HEIGHT;

    /** Kolor wulkanu*/
    public final Color VOLCANO_COLOR;

    public final double[] LANDING_PAD;

    /** Liczba ogniowych kul*/
    public final int FIREBALL_NUMBER;

    /**Współczynniki lokalizacji ogniowych kul*/
    public final Point2D.Double[] FIREBALL;

    /** Współczynnik rozmiaru ogniowej kuli*/
    public final double FIREBALL_SIZE;

    /** Liczba dodatkowych żyć na planszy*/
    public final int ADD_LIFE_NUMBER;

    /** Współczynniki lokalizacji dodatkowych żyć*/
    public final Point2D.Double[] ADD_LIFE;

    /** Liczba zbiorników z dodatkowym paliwem na planszy*/
    public final int ADD_FUEL_NUMBER;

   /** Współczynniki lokalizacji zbiorników z dodatkowym paliwem na planszy*/
    public final Point2D.Double[] ADD_FUEL;



    /**
     * Konstruktor obiektu przechowującego parametry danego poziomu
     * @param id Identyfikator poziomu
     * @param server Czy gra pracuje z serwerem
     */
    public LevelsParameters(String id, boolean server) {
        String levels_path = "resources/local/levels_parameters.properties";
        ParseConfigurationFile levels_parameters = new ParseConfigurationFile(levels_path);

        this.LEVEL_ID = levels_parameters.getStringProperty(id + "LEVEL_ID");
        this.LEVEL_NAME = levels_parameters.getStringProperty(id + "LEVEL_NAME");
        this.GRAVITY = levels_parameters.getDoubleProperty(id + "GRAVITY");
        this.START_POINT = levels_parameters.getPoint2DProperty(id + "START_POINT");
        this.BACKGROUND = levels_parameters.getColorProperty(id + "BACKGROUND");
        this.THEME_COLOR = levels_parameters.getColorProperty(id + "THEME_COLOR");
        this.TERRAIN_POINTS = levels_parameters.getIntProperty(id + "TERRAIN");
        this.TERRAIN = levels_parameters.getPoint2DArrayProperty(id + "TERRAIN");
        this.VOLCANO_NUMBER = levels_parameters.getIntProperty(id + "VOLCANO");
        this.VOLCANO = levels_parameters.getPoint2DArrayProperty(id + "VOLCANO");
        this.VOLCANO_WIDTH = levels_parameters.getDoubleArrayProperty(id + "VOLCANO_WIDTH");
        this.VOLCANO_HEIGHT = levels_parameters.getDoubleArrayProperty(id + "VOLCANO_HEIGHT");
        this.VOLCANO_COLOR = levels_parameters.getColorProperty(id + "VOLCANO_COLOR");
        this.LANDING_PAD = levels_parameters.getDoubleArrayProperty(id + "LANDING_PAD");
        this.FIREBALL_NUMBER = levels_parameters.getIntProperty(id + "FIREBALL");
        this.FIREBALL = levels_parameters.getPoint2DArrayProperty(id + "FIREBALL");
        this.FIREBALL_SIZE = levels_parameters.getDoubleProperty(id + "FIREBALL_SIZE");
        this.ADD_LIFE_NUMBER = levels_parameters.getIntProperty(id + "ADD_LIFE");
        this.ADD_LIFE = levels_parameters.getPoint2DArrayProperty(id + "ADD_LIFE");
        this.ADD_FUEL_NUMBER = levels_parameters.getIntProperty(id + "ADD_FUEL");
        this.ADD_FUEL = levels_parameters.getPoint2DArrayProperty(id + "ADD_FUEL");
    }

}