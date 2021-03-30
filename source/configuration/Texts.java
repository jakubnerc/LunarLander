package configuration;

import static configuration.GameParameters.*;

/**
 * klasa przechowująca parametryzowane teksty okien w grze
 */
public class Texts {
    /** obiekt odczytujący wybrane dane z pliku i zwracający je w postaci zmiennej zadanego typu*/
    private static final ParseConfigurationFile texts = new ParseConfigurationFile("resources\\local\\texts.properties");

    /** parametryzowana treść zasad ogólnych gry*/
    public static final String TEXT_GENERAL_RULES = texts.getStringProperty("TEXT_GENERAL_RULES");

    /** parametryzowana treść zasad sterowania*/
    public static final String TEXT_CONTROL_RULES = texts.getStringProperty("TEXT_CONTROL_RULES");

    /** parametryzowana treść zasad punktowania*/
    public static final String TEXT_POINTS_RULES = texts.getStringProperty("TEXT_POINTS_RULES");

    /** parametryzowana treść komunikatu rozpoczęcia gry*/
    public static final String TEXT_GAME_START_D = texts.getStringProperty("TEXT_GAME_START_D");

    /** pytanie o akcję w oknie rozpoczęcia gry*/
    public static final String TEXT_GAME_START_Q = texts.getStringProperty("TEXT_GAME_START_Q");

    /** parametryzowana treść komunikatu końca gry*/
    public static final String TEXT_GAME_OVER_D = texts.getStringProperty("TEXT_GAME_OVER_D");

    /** pytanie o akcję w oknie końca gry*/
    public static final String TEXT_GAME_OVER_Q = texts.getStringProperty("TEXT_GAME_OVER_Q");

    /** parametryzowana treść komunikatu przegranego poziomu*/
    public static final String TEXT_LEVEL_REPEAT_D = texts.getStringProperty("TEXT_LEVEL_REPEAT_D");

    /** pytanie o akcję w oknie przegranego poziomu*/
    public static final String TEXT_LEVEL_REPEAT_Q = texts.getStringProperty("TEXT_LEVEL_REPEAT_Q");

    /** parametryzowana treść komunikatu podsumowującego poziom*/
    public static final String TEXT_LEVEL_SUMMARY_D = texts.getStringProperty("TEXT_LEVEL_SUMMARY_D");

    /** pytanie o akcję w oknie podsumowania poziomu*/
    public static final String TEXT_LEVEL_SUMMARY_Q = texts.getStringProperty("TEXT_LEVEL_SUMMARY_Q");

    /** parametryzowana treść komunikatu o wstrzymaniu gry*/
    public static final String TEXT_PAUSE_D = texts.getStringProperty("TEXT_PAUSE_D");

    /** pytanie o akcję w oknie pauzy*/
    public static final String TEXT_PAUSE_Q = texts.getStringProperty("TEXT_PAUSE_Q");

    /** parametryzowana treść komunikatu o wygranej grze*/
    public static final String TEXT_GAME_WIN_D = texts.getStringProperty("TEXT_GAME_WIN_D");

    /** pytanie o akcję w oknie wygranej gry*/
    public static final String TEXT_GAME_WIN_Q = texts.getStringProperty("TEXT_GAME_WIN_Q");

    /** parametryzowana treść komunikatu o następnym poziomie*/
    public static final String TEXT_NEXT_LEVEL_D = texts.getStringProperty("TEXT_NEXT_LEVEL_D");

    /** pytanie o akcję w oknie następnego poziomu*/
    public static final String TEXT_NEXT_LEVEL_Q = texts.getStringProperty("TEXT_NEXT_LEVEL_Q");

    /**
     * nieaktywny konstruktor
     */
    private Texts() {}

    /**
     * uzupełnienie tekstów zasad parametrami gry
     * @return tablica gotowych tekstów do wyświetlenia w oknie zasad
     */
    public static String[] TEXTRULES() {
        String[] tr = new String[3];
        tr[0] = String.format(TEXT_GENERAL_RULES, LEVELS, poziomD(LEVELS), V_V_MAX, V_H_MAX, LIVES_MAX, zycieN(LIVES_MAX));
        tr[1] = TEXT_CONTROL_RULES;
        tr[2] = String.format(TEXT_POINTS_RULES, POINTS_TIME, POINTS_TIME_MAX, POINTS_FUEL, POINTS_V_V, V_V_MAX,
                V_V_MAX, POINTS_V_H, V_H_MAX, V_H_MAX);
        return tr;
    }

