package map;


import characters.Pacman;
import Items.Upgrade;
import characters.Ghost;
import menu.PlayerResult;
import menu.PlayerSavingMechanics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Random;

public class Map extends JFrame {

    private final int[][] currentMap;

    private final int[][] map1 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private final int[][] map2 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 3, 1, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 1, 1, 1, 1, 3, 1, 3, 1, 1, 1, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private final int[][] map3 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 1, 3, 1, 3, 3, 1, 1, 1, 3, 3, 1, 3, 1, 1, 3, 1},
            {1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 1, 3, 1, 1, 3, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 3, 1, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 3, 1},
            {1, 3, 3, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 1, 3, 1, 1, 3, 1, 3, 1, 1, 3, 1, 3, 3, 3, 1},
            {1, 3, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 3, 1, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 1, 3, 1, 1, 3, 1, 1, 3, 1},
            {1, 3, 1, 1, 3, 1, 3, 3, 1, 1, 1, 3, 3, 1, 3, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private final int[][] map4 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 1, 1, 1, 3, 1, 1, 1, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 3, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 3, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 1, 1, 1, 3, 1, 1, 1, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private final int[][] map5 = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 3, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 3, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 1, 1, 3, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 3, 1, 3, 1},
            {1, 3, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1},
            {1, 3, 1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1, 3, 1},
            {1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1},
            {1, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3, 1},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    private final int MAP_WIDTH;
    private final int MAP_HEIGHT;
    private final int TILE_SIZE;


    //LABELS
    private final JLabel mapTitle;
    private final JLabel scoreLabel;
    private final JLabel livesLabel;
    private final JLabel timeLabel;


    //PANELS

    private JPanel cell;
    private final JPanel[][] grid;
    private final JPanel topPanel;
    private final JPanel bottomPanel;


    //FONTS
    private final Font scoreboardFont;
    private final Font mapTitleFont;


    //SAVING FOR PlayerResult
    private String userName;
    private int points = 0;


    //GAME MECHANICS
    private int lives = 3;
    private volatile int timeSeconds = 0;
    private int remainingPoints = 170;
    private Upgrade currentUpgrade;


    //CHARACTERS & POSITIONS
    private final Pacman pacman;
    private final Ghost[] ghosts;
    private int pacmanX = 1;
    private int pacmanY = 1;

    public Map(int map) {
        switch (map) {
            case 0:
                currentMap = map1;
                break;
            case 1:
                currentMap = map2;
                break;
            case 2:
                currentMap = map3;
                break;
            case 3:
                currentMap = map4;
                break;
            case 4:
                currentMap = map5;

                break;
            default:
                throw new IllegalArgumentException("Invalid map choice");
        }

        int rows = currentMap.length;
        int columns = currentMap[0].length;

        //WINDOW SETTINGS
        setTitle("Map " + rows + " x " + columns);
        int WINDOW_SIZE = 600;
        TILE_SIZE = WINDOW_SIZE / rows;
        MAP_WIDTH = WINDOW_SIZE / TILE_SIZE;
        MAP_HEIGHT = WINDOW_SIZE / TILE_SIZE;
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //TOP AND BOTTOM PANELS
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 0));
        bottomPanel = new JPanel();

        //FONTS
        mapTitleFont = loadFont("Pac-Man/assets/namco.ttf");
        scoreboardFont = loadFont("Pac-Man/assets/emulogic.ttf");

        //TOP PANEL
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(600, 50));
        mapTitle = new JLabel(columns + " x " + rows);
        mapTitle.setFont(mapTitleFont.deriveFont(Font.BOLD, 40));
        mapTitle.setForeground(Color.YELLOW);
        topPanel.add(mapTitle);

        // TIME LABEL
        timeLabel = new JLabel("Time: 0");
        assert scoreboardFont != null;
        timeLabel.setFont(scoreboardFont.deriveFont(Font.BOLD, 20));
        timeLabel.setForeground(Color.YELLOW);
        topPanel.add(timeLabel);

        // TIME COUNTING THREAD
        Thread timeThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    timeSeconds++;
                    SwingUtilities.invokeLater(() -> timeLabel.setText("Time: " + timeSeconds));
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        timeThread.start();

        //BOTTOM PANEL - 1) SCORE   2) LIVES
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setPreferredSize(new Dimension(600, 50));

        //1) SCORE
        scoreLabel = new JLabel("Score: " + points);
        scoreLabel.setFont(scoreboardFont.deriveFont(Font.BOLD, 20));
        scoreLabel.setForeground(Color.YELLOW);
        bottomPanel.add(scoreLabel);

        //2) LIVES
        livesLabel = new JLabel("   Lives: " + lives);
        livesLabel.setFont(scoreboardFont.deriveFont(Font.BOLD, 20));
        livesLabel.setForeground(Color.YELLOW);
        bottomPanel.add(livesLabel);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);


        //MAIN PANEL WITH GOOD SCALEED CELLS AND SIZE
        JPanel gridPanel = new JPanel(new GridLayout(MAP_HEIGHT, MAP_WIDTH));
        grid = new JPanel[MAP_HEIGHT][MAP_WIDTH];

        //MAP INICIALIZATION
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                cell = new JPanel();
                cell.setLayout(new GridBagLayout());
                cell.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (currentMap[y][x] == 1) {
                    cell.setBackground(Color.BLUE);
                } else if (currentMap[y][x] == 3) {
                    cell.setBackground(Color.BLACK);
                    JPanel pointPanel = new JPanel();
                    pointPanel.setBackground(Color.WHITE);
                    pointPanel.setPreferredSize(new Dimension(TILE_SIZE / 5, TILE_SIZE / 5));
                    cell.add(pointPanel, new GridBagConstraints());
                }
                gridPanel.add(cell);
                grid[y][x] = cell;
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        //GHOSTS INIZJALIZATION
        ghosts = new Ghost[3];

        ghosts[0] = new Ghost(11, 11, Ghost.GhostColor.BLUE, currentMap, MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, grid);
        ghosts[1] = new Ghost(5, 5, Ghost.GhostColor.PINK, currentMap, MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, grid);
        ghosts[2] = new Ghost(7, 8, Ghost.GhostColor.GREEN, currentMap, MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, grid);

        // ADD GHOST TO MAP
        for (Ghost ghost : ghosts) {
            grid[ghost.getY()][ghost.getX()].add(ghost.getGhostLabel());
        }

        // RUNNING THREADS OF GHOSTS
        for (Ghost ghost : ghosts) {
            Thread ghostThread = new Thread(ghost);
            ghostThread.start();
        }

        // PACMAN INIZJALIZATION AND SETINGS
        pacman = new Pacman(pacmanX);
        pacman.setSize(TILE_SIZE, TILE_SIZE);

        switch (map) {

            case 0:
                pacman.scaleIcons(45, 45);
                for (Ghost ghost : ghosts)
                    ghost.scaleIcons(45, 45);
                break;
            case 1:
                pacman.scaleIcons(40, 40);
                for (Ghost ghost : ghosts)
                    ghost.scaleIcons(45, 45);
                break;
            case 2:
                pacman.scaleIcons(35, 35);
                for (Ghost ghost : ghosts)
                    ghost.scaleIcons(45, 45);
                break;
            case 3:
                pacman.scaleIcons(30, 30);
                for (Ghost ghost : ghosts)
                    ghost.scaleIcons(40, 40);
                break;
            case 4:
                pacman.scaleIcons(25, 25);
                for (Ghost ghost : ghosts)
                    ghost.scaleIcons(35, 35);
                break;
        }

        pacman.setPosition(pacmanX, pacmanY);
        updatePacmanPosition();

        Thread pacmanThread = new Thread(() -> {
            while (true) {
                movePacman();
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        pacmanThread.start();
        setFocusable(true);
        requestFocusInWindow();

        //ESC- keyListener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    int choice = JOptionPane.showConfirmDialog(Map.this, "Czy na pewno chcesz zamknąć grę?", "Potwierdzenie zamknięcia", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                }
            }
        });

        addKeyListener(pacman);

        // UPGRADE THREAD
        Thread upgradeDroppingThread = getThread();
        upgradeDroppingThread.start();

    }

    private Thread getThread() {
        Ghost[] finalGhosts = ghosts;
        //EVERY 5 SECOUNDS
        return new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(5000); //EVERY 5 SECOUNDS
                    int ghostIndex = random.nextInt(finalGhosts.length);
                    Upgrade upgrade = finalGhosts[ghostIndex].dropUpgrade();
                    placeUpgrade(upgrade);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //DROP UPGRADE ON MAP
    private void placeUpgrade(Upgrade upgrade) {
        int x = upgrade.getX();
        int y = upgrade.getY();

        currentUpgrade = upgrade;
        grid[y][x].add(currentUpgrade);
        grid[y][x].revalidate();
        grid[y][x].repaint();
    }


    private void updatePacmanPosition() {
        JPanel previousCell = grid[pacmanY][pacmanX];
        previousCell.removeAll();
        previousCell.repaint();

        if (currentMap[pacmanY][pacmanX] == 3) {
            currentMap[pacmanY][pacmanX] = 0;
            points += 100;
            scoreLabel.setText("Score: " + points);
            remainingPoints--;

            //PACMAN COLLECTED WHOLE POINTS
            if (remainingPoints == 0) {
                restartMap();
                resetPacman();
                resetGhosts();
                lives++;
                livesLabel.setText("Lives: " + lives);
            }

        } else if (currentUpgrade != null && pacmanX == currentUpgrade.getX() && pacmanY == currentUpgrade.getY()) {
            lives++;
            livesLabel.setText("Lives: " + lives);
            grid[currentUpgrade.getY()][currentUpgrade.getX()].remove(currentUpgrade);
            grid[currentUpgrade.getY()][currentUpgrade.getX()].revalidate();
            grid[currentUpgrade.getY()][currentUpgrade.getX()].repaint();
            currentUpgrade = null;
        }

        grid[pacmanY][pacmanX].add(pacman);
        grid[pacmanY][pacmanX].revalidate();
        repaint();


        for (Ghost ghost : ghosts) {
            if (pacmanX == ghost.getX() && pacmanY == ghost.getY()) {
                lives--;
                livesLabel.setText("    Lives: " + lives);
                if (lives <= 0) {
                    userName = JOptionPane.showInputDialog(null, "Nickname: ", "Score saveing", JOptionPane.PLAIN_MESSAGE);
                    if (userName == null) {
                        userName = "anonymous";
                    } else if (remainingPoints == points / 100) {
                        restartMap();
                    }
                    dispose();
                    saveRanking(userName, points);
                    return;
                } else {
                    resetPacman();
                    resetGhosts();
                }
            }
        }
    }


    private void movePacman() {
        int nextX = pacmanX;
        int nextY = pacmanY;

        switch (pacman.getCurrentDirection()) {
            case KeyEvent.VK_UP:
                nextY--;
                break;
            case KeyEvent.VK_DOWN:
                nextY++;
                break;
            case KeyEvent.VK_LEFT:
                nextX--;
                break;
            case KeyEvent.VK_RIGHT:
                nextX++;
                break;
        }

        if (nextX >= 0 && nextX < MAP_WIDTH && nextY >= 0 && nextY < MAP_HEIGHT && currentMap[nextY][nextX] != 1) {
            pacmanX = nextX;
            pacmanY = nextY;
            updatePacmanPosition();
        }
    }

    private void resetPacman() {
        pacmanX = 1;
        pacmanY = 1;
        pacman.setPosition(pacmanX, pacmanY);
        updatePacmanPosition();
    }

    private void resetGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.resetPosition(ghost.getInitialX(), ghost.getInitialY());
            grid[ghost.getInitialY()][ghost.getInitialX()].add(ghost.getGhostLabel());
        }
    }

    private void restartMap() {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                if (currentMap[y][x] == 0) {
                    grid[y][x].removeAll();
                    grid[y][x].setBackground(Color.BLACK);
                    JPanel pointPanel = new JPanel();
                    pointPanel.setBackground(Color.WHITE);
                    pointPanel.setPreferredSize(new Dimension(TILE_SIZE / 5, TILE_SIZE / 5));
                    grid[y][x].add(pointPanel, new GridBagConstraints());
                }
            }
        }
        scoreLabel.setText("Score: " + points);
    }

    private Font loadFont(String path) {
        try (InputStream is = new FileInputStream(path)) {
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveRanking(String userName, int points) {
        PlayerResult result = new PlayerResult(userName, points);
        PlayerSavingMechanics.saveResult(result);
    }
}
