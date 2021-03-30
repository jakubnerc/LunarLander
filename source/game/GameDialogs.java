package game;

import application.RulesDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static configuration.Constants.*;
import static configuration.Texts.*;
import static configuration.GameParameters.*;

/**
 * Klasa obiektów wyświetlających okna dialogowe w trakcie gry
 */
public class GameDialogs {
    /**
     * główne okno gry
     */
    private JFrame parent;

    /**
     * szablon okna dialogowego z predefiniowanymi przyciskami
     */
    private JOptionPane jOptionPane;

    /**
     * ikona lądownika wyświetlana w niektórych oknach dialogowych
     */
    private final ImageIcon landerIcon;

    /**
     * tytuł okna dialogowego
     */
    private String title;

    /**
     * nick nazwa użytkownika
     */
    private String nick;

    /**
     * pozstała liczba żyć
     */
    private int lives;

    /**
     * numer poziomu
     */
    private int levelNumber;

    /**
     * liczba punktów dotychczas zgromadzonych
     */
    private int totalpoints;

    /**
     * liczba punktów zgromadzonych w ostatnim poziomie
     */
    private int points;

    /**
     * true - jeśli nowy wynik jest najlepszy w rankingu, false - w przeciwnym wypadku
     */
    private boolean best;

    /**
     * pozycja wyniku w rankingu
     */
    private int rank;

    /**
     * okno zasad
     */
    private final RulesDialog rulesDialog;

    /**
     * ikona reprezentująca numer poziomu na tle jego motywu przewodniego
     */
    private final BufferedImage levelImage;

    /**
     * kontekst graficzny dla ikony levelImage
     */
    private final Graphics levelGraphics;


    /**
     * konstruktor klasy
     * @param parent główne okno gry, na tle którego generowane są okna dialogowe
     */
    public GameDialogs(JFrame parent) {
        this.parent = parent;
        this.lives = LIVES_MAX;
        this.levelNumber = 1;
        this.totalpoints = 0;
        this.points = 0;
        this.best = true;
        this.rank = 1;
        this.rulesDialog = new RulesDialog();
        this.landerIcon = new ImageIcon("resources/lander.png");
        UIManager.put("OptionPane.cancelButtonText","Menu Główne");
        this.levelImage = new BufferedImage(70, 70, BufferedImage.TYPE_INT_RGB);
        this.levelGraphics = levelImage.getGraphics();
    }




    public void setParent(JFrame parent) {
        this.parent = parent;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void setTotalpoints(int totalpoints) {
        this.totalpoints = totalpoints;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setBest(boolean best) {
        this.best = best;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    /**
     * tworzenie obiektu klasy JPanel, zawierającego główne teksty i elementy okna dialogowego
     * @param text teksty do wyświetlenia w oknie dialogowym
     * @return obiekt JPanel z zawartością
     */
    private JPanel createPanel(String[] text) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        JLabel jLabel1 = new JLabel(text[0]);
        jLabel1.setFont(font);
        c.gridx = 0;
        c.gridy = 0;
        panel.add(jLabel1, c);

        font = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
        JLabel jLabel2 = new JLabel(text[1]);
        jLabel2.setFont(font);
        c.gridx = 0;
        c.gridy = 1;
        jLabel2.setText(text[1]);
        panel.add(jLabel2, c);
        return panel;
    }


    /**
     * generowanie ikony poziomu złożonej z jego numeru na tle motywu przewodniego tego poziomu
     * @return wygenerowana ikona
     */
    private ImageIcon levelIcon() {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, LEVEL_FONT_SIZE);
        FontMetrics metrics = levelGraphics.getFontMetrics(font);
        int x = (70 - metrics.stringWidth(String.valueOf(levelNumber))) / 2;
        int y = ((70 - metrics.getHeight()) / 2) + metrics.getAscent();
        levelGraphics.setFont(font);
        levelGraphics.clearRect(0,0,70,70);
        levelGraphics.setColor(LEVELS_PARAMETERS[levelNumber - 1].THEME_COLOR);
        levelGraphics.fillRect(0, 0, 70, 70);
        levelGraphics.setColor(Color.white);
        levelGraphics.drawString(String.valueOf(levelNumber), x, y);
        return new ImageIcon(levelImage);
    }


    /**
     * ustalenie treści tekstów wyświetlanych na przyciskach okna dialogowego, które zastępują wartości domyślne
     * @param ok tekst zastępujący "ok" w przycisku ok
     * @param yes tekst zastępujący "yes" w przycisku yes
     * @param no tekst zastępujący "no" w przycisku no
     */
    private void setButtons(String ok, String yes, String no) {
        if(!ok.equals("")) UIManager.put("OptionPane.okButtonText",ok);
        if(!yes.equals("")) UIManager.put("OptionPane.yesButtonText",yes);
        if(!no.equals("")) UIManager.put("OptionPane.noButtonText",no);
    }


    /**
     * wyświetlenie okna dialogowego danego typu
     * @param dialogType typ okna dialogowego
     * @return podjęta w oknie decyzja
     */
    public int showDialog(int dialogType) {


        switch (dialogType) {
            case GAMESTART:
                setButtons("","Graj","Instrukcja");
                jOptionPane = new JOptionPane(createPanel(TEXTGAMESTART(nick)),
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, landerIcon);
                title = "Początek gry";
                break;


            case GAMENEXTLEVEL:
                setButtons("Graj", "", "");
                jOptionPane = new JOptionPane(createPanel(TEXTNEXTLEVEL(levelNumber, lives)),
                        JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, levelIcon());
                title = String.format("Poziom %d", levelNumber);
                break;


            case GAMELEVELSUMMARY:
                setButtons("Dalej", "", "");
                jOptionPane = new JOptionPane(createPanel(TEXTLEVELSUMMARY(points, totalpoints, lives)),
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, landerIcon);
                title = "Brawo!";
                break;


            case GAMELEVELREPEAT:
                setButtons("Spróbuj jeszcze raz", "", "");
                jOptionPane = new JOptionPane(createPanel(TEXTLEVELREPEAT(lives)),
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, landerIcon);
                title = "Nie udało się...";
                break;


            case GAMEWIN:
                setButtons("Graj jeszcze raz","","");
                jOptionPane = new JOptionPane(createPanel(TEXTGAMEWIN(nick, totalpoints, best, rank)),
                        JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, landerIcon);
                title = "Gratulacje, wygrałeś!";
                break;


            case GAMEOVER:
                setButtons("Graj od początku","","");
                jOptionPane = new JOptionPane(createPanel(TEXTGAMEOVER(nick, totalpoints)),
                        JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                title = "Przegrałeś!";
                break;


            case GAMEPAUSE:
                setButtons("", "Wznów", "Instrukcja");
                jOptionPane = new JOptionPane(createPanel(TEXTPAUSE(levelNumber)),
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, levelIcon());
                title = "Wstrzymano";
                break;


            default:
                try {
                    throw new Exception("GameDialogs switch wrong argument");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }


        JDialog dialog = jOptionPane.createDialog(parent, title);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.setVisible(true);
        int decision = (int) jOptionPane.getValue();
        while (decision == JOptionPane.NO_OPTION) {
            rulesDialog.showRules(parent);
            dialog.setVisible(true);
            decision = (int) jOptionPane.getValue();
        }
        dialog.dispose();
        return decision;
    }
}
