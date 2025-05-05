package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48px
    final int maxScreenRow = 12;
    final int maxScreenCol = 16;
    final int screenHeight = maxScreenRow * tileSize; // 576px
    final int screenWidth = maxScreenCol * tileSize; // 768px
    final int FPS = 60;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(this, keyHandler);


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1e9 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {

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
        player.draw(g2);
        g2.dispose();
    }
}
