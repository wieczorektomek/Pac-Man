import menu.MenuWindow;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                MenuWindow::new
        );
    }

}