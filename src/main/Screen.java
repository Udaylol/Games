package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel implements Runnable {

    // SCREEN SETTINGS
    public static final int TILE_SIZE = 16 * 3; // 16px scaled 3 times for bigger screen (48px)
    public static final int ROWS = 12;
    public static final int COLS = 16;
    public static final int HEIGHT = ROWS * TILE_SIZE; // 576px
    public static final int WIDTH = COLS * TILE_SIZE; // 768px

    // FPS
    private static final int FPS = 60;

    // INITIALISATION
    Thread game;
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);
    public Player player = new Player(keyHandler);

    public Screen() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGame() {
        game = new Thread(this);
        game.start();
    }

    @Override
    public void run() {
        long drawInterval = 1_000_000_000L / FPS;
        long nextDrawTime = System.nanoTime() + drawInterval;

        while (game != null) {
            update();
            repaint();

            long sleepTime = (nextDrawTime - System.nanoTime()) / 1_000_000;
            if (sleepTime < 0) {
                sleepTime = 0;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            nextDrawTime += drawInterval;
        }
    }


    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
