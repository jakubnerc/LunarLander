package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Properties;
import java.util.Vector;

/**
 * klasa obiektu obsługującego listę wyników przechowywaną na serwerze
 */
public class Results {
    /**
     * lista obiektów Rezultat (nick + wynik)
     */
    public Vector<Result> results;

    public int maxSize;

    /**
     * rozmiar listy results
     */
    int size;

    /**
     * pomocniczy obiekt realizujący odczyt pliku i zapis do niego
     */
    Properties r;

    /**
     * zbudowanie listy results na podstawie pliku
     */
    public Results() {
        r = new Properties();
        try (FileInputStream fis = new FileInputStream("resources/local/results.properties")) {
            r.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        maxSize = Integer.parseInt(r.getProperty("NMAX"));
        size = Integer.parseInt(r.getProperty("N"));
        results = new Vector<>(size);

        for(int i = 1; i <= size; i++) {
            Result result = new Result(r.getProperty(i + "n"), Integer.parseInt(r.getProperty(i + "s")));
            results.addElement(result);
        }
    }

    /**
     * sprawdzenie, czy nowy wynik może zostać dodany do listy najlepszych
     * @param nick nazwa użytkownika, który zdobył dany wynik
     * @param result wynik
     * @return miejsce w rankingu badanego wyniku. W przypadku nieznalezienia się w nim: -1
     */
    int checkResult(String nick, int result) {
        Result res = new Result(nick, result);
        results.add(res);
        Collections.sort(results);
        int index = results.indexOf(res);
        if (results.size() > maxSize) {
            results.remove(maxSize - 1);
        }

        if(index > maxSize - 1) return -1;
        else {
            updateResults();
            return index + 1;
        }
    }

    /**
     * aktualizacja pliku z najlepszymi wynikami na podstawie nowej zawartości listy results
     */
    void updateResults() {
        try (FileOutputStream fot = new FileOutputStream("resources\\local\\results.properties", false)) {
            r.setProperty("N",String.valueOf(results.size()));
            for(Result res : results) {
                int index = results.indexOf(res) + 1;
                r.setProperty(index + "n", res.nick);
                r.setProperty(index + "s", String.valueOf(res.score));
            }
            r.store(fot, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
