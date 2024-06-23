package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MenuWindow extends JFrame {

    private JLabel title;
    private JPanel menuOptions;
    private JButton newGameButton;
    private JButton highScoresButton;
    private JButton exitButton;
    private Font pacManFont = loadFont("assets/PAC-FONT.TTF");
    private Font menuFont = loadFont("assets/emulogic.ttf");

    public MenuWindow() {
        // Setup title
        title = new JLabel("Pac-Man", SwingConstants.CENTER);
        title.setFont(pacManFont.deriveFont(Font.BOLD, 70));
        title.setForeground(Color.YELLOW);
        title.setOpaque(true);
        title.setBackground(Color.BLACK);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(125, 0, 0, 0));
        titlePanel.add(title, BorderLayout.CENTER);

        // Setup menu options
        menuOptions = new JPanel();
        menuOptions.setBackground(Color.BLACK);
        menuOptions.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        newGameButton = createCustomButton("New Game", 0);
        menuOptions.add(newGameButton, gbc);

        gbc.gridy = 1;
        highScoresButton = createCustomButton("High Scores", 1);
        menuOptions.add(highScoresButton, gbc);

        gbc.gridy = 2;
        exitButton = createCustomButton("Exit", 2);
        menuOptions.add(exitButton, gbc);

        // Setup frame
        this.getContentPane().setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(menuOptions, BorderLayout.CENTER);
        this.setTitle("Pac-Man");
        this.setSize(720, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private JButton createCustomButton(String text, int index) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setForeground(Color.YELLOW);
        button.setFont(menuFont.deriveFont(Font.BOLD, 35));
        addHoverEffect(button, index);
        return button;
    }

    private void addHoverEffect(JButton button, int index) {
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = button.getForeground();

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(originalColor);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleButtonHover(index);
            }
        });
    }

    private void handleButtonHover(int index) {
        switch (index) {
            case 0:
                displayMaps();
                break;
            case 1:
                displayHighScores();
                break;
            case 2:
                System.exit(0);
                break;
        }
    }

    private void displayMaps() {
        JFrame mapMenuFrame = new JFrame("Map menu");
        MapMenu mapMenu = new MapMenu();
        mapMenuFrame.add(mapMenu);
        mapMenuFrame.pack();
        mapMenuFrame.setLocationRelativeTo(null);
        mapMenuFrame.setVisible(true);
    }

    private void displayHighScores() {
        JFrame highScoresFrame = new JFrame("High Scores");
        HighScoresList highScoresListPanel = new HighScoresList();
        highScoresFrame.add(highScoresListPanel);
        highScoresFrame.setSize(400, 300);
        highScoresFrame.setLocationRelativeTo(null);
        highScoresFrame.setVisible(true);
    }

    private Font loadFont(String path) {
        try (InputStream is = new FileInputStream(new File(path))) {
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
