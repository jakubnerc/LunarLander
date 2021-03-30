import application.MainWindow;

public class LunarLander {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        javax.swing.SwingUtilities.invokeLater(mainWindow::startUI);
    }
}