    /**
     * uzupełnienie tekstów okna rozpoczęcia gry parametrami gry
     * @param nick nazwa aktualnie grającego użytkownika
     * @return tablica gotowych tekstów do wyświetlenia w oknie rozpoczęcia gry
     */
    public static String[] TEXTGAMESTART(String nick) {
        String[] tgs = new String[2];
        tgs[0] = String.format(TEXT_GAME_START_D, nick, LEVELS, poziom(LEVELS), ktory(LEVELS), LIVES_MAX,
                zycieN(LIVES_MAX), V_V_MAX, V_H_MAX);
        tgs[1] = TEXT_GAME_START_Q;
        return tgs;
    }


    /**
     * uzupełnienie tekstów okna końca gry parametrami gry
     * @param nick nazwa aktualnie grającego użytkownika
     * @param totalpoints liczba zdobytych dotychczas punktów
     * @return tablica gotowych tekstów do wyświetlenia w oknie końca gry
     */
    public static String[] TEXTGAMEOVER(String nick, int totalpoints) {
        String[] tgo = new String[2];
        tgo[0] = String.format(TEXT_GAME_OVER_D, nick, totalpoints, punktB(totalpoints));
        tgo[1] = TEXT_GAME_OVER_Q;
        return tgo;
    }


    /**
     * uzupełnienie tekstów okna przegranego poziomu parametrami gry
     * @param lives liczba pozostałych żyć
     * @return tablica gotowych tekstów do wyświetlenia w oknie przegranego poziomu
     */
    public static String[] TEXTLEVELREPEAT(int lives) {
        String[] tlr = new String[2];
        tlr[0] = String.format(TEXT_LEVEL_REPEAT_D, pozostac(lives), lives, zycieB(lives));
        tlr[1] = TEXT_LEVEL_REPEAT_Q;
        return tlr;
    }

    /**
     * uzupełnienie tekstów okna posumowania poziomu parametrami gry
     * @param points liczba punktów zdobytych po przejściu poziomu
     * @param totalpoints liczba zdobytych dotychczas punktów
     * @param lives liczba pozostałych żyć
     * @return tablica gotowych tekstów do wyświetlenia w oknie podsumowania poziomu
     */
    public static String[] TEXTLEVELSUMMARY(int points, int totalpoints, int lives) {
        String[] tls = new String[2];
        tls[0] = String.format(TEXT_LEVEL_SUMMARY_D, points, punktB(points), pozostac(lives), lives, zycieB(lives),
                totalpoints, punktB(totalpoints));
        tls[1] = TEXT_LEVEL_SUMMARY_Q;
        return tls;
    }

    /**
     * uzupełnienie tekstów okna pauzy
     * @param levelNumber numer poziomu
     * @return tablica gotowych tekstów do wyświetlenia w oknie pauzy
     */
    public static String[] TEXTPAUSE(int levelNumber) {
        String[] tp = new String[2];
        tp[0] = String.format(TEXT_PAUSE_D, levelNumber, LEVELS);
        tp[1] = TEXT_PAUSE_Q;
        return tp;
    }

    /**
     * uzupełnienie tekstów okna wygranej gry
     * @param nick nazwa aktualnie grającego użytkownika
     * @param totalpoints liczba zdobytych dotychczas punktów
     * @param best czy gracz zajął pierwsze miejsce w rankingu
     * @param rank miejsce gracza w rankingu
     * @return tablica gotowych tekstów do wyświetlenia w oknie wygranej gry
     */
    public static String[] TEXTGAMEWIN(String nick, int totalpoints, boolean best, int rank) {
        String[] tgw = new String[2];
        tgw[0] = String.format(TEXT_GAME_WIN_D, nick, totalpoints, punktB(totalpoints), rezultat(best, rank));
        tgw[1] = TEXT_GAME_WIN_Q;
        return tgw;
    }

    /**
     * uzupełnienie tekstów
     * @param levelNumber numer następnego poziomu
     * @param lives pozstała liczba żyć
     * @return tablica gotowych tekstów do wyświetlenia w oknie następnego poziomu
     */
    public static String[] TEXTNEXTLEVEL(int levelNumber, int lives) {
        String[] tnl = new String[2];
        tnl[0] = String.format(TEXT_NEXT_LEVEL_D, levelNumber, LEVELS_PARAMETERS[levelNumber - 1].GRAVITY,lives, zycieB(lives));
        tnl[1] = TEXT_NEXT_LEVEL_Q;
        return tnl;
    }

    /**
     * metoda odmieniająca wyraz "poziom" w zależności od liczby pozostałych poziomów
     * @param number liczba pozostałych poziomów
     * @return odmieniony wyraz "poziom"
     */
    public static String poziom(int number) {
        if (number == 1) return "poziom";
        else if (number % 100 > 10 && number % 100 < 15) {
            return "poziomów";
        }
        else {
            switch (number % 10) {
                case 2:
                case 3:
                case 4:
                    return "poziomy";
                default:
                    return "poziomów";
            }
        }
    }

    /**
     * metoda odmieniająca wyraz "poziom" w dopełniaczu
     * @param number liczba poziomów
     * @return odmieniony wyraz poziom
     */
    public static String poziomD(int number) {
        if (number == 1) return "poziomu";
        else return "poziomów";
    }

    /**
     * metoda odmieniająca wyraz "życie" w bierniku
     * @param number liczba żyć
     * @return odmieniony wyraz życie
     */
    public static String zycieB(int number) {
        if (number == 1) return "życie";
        else if (number % 100 > 10 && number % 100 < 15) {
            return "żyć";
        }
        else {
            switch (number % 10) {
                case 2:
                case 3:
                case 4:
                    return "życia";
                default:
                    return "żyć";
            }
        }
    }

    /**
     * metoda odmieniająca wyraz "życie" w narzędniku
     * @param number liczba żyć
     * @return odmieniony wyraz "życie"
     */
    public static String zycieN(int number) {
        if (number == 1) return "życiem";
        else return "życiami";
    }

    /**
     * metoda odmieniająca wyraz życie w narzędniku
     * @param number liczba żyć
     * @return odmieniony wyraz "pozostać"
     */
    public static String pozostac(int number) {
        if (number == 1 || (number % 100 > 10 && number % 100 < 15)) return "pozostało";
        else {
            switch (number % 10) {
                case 2:
                case 3:
                case 4:
                    return "pozostały";
                default:
                    return "pozostało";
            }
        }
    }

    /**
     * metoda odmieniająca wyraz "który"
     * @param number liczba poziomów
     * @return odmieniony wyraz "poziom"
     */
    public static String ktory(int number) {
        if (number == 1) return "którym";
        else return "których";
    }


    /**
     * metoda odmieniająca wyraz "punkt"
     * @param number liczba punktów
     * @return odmieniony wyraz "punkt"
     */
    public static String punktB(int number) {
        if (number == 1) return "punkt";
        else if (number % 100 > 10 && number % 100 < 15) {
            return "punktów";
        }
        else {
            switch (number % 10) {
                case 2:
                case 3:
                case 4:
                    return "punkty";
                default:
                    return "punktów";
            }
        }
    }

    /**
     * metoda zwracająca inny tekst dla zajęcia pierwszego miejsca w rankingu, a inny dla zajęcia pozostałych miejsc
     * @param best true - jeśli zajęto pierwsze miejsce, false - jeśli zajęto miejsce inne niż pierwsze
     * @param number miejsce w rankingu
     * @return tekst komunikatu do wyświetlenia
     */
    public static String rezultat(boolean best, int number) {
        if(best) {
            if(number == 1) return "Twój wynik jest nowym najlepszym rezultatem na liście. ";
            else return "Twój wynik znalazł się na liście najlepszych rezultatów, zajmując w nim " + number + " pozycję. ";
        }
        else return "Niestety Twój wynik jest niewystarczający by znaleźć się na liście najlepszych rezultatów. ";
    }

    public static void main(String[] args) {
        //System.out.println(TEXTGAMEOVER("kiszka",25)[0]);
    }
}

