package application;

import configuration.GameParameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * okno menu głównego
 */
public class MainWindow implements ActionListener {
    private JFrame mainWindow;
    private JButton startButton;
    private JButton rulesButton;
    private JButton resultsButton;
    private JButton closeButton;

    /**
     * definicja i ustalenie wyglądu okna
     * @param pane pole obiektu JFrame, na którym umieszczone zostaną elementy
     */
    private void addComponents(Container pane) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(mainPanel);

        JLabel lunarLabel = new JLabel("Lunar Lander", SwingConstants.CENTER);
        lunarLabel.setFont(lunarLabel.getFont().deriveFont(32.0f));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainPanel.add(lunarLabel, c);

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        mainPanel.add(startButton, c);

        rulesButton = new JButton("Zasady");
        rulesButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        mainPanel.add(rulesButton, c);

        resultsButton = new JButton("Najlepsze wyniki");
        resultsButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        mainPanel.add(resultsButton, c);

        closeButton = new JButton("Zamknij");
        closeButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        mainPanel.add(closeButton, c);

        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("resources/local/biglander.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 0;
        c.gridheight = 5;
        c.insets = new Insets(0,0,0,0);
        mainPanel.add(imageLabel, c);
    }

    /**
     * rozpoczęcie działania programu
     */
    public void startUI() {
        mainWindow = new JFrame("Lunar Lander");
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setIconImage(new ImageIcon("resources/local/lander.png").getImage());
        addComponents(mainWindow.getContentPane());
        mainWindow.pack();
        mainWindow.setVisible(true);
        GameParameters.parse();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == startButton) {
            try {
                startGame();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if(source == rulesButton) showRules();
        if(source == resultsButton) showResults();
        if(source == closeButton) closeWindow();
    }

    /**
     * rozpoczęcie gry
     */
    private void startGame() {
        System.out.println("Start");
        NickDialog ni = new NickDialog();
        if(ni.showNick(mainWindow) == JOptionPane.OK_OPTION) {
            Scenario sc = new Scenario();
            sc.setNick(ni.nick);
            Thread s = new Thread(sc);
            s.start();
        }
    }

    /**
     * wyświetlenie okna z zasadami gry
     */
    private void showRules() {
        System.out.println("Show rules");
        RulesDialog ru = new RulesDialog();
        ru.showRules(mainWindow);
    }

    /**
     * wyświetlenie okna z listą najlepszych wyników
     */
    private void showResults() {

        ResultsDialog rd = new ResultsDialog();
        rd.showResults(mainWindow);
    }

    /**
     * zamknięcie okien i wyłączenie programu
     */
    private void closeWindow() {
        System.exit(0);
    }

}
