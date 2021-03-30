package configuration;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Klasa obiektów przechowujących odczytane z pliku konfiguracyjnego zmienne dotyczące wszystkich poziomów
 */
public class GameParameters {

    /**
     * gdy true - praca z serwerem, gdy false - praca lokalna
     */
    public static boolean server;

    /**
     * określa, który plik (lokalny, czy pobrany z serwera) ma zostać użyty do załadowania konfiguracji
     */
    public static void parse() {
        String game_path = "resources/local/game_parameters.properties";
        ParseConfigurationFile game_parameters = new ParseConfigurationFile(game_path);
        GAME_NAME = game_parameters.getStringProperty("GAME_NAME");
        LEVELS = game_parameters.getIntProperty("LEVELS");
        ORDER = game_parameters.getStringArrayProperty("ORDER");
        DELAY = game_parameters.getIntProperty("DELAY");
        ENGINE_ACCELERATION = game_parameters.getDoubleProperty("ENGINE_ACCELERATION");
        V_H_MAX = game_parameters.getDoubleProperty("V_H_MAX");
        V_V_MAX = game_parameters.getDoubleProperty("V_V_MAX");
        LIVES_MAX = game_parameters.getIntProperty("LIVES_MAX");
        PREFERRED_SIZE = new Dimension(game_parameters.getIntProperty("PREFERRED_SIZE_X"),
                game_parameters.getIntProperty("PREFERRED_SIZE_Y"));
        LEVEL_INDICATOR = game_parameters.getPointProperty("LEVEL_INDICATOR");
        LEVEL_INDICATOR_WIDTH = game_parameters.getIntProperty("LEVEL_INDICATOR_WIDTH");
        PARAMETERS = game_parameters.getPointProperty("PARAMETERS");
        LANDER_SIZE = game_parameters.getIntProperty("LANDER_SIZE");
        LIFE_SIZE = game_parameters.getIntProperty("LIFE_SIZE");
        LIVES = game_parameters.getPoint2DArrayProperty("LIVES");
        ADD_LIFE_SIZE = game_parameters.getDoubleProperty("ADD_LIFE_SIZE");
        ADD_FUEL_SIZE = game_parameters.getDoubleProperty("ADD_FUEL_SIZE");
        FUEL_BAR = game_parameters.getPoint2DProperty("FUEL_BAR");
        FUEL_BAR_WIDTH = game_parameters.getDoubleProperty("FUEL_BAR_WIDTH");
        FUEL_BAR_HEIGHT = game_parameters.getDoubleProperty("FUEL_BAR_HEIGHT");
        LEVEL_FONT_SIZE = game_parameters.getIntProperty("LEVEL_FONT_SIZE");
        PARAMETERS_FONT_SIZE = game_parameters.getIntProperty("PARAMETERS_FONT_SIZE");
        POINTS_TIME_MAX = game_parameters.getIntProperty("POINTS_TIME_MAX");
        POINTS_TIME = game_parameters.getIntProperty("POINTS_TIME");
        POINTS_FUEL = game_parameters.getIntProperty("POINTS_FUEL");
        POINTS_V_H = game_parameters.getIntProperty("POINTS_V_H");
        POINTS_V_V = game_parameters.getIntProperty("POINTS_V_V");
        LEVELS_PARAMETERS = importLevels();
    }

    /** Nazwa gry*/
    public static String GAME_NAME;

    /** Liczba poziomów*/
    public static int LEVELS;

    /** Kolejność poziomów*/
    public static String[] ORDER;

    /** Okres odświeżania obrazu gry*/
    public static int DELAY;

    /** Przyspieszenie, jakie zapewnia silnik lądownika*/
    public static double ENGINE_ACCELERATION;

    /** Maksymalna dopuszczalna prędkość horyzontalna lądownika*/
    public static double V_H_MAX;

    /** Maksymalna dopuszczalna prędkość wertykalna lądownika*/
    public static double V_V_MAX;

    /** Maksymalna liczba żyć*/
    public static int LIVES_MAX;

    /** Domyślny rozmiar planszy po otwarciu*/
    public static Dimension PREFERRED_SIZE;

    /** Współrzędne wskaźnika rogrywanego poziomu*/
    public static Point LEVEL_INDICATOR;

    /** Rozmiar wskaźnika rozgrywanego poziomu*/
    public static int LEVEL_INDICATOR_WIDTH;

    /** Współrzędne pola parametrów gry*/
    public static Point PARAMETERS;

    public static int LANDER_SIZE;

    /** Rozmiar serca (symbolizującego życie)*/
    public static int LIFE_SIZE;

    /** Współrzędne serc (symbolizujących życia) na planszy*/
    public static Point2D.Double[] LIVES;

    /** Rozmiar dodatkowego serca dla domyślnych wymiarów okna*/
    public static double ADD_LIFE_SIZE;

    /** Rozmiar dodatkowego paliwa dla domyślnych wymiarów okna*/
    public static double ADD_FUEL_SIZE;

    /** Współrzędne wskaźnika pozostałego paliwa*/
    public static Point2D.Double FUEL_BAR;

    /** Szerokość wskaźnika pozostałego paliwa*/
    public static double FUEL_BAR_WIDTH;

    /** Wysokość wskaźnika pozostałego paliwa*/
    public static double FUEL_BAR_HEIGHT;

    /** Wielkość fontu używanego na wskaźniku aktualnego poziomu*/
    public static int LEVEL_FONT_SIZE;

    /** Wielkość fontu używanego w polu informacji o parametrach*/
    public static int PARAMETERS_FONT_SIZE;

    /** Maksymalny czas gry, dla którego można zdobyć punkty za czas*/
    public static int POINTS_TIME_MAX;

    /** Współczynnik punktów za czas*/
    public static int POINTS_TIME;

    /** Współczynnik punktów za paliwo*/
    public static int POINTS_FUEL;

    /** Współczynnik punktów za prędkość horyzontalną*/
    public static int POINTS_V_H;

    /** Współczynnik punktów za prędkość wertykalną*/
    public static int POINTS_V_V;

    /** Tablica parametrów każdego poziomu*/
    public static LevelsParameters[] LEVELS_PARAMETERS;

    /**
     * Tworzenie tablicy parametrów dla każdego poziomu
     * @return Tablica parametrów dla każdego poziomu
     */
    private static LevelsParameters[] importLevels() {
        LevelsParameters[] levels = new LevelsParameters[LEVELS];
        for(int i = 0; i < LEVELS; i++) levels[i] = new LevelsParameters(ORDER[i], server);
        return levels;
    }


    /**
     * Nieaktywny konstruktor
     */
    private GameParameters() {}
}