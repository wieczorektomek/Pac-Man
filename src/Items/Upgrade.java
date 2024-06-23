package Items;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Upgrade extends JPanel {
    private final JLabel[] upgradeLabels;
    private int x;
    private int y;
    private int TILE_SIZE;

    public Upgrade(int x, int y, int TILE_SIZE) {
        this.x = x;
        this.y = y;
        this.TILE_SIZE = TILE_SIZE;

        upgradeLabels = new JLabel[5];
        upgradeLabels[0] = createLabel(Color.BLUE);
        upgradeLabels[1] = createLabel(Color.RED);
        upgradeLabels[2] = createLabel(Color.GREEN);
        upgradeLabels[3] = createLabel(Color.YELLOW);
        upgradeLabels[4] = createLabel(Color.ORANGE);


        Random random = new Random();
        int randomIndex = random.nextInt(upgradeLabels.length); //RANDOM UPGRADE
        JLabel selectedLabel = upgradeLabels[randomIndex];
        add(selectedLabel, new GridBagConstraints());
    }




    private JLabel createLabel(Color color) {
        JLabel label = new JLabel();
        label.setOpaque(true);
        label.setBackground(color);
        label.setPreferredSize(new Dimension(TILE_SIZE / 2, TILE_SIZE / 2));
        return label;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
