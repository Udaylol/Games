package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int ORIGINALTILESIZE = 16;
    private final int SCALE = 3;
    public final int TILESIZE = ORIGINALTILESIZE * SCALE; // 48px
    private final int SCREENROWS = 12;
    private final int SCREENCOLS = 16;
    private final int SCREENHEIGHT = SCREENROWS * TILESIZE; // 576px
    private final int SCREENWIDTH = SCREENCOLS * TILESIZE; // 768px

    // FPS
    private final int FPS = 60;

    // INITIALISATION
    Thread game;
    KeyHandler key = new KeyHandler();
    Player player = new Player(this, key);


    public GameScreen() {
        this.setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public void startGameThread() {
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
        player.draw(g2);
        g2.dispose();
    }
}
