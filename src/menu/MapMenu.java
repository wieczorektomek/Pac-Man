package menu;

import map.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MapMenu extends JPanel {

    int index;

    public MapMenu() {
        this.setPreferredSize(new Dimension(720, 300));
        this.setBackground(Color.BLACK);

        Font checkBoxFont = loadFont("Pac-Man/assets/emulogic.ttf");

        JRadioButton[] jRadioButtons = new JRadioButton[5];
        jRadioButtons[0] = new JRadioButton("Map 13x13");
        jRadioButtons[1] = new JRadioButton("Map 17x17 ");
        jRadioButtons[2] = new JRadioButton("Map 19x19 ");
        jRadioButtons[3] = new JRadioButton("Map 21x21 ");
        jRadioButtons[4] = new JRadioButton("Map 25x25 ");

        JPanel mapChoices = new JPanel();
        ButtonGroup choicesGroup = new ButtonGroup();

        for (JRadioButton jRadioButton : jRadioButtons) {
            assert checkBoxFont != null;
            jRadioButton.setFont(checkBoxFont.deriveFont(Font.BOLD, 15));
            mapChoices.add(jRadioButton);
            choicesGroup.add(jRadioButton);
            mapChoices.add(jRadioButton);
            jRadioButton.setOpaque(false);
            jRadioButton.setForeground(Color.WHITE);
            jRadioButton.setFocusable(false);
        }



        mapChoices.setLayout(new FlowLayout());
        mapChoices.setBackground(Color.BLACK);

        JButton confirmMapButton = new JButton("PLAY");
        confirmMapButton.setContentAreaFilled(false);
        confirmMapButton.setBorderPainted(false);
        confirmMapButton.setFocusPainted(false);
        confirmMapButton.setForeground(Color.YELLOW);
        confirmMapButton.setFont(checkBoxFont.deriveFont(Font.BOLD, 35));


        confirmMapButton.addMouseListener(new MouseAdapter() {
            final Color originalColor = confirmMapButton.getForeground();
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                switch (index) {

                    case 0 -> {
                        Map gameMap = new Map(0);
                        gameMap.setVisible(true);
                    }

                    case 1 -> {
                        Map gameMap = new Map(1);
                        gameMap.setVisible(true);
                    }

                    case 2 -> {
                        Map gameMap = new Map(2);
                        gameMap.setVisible(true);
                    }
                    case 3 -> {
                        Map gameMap = new Map(3);
                        gameMap.setVisible(true);
                    }
                    case 4 -> {
                        Map gameMap = new Map(4);
                        gameMap.setVisible(true);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                confirmMapButton.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                confirmMapButton.setForeground(originalColor);
            }
        });


        confirmMapButton.addActionListener(e -> {

            for (int i = 0; i < jRadioButtons.length; i++) {
                if (jRadioButtons[i].isSelected()) {
                    index = i;
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(720, 150));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(confirmMapButton);

        this.setLayout(new BorderLayout());
        this.add(mapChoices, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.PAGE_END);


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
