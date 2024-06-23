package maps;


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

public class Map17x17 extends JFrame {

    private final int WINDOW_SIZE = 800;
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
    private int remainingPoints = 152;
    private Upgrade currentUpgrade;


    //CHARACTERS & POSITIONS
    private final Pacman pacman;
    private final Ghost[] ghosts;
    private int pacmanX = 1;
    private int pacmanY = 1;

    private final int[][] map = {
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

    public Map17x17() {

        //FONTS
        mapTitleFont = loadFont("assets/namco.ttf");
        scoreboardFont = loadFont("assets/emulogic.ttf");

        //WINDOW SETTINGS
        setTitle("Map 17x17");
        TILE_SIZE = WINDOW_SIZE / 17;
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

        //TOP PANEL
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(600, 50));
        mapTitle = new JLabel("17 x 17");
        mapTitle.setFont(mapTitleFont.deriveFont(Font.BOLD, 40));
        mapTitle.setForeground(Color.YELLOW);
        topPanel.add(mapTitle);

        // TIME LABEL
        timeLabel = new JLabel("Time: 0");
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


        //GRID WITH GOOD SCALEED CELLS AND SIZE
        JPanel gridPanel = new JPanel(new GridLayout(MAP_HEIGHT, MAP_WIDTH));
        grid = new JPanel[MAP_HEIGHT][MAP_WIDTH];

        //MAP INICIALIZATION
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                cell = new JPanel();
                cell.setLayout(new GridBagLayout());
                cell.setPreferredSize(new Dimension(TILE_SIZE, TILE_SIZE));
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (map[y][x] == 1) {
                    cell.setBackground(Color.BLUE);
                } else if (map[y][x] == 3) {
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

        ghosts[0] = new Ghost(11, 11, Ghost.GhostColor.BLUE, map, MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, grid);
        ghosts[0].scaleIcons(45, 45);

        ghosts[1] = new Ghost(5, 5, Ghost.GhostColor.PINK, map, MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, grid);
        ghosts[1].scaleIcons(45, 45);

        ghosts[2] = new Ghost(7, 9, Ghost.GhostColor.GREEN, map, MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, grid);
        ghosts[2].scaleIcons(45, 45);

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
        pacman.scaleIcons(40, 40);
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

        //THIS KeyListener COULD ALSO BO MOVED TO PACMAN CLASS
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    int choice = JOptionPane.showConfirmDialog(Map17x17.this, "Czy na pewno chcesz zamknąć grę?", "Potwierdzenie zamknięcia", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        dispose();
                    }
                }
            }
        });

        addKeyListener(pacman);

        // UPGRADE THREAD
        //*every new try the object of upgrade is lost (problem with collecting upgrades)
        Thread upgradeDroppingThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(5000); //EVERY 5 SECOUNDS
                    int ghostIndex = random.nextInt(ghosts.length);
                    Upgrade upgrade = ghosts[ghostIndex].dropUpgrade();
                    placeUpgrade(upgrade);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        upgradeDroppingThread.start();

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

        if (map[pacmanY][pacmanX] == 3) {
            map[pacmanY][pacmanX] = 0;
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
//  PAC-MAN COLLISIONS WITH WORLD
        if (nextX >= 0 && nextX < MAP_WIDTH && nextY >= 0 && nextY < MAP_HEIGHT && map[nextY][nextX] != 1) {
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
                if (map[y][x] == 0) {
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
        try (InputStream is = new FileInputStream(new File(path))) {
            return Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //SAVEING RESULT USING Serializable Interface
    private void saveRanking(String userName, int points) {
        PlayerResult result = new PlayerResult(userName, points);
        PlayerSavingMechanics.saveResult(result);
    }
}
