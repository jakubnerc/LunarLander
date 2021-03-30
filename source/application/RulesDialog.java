package application;

import javax.swing.*;
import java.awt.*;

import static configuration.Texts.*;

/**
 * okno z zasadami gry
 */
public class RulesDialog {


    /**
     * tworzenie obiektu klasy JPanel, zawierającego główne teksty i elementy okna dialogowego
     * @return obiekt JPanel z zawartością
     */
    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JTabbedPane tabbedPane = new JTabbedPane();
        String general = TEXTRULES()[0];
        String control = TEXTRULES()[1];
        String points = TEXTRULES()[2];
        JComponent generalRules = makeTextPanel(general);
        JComponent controlRules = makeTextPanel(control);
        JComponent pointsRules = makeTextPanel(points);
        tabbedPane.addTab("Ogólne", null, generalRules, "ogólne zasady gry");
        tabbedPane.addTab("Sterowanie", null, controlRules, "sterowanie grą z poziomu klawiatury");
        tabbedPane.addTab("Punktacja", null, pointsRules, "zasady punktacji");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(tabbedPane, c);
        return panel;
    }

    /**
     * wyświetlenie okna dialogowego
     * @param parent okno, na tle którego wyświetla się okno dialogowe
     */
    public void showRules(JFrame parent) {
        UIManager.put("OptionPane.okButtonText","Wróć");
        JOptionPane gs = new JOptionPane(createPanel(), JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION);
        JDialog d = gs.createDialog(parent, "Zasady gry");
        d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        d.setVisible(true);
    }

    /**
     * generowanie panelu z tekstem do wyświetlenia w oknie dialogowym
     * @param text tekst do wyświetlenia
     * @return panel z wygenerowanym tekstem
     */
    private JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        filler.setFont(font);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}