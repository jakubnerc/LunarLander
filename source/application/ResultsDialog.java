package application;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * okno z listą najlepszych wyników
 */
public class ResultsDialog {


    /**
     * tworzenie obiektu klasy JPanel, zawierającego główne teksty i elementy okna dialogowego
     * @return obiekt JPanel z zawartością
     */
    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream("resources/local/results.properties")) {
            p.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        int n = Integer.parseInt(p.getProperty("N"));

        for(int i = 1; i <= n; i++) {
            String nick = p.getProperty(i + "n");
            String result = p.getProperty(i + "s");
            sb.append(i).append(". ").append(nick).append(" - ").append(result).append("<br>");
        }

        sb.append("</html>");
        String label = new String(sb);
        JLabel results = new JLabel(label);
        c.gridx = 0;
        c.gridy = 0;
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        results.setFont(font);
        panel.add(results, c);
        return panel;
    }

    /**
     * wyświetlenie okna dialogowego
     * @param parent okno, na tle którego wyświetla się okno dialogowe
     */
    public void showResults(JFrame parent) {
        UIManager.put("OptionPane.okButtonText","Wróć");
        JOptionPane gs = new JOptionPane(createPanel(), JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION);
        JDialog d = gs.createDialog(parent, "Lista najlepszych wyników");
        d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        d.setVisible(true);
    }

}
