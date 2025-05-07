package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16;
    private final int scale = 3;

    public final int TILE_SIZE = originalTileSize * scale; // 48px
    public final int ROWS = 12;
    public final int COLS = 16;
    public final int HEIGHT = ROWS * TILE_SIZE; // 576px
    public final int WIDTH = COLS * TILE_SIZE; // 768px

    // FPS
    private final int FPS = 60;

    // INITIALISATION
    Thread game;
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);
    Player player = new Player(this, keyHandler);


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
        double drawInterval = 1e9 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (game != null) {

            // 1 UPDATE: update information
            update();
            // 2 DRAW: draw screen with updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime / 1000000);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
