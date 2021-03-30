package application;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

/**
 * okno wpisywania nazwy użytkownika
 */
public class NickDialog {
    /**
     * nazwa użytkownika
     */
    String nick;

    /**
     * pole tekstowe do wspisania nazwy użytkownika
     */
    JFormattedTextField formattedTextField;

    /**
     * generacja domyślnego nicka, dla poprawnego działania okna dialogowego
     */
    public NickDialog() {
        nick = "";
    }

    /**
     * tworzenie obiektu klasy JPanel, zawierającego główne teksty i elementy okna dialogowego
     * @return obiekt JPanel z zawartością
     */
    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        String nickQuestion = "<html><u><strong>Jak się do Ciebie zwracać?</strong></u><br><br></html>";
        JLabel label = new JLabel(nickQuestion);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        label.setFont(font);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(label, c);
        try {
            formattedTextField = new JFormattedTextField(new MaskFormatter("AAAAAAAA"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.gridx = 0;
        c.gridy = 1;
        panel.add(formattedTextField, c);
        formattedTextField.setEditable(true);
        formattedTextField.setColumns(15);
        formattedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        formattedTextField.setFocusLostBehavior(JFormattedTextField.COMMIT);
        return panel;
    }

    /**
     * wyświetlenie okna dialogowego
     * @param parent okno, na tle którego wyświetla się okno dialogowe
     * @return podjęta w oknie decyzja
     */
    public int showNick(JFrame parent) {
        UIManager.put("OptionPane.okButtonText","OK");
        UIManager.put("OptionPane.cancelButtonText","Anuluj");
        JOptionPane ni = new JOptionPane(createPanel(), JOptionPane.PLAIN_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION);
        JDialog d = ni.createDialog(parent, "Wprowadź nazwę użytkownika");
        d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        d.setVisible(true);

        if((int) ni.getValue() == JOptionPane.OK_OPTION && formattedTextField.getText().replaceAll("\\s+","").equals("")) {
            JOptionPane.showMessageDialog(d, "Aby kontynuować, wprowadź nazwę użytkownika",
                    "Nie wprowadzono nazwy użytkownika", JOptionPane.WARNING_MESSAGE);
            d.setVisible(true);
        }
        nick = formattedTextField.getText().replaceAll("\\s+","");
        int decision = (int) ni.getValue();
        d.dispose();
        return decision;
    }
}