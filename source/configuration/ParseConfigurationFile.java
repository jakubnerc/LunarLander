package configuration;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Sczytywanie i przekazywanie danych z pliku konfiguracyjnego
 */
public class ParseConfigurationFile {
    private final Properties configuration;

    /**
     * Konstruktor wczytujący plik o podanej nazwie i zapisujący dane w nim zawarte w zmiennej Properties
     *
     * @param file_name nazwa pliku konfiguracyjnego
     */
    public ParseConfigurationFile(String file_name) {
        configuration = new Properties();

        try (FileInputStream fis = new FileInputStream(file_name)) {
            configuration.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @return dane konfiguracyjne w obiektu klasy Properties
     */
    public Properties getProperties() {
        return configuration;
    }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci  obiektu klasy String
     */
    public String getStringProperty(String key) { return configuration.getProperty(key).trim(); }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci tablicy obiektów klasy String
     */
    public String[] getStringArrayProperty(String key) {
        return configuration.getProperty(key).trim().split(",");
    }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci zmiennej typu int
     */
    public int getIntProperty(String key) {
        return Integer.parseInt(configuration.getProperty(key).trim());
    }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci tablicy zmiennych typu int
     */
    public int[] getIntArrayProperty(String key) {
        String[] strings = getStringArrayProperty(key);
        int[] ints = new int[strings.length];
        for(int i = 0; i < strings.length; i++)
        {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints;
    }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci zmiennej typu double
     */
    public double getDoubleProperty(String key) {
        return Double.parseDouble(configuration.getProperty(key));
    }


    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci tablicy zmiennych typu double
     */
    public double[] getDoubleArrayProperty(String key) {
        String[] strings = getStringArrayProperty(key);
        double[] doubles = new double[strings.length];
        for (int i = 0; i < strings.length; i++) {
            doubles[i] = Double.parseDouble(strings[i]);
        }
        return doubles;
    }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci obiektu Color
     */
    public Color getColorProperty(String key) {
        int[] colors = getIntArrayProperty(key);
        return new Color(colors[0],colors[1],colors[2]);
    }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci punktu typu int
     */
    public Point getPointProperty(String key) {
        int x = getIntProperty(key+"_X");
        int y = getIntProperty(key+"_Y");
        return new Point(x,y);
    }


    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci punktu typu double
     */
    public Point2D.Double getPoint2DProperty(String key) {
        double x = getDoubleProperty(key+"_X");
        double y = getDoubleProperty(key+"_Y");
        return new Point2D.Double(x,y);
    }

    /**
     * Getter danej z pliku konfiguracyjnego
     * @param key nazwa danej, której wartość chce się uzyskać
     * @return wartość danej w postaci tablicy punktów typu double
     */
    public Point2D.Double[] getPoint2DArrayProperty(String key) {
        int n = getIntProperty(key);
        double[] x = getDoubleArrayProperty(key+"_X");
        double[] y = getDoubleArrayProperty(key+"_Y");
        Point2D.Double[] points = new Point2D.Double[n];

        for(int i = 0; i < n; i++) {
            points[i] = new Point2D.Double(x[i], y[i]);
        }
        return points;
    }
}