package configuration;

/**
 * stałe wykorzystywane do wywoływania określonych akcji dla danej wartości
 */
public class Constants {
    /**
     * wywołanie okna rozpoczęcia gry
     */
    public static final int GAMESTART = 0;

    /**
     * wywołanie okna następnego poziomu
     */
    public static final int GAMENEXTLEVEL = 1;

    /**
     * wywołanie okna podsumowania poziomu
     */
    public static final int GAMELEVELSUMMARY = 2;

    /**
     * wywołanie okna przegranego poziomu
     */
    public static final int GAMELEVELREPEAT = 3;

    /**
     * wywołanie okna wygranej gry
     */
    public static final int GAMEWIN = 4;

    /**
     * wywołanie okna przegranej gry
     */
    public static final int GAMEOVER = 5;

    /**
     * wywołanie okna pauzy
     */
    public static final int GAMEPAUSE = 6;

    /**
     * informacja o wygranej
     */
    public static final int WIN = 7;

    /**
     * informacja o przegranej
     */
    public static final int LOSE = 8;

    /**
     * powrót do menu
     */
    public static final int MENU = 9;

    /**
     * nieaktywny konstruktor
     */
    private Constants() {}
}
