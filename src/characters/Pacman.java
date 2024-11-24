package characters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pacman extends JLabel implements Runnable, KeyListener {

    private final List<ImageIcon> openMouthArray = new ArrayList<>();
    private final List<ImageIcon> closeMouthArray = new ArrayList<>();

    private ImageIcon pacmanUpClosedIcon;
    private ImageIcon pacmanUpOpenIcon;
    private ImageIcon pacmanDownClosedIcon;
    private ImageIcon pacmanDownOpenIcon;
    private ImageIcon pacmanLeftClosedIcon;
    private ImageIcon pacmanLeftOpenIcon;
    private ImageIcon pacmanRightClosedIcon;
    private ImageIcon pacmanRightOpenIcon;

    private int currentDirection;

    private boolean isOpenMouth;
    private boolean running;

    private int x;
    private int y;

    private final int pacmanRow;

    public Pacman(int initialRow) {
        this.pacmanUpClosedIcon = new ImageIcon(new File("Pac-Man/assets/pacManUpClosed.png").getPath());
        this.pacmanUpClosedIcon = new ImageIcon(new File("Pac-Man/assets/pacManUpClosed.png").getPath());
        this.pacmanUpOpenIcon = new ImageIcon(new File("Pac-Man/assets/pacManUpOpen.png").getPath());
        this.pacmanDownClosedIcon = new ImageIcon(new File("Pac-Man/assets/pacManDownClosed.png").getPath());
        this.pacmanDownOpenIcon = new ImageIcon(new File("Pac-Man/assets/pacManDownOpen.png").getPath());
        this.pacmanLeftClosedIcon = new ImageIcon(new File("Pac-Man/assets/pacManLeftClosed.png").getPath());
        this.pacmanLeftOpenIcon = new ImageIcon(new File("Pac-Man/assets/pacManLeftOpen.png").getPath());
        this.pacmanRightClosedIcon = new ImageIcon(new File("Pac-Man/assets/pacManRightClosed.png").getPath());
        this.pacmanRightOpenIcon = new ImageIcon(new File("Pac-Man/assets/pacManRightOpen.png").getPath());


        setIcon(pacmanRightClosedIcon);
        currentDirection = KeyEvent.VK_RIGHT;

        setFocusable(true);
        addKeyListener(this);

        openMouthArray.add(pacmanUpOpenIcon);
        openMouthArray.add(pacmanDownOpenIcon);
        openMouthArray.add(pacmanLeftOpenIcon);
        openMouthArray.add(pacmanRightOpenIcon);

        closeMouthArray.add(pacmanUpClosedIcon);
        closeMouthArray.add(pacmanDownClosedIcon);
        closeMouthArray.add(pacmanLeftClosedIcon);
        closeMouthArray.add(pacmanRightClosedIcon);

        this.pacmanRow = initialRow;
        startAnimation();
    }

    public void startAnimation() {
        running = true;
        Thread animationThread = new Thread(this);
        animationThread.start();
    }

    public void scaleIcons(int tileWidth, int tileHeight) {
        pacmanUpClosedIcon = new ImageIcon(pacmanUpClosedIcon.getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH));
        pacmanUpOpenIcon = new ImageIcon(pacmanUpOpenIcon.getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH));
        pacmanDownClosedIcon = new ImageIcon(pacmanDownClosedIcon.getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH));
        pacmanDownOpenIcon = new ImageIcon(pacmanDownOpenIcon.getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH));
        pacmanLeftClosedIcon = new ImageIcon(pacmanLeftClosedIcon.getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH));
        pacmanLeftOpenIcon = new ImageIcon(pacmanLeftOpenIcon.getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH));
        pacmanRightClosedIcon = new ImageIcon(pacmanRightClosedIcon.getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH));
        pacmanRightOpenIcon = new ImageIcon(pacmanRightOpenIcon.getImage().getScaledInstance(tileWidth, tileHeight, Image.SCALE_SMOOTH));

        closeMouthArray.set(0, pacmanUpClosedIcon);
        closeMouthArray.set(1, pacmanDownClosedIcon);
        closeMouthArray.set(2, pacmanLeftClosedIcon);
        closeMouthArray.set(3, pacmanRightClosedIcon);

        openMouthArray.set(0, pacmanUpOpenIcon);
        openMouthArray.set(1, pacmanDownOpenIcon);
        openMouthArray.set(2, pacmanLeftOpenIcon);
        openMouthArray.set(3, pacmanRightOpenIcon);

        setIcon(pacmanRightClosedIcon); // Ustawienie ikony startowej
    }


    public void setPosition(int x, int y) {
        this.x = x * 35;  // 35 to wymiar Pacmana
        this.y = y * 35;  // 35 to wymiar Pacmana
        setBounds(this.x, this.y, 35, 35); // Ustawienie położenia i rozmiaru Pacmana
    }


    public int getCurrentDirection() {
        return currentDirection;
    }


    @Override
    public void run() {
        while (running) {
            updateDirection();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateDirection() {
        if (isOpenMouth) {
            switch (currentDirection) {
                case KeyEvent.VK_UP:
                    setIcon(openMouthArray.get(0));
                    break;
                case KeyEvent.VK_DOWN:
                    setIcon(openMouthArray.get(1));
                    break;
                case KeyEvent.VK_LEFT:
                    setIcon(openMouthArray.get(2));
                    break;
                case KeyEvent.VK_RIGHT:
                    setIcon(openMouthArray.get(3));
                    break;
            }
        } else {
            switch (currentDirection) {
                case KeyEvent.VK_UP:
                    setIcon(closeMouthArray.get(0));
                    break;
                case KeyEvent.VK_DOWN:
                    setIcon(closeMouthArray.get(1));
                    break;
                case KeyEvent.VK_LEFT:
                    setIcon(closeMouthArray.get(2));
                    break;
                case KeyEvent.VK_RIGHT:
                    setIcon(closeMouthArray.get(3));
                    break;
            }
        }
        isOpenMouth = !isOpenMouth;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            currentDirection = keyCode;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}