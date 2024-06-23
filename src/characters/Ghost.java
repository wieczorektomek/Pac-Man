package characters;

import Items.Upgrade;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class Ghost implements Runnable {
    public enum GhostColor {
        BLUE, GREEN, PINK
    }

    private final int[][] map;
    private final int MAP_WIDTH;
    private final int MAP_HEIGHT;
    private final JPanel[][] grid;
    private ImageIcon ghostImage;
    private final JLabel ghostLabel;

    private final int TILE_SIZE;
    private int x;
    private int y;
    private final int initialX;
    private final int initialY;
    private static final Random random = new Random();

    public Ghost(int x, int y, GhostColor ghostColor, int[][] map, int MAP_WIDTH, int MAP_HEIGHT, int TILE_SIZE, JPanel[][] grid) {
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
        this.map = map;
        this.MAP_WIDTH = MAP_WIDTH;
        this.MAP_HEIGHT = MAP_HEIGHT;
        this.TILE_SIZE = TILE_SIZE;
        this.grid = grid;

        switch (ghostColor) {
            case BLUE:
                ghostImage = new ImageIcon(new File("assets/blueGhost.png").getPath());
                break;
            case GREEN:
                ghostImage = new ImageIcon(new File("assets/greenGhost.png").getPath());
                break;
            case PINK:
                ghostImage = new ImageIcon(new File("assets/pinkGhostIcon.png").getPath());
                break;
        }

        ghostLabel = new JLabel(ghostImage);
        ghostLabel.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
    }

    @Override
    public void run() {
        while (true) {
            moveGhost();
            try {
                Thread.sleep(500); //GHOST MOVEMENT SPEED
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //DROPING ON WHOLE MAP, NOT BEHIND THE GHOSTS :/
    public Upgrade dropUpgrade() {
        int newX, newY;
        do {
            newX = random.nextInt(map.length);
            newY = random.nextInt(map[0].length);
        } while (map[newY][newX] != 3); // PATH OR POINT

        return new Upgrade(newX, newY, TILE_SIZE);
    }

    private void moveGhost() {
        int previousX = x;
        int previousY = y;

        Random random = new Random();
        int moveChance = random.nextInt(4);

        switch (moveChance) {
            case 0: // UP
                if (y > 0 && map[y - 1][x] != 1) y--;
                break;
            case 1: // DOWN
                if (y < MAP_HEIGHT - 1 && map[y + 1][x] != 1) y++;
                break;
            case 2: // LEFT
                if (x > 0 && map[y][x - 1] != 1) x--;
                break;
            case 3: // RIGHT
                if (x < MAP_WIDTH - 1 && map[y][x + 1] != 1) x++;
                break;
        }

        //PREVIOUS repaint, CURRENT update
        updateGhostPosition(previousX, previousY);
    }

    public void updateGhostPosition(int previousX, int previousY) {
        //PREVIOUS
        JPanel previousCell = grid[previousY][previousX];
        previousCell.removeAll();
        previousCell.repaint();
        previousCell.revalidate();

        if (map[previousY][previousX] == 3) {
            JPanel pointPanel = new JPanel();
            pointPanel.setBackground(Color.WHITE);
            pointPanel.setPreferredSize(new Dimension(TILE_SIZE / 5, TILE_SIZE / 5));
            previousCell.add(pointPanel, new GridBagConstraints());
        }
        previousCell.revalidate();
        previousCell.repaint();

        //CURRENT
        JPanel currentCell = grid[y][x];
        currentCell.add(ghostLabel);
        currentCell.revalidate();
        currentCell.repaint();
    }

//USEFUL FOR RESTARTING MAP AFTER COLLECTING WHOLE POINTS
    public void resetPosition(int initialX, int initialY) {
        this.x = initialX;
        this.y = initialY;
        updateGhostPosition(initialX, initialY);
    }



    //SCALING
    public void scaleIcons(int width, int height) {
        Image image = ghostImage.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ghostImage = new ImageIcon(scaledImage);
        ghostLabel.setIcon(ghostImage);
    }

    public JLabel getGhostLabel() {
        return ghostLabel;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
