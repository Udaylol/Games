package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize*scale; // 48px
    final int maxScreenRow = 12;
    final int maxScreenCol = 16;
    final int screenHeight = maxScreenRow*tileSize; // 576px
    final int screenWidth = maxScreenCol*tileSize; // 768px

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }
}
