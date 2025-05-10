package tile;

import main.Screen;
import main.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class TileManager {
    public static Tile[] tile = new Tile[6];
    Screen screen;

    public TileManager(Screen screen) {
        this.screen = screen;
        getTileImage();
        World.loadWorldMap();
    }

    public static void getTileImage() {
        try {
            for (int i = 0; i < tile.length; i++) {
                tile[i] = new Tile();
            }
            tile[0].image = ImageIO.read(Objects.requireNonNull(TileManager.class.getResourceAsStream("/tiles/grass.png")));
            tile[1].image = ImageIO.read(Objects.requireNonNull(TileManager.class.getResourceAsStream("/tiles/wall.png")));
            tile[1].isSolid = true;
            tile[2].image = ImageIO.read(Objects.requireNonNull(TileManager.class.getResourceAsStream("/tiles/water.png")));
            tile[2].isSolid = true;
            tile[3].image = ImageIO.read(Objects.requireNonNull(TileManager.class.getResourceAsStream("/tiles/earth.png")));
            tile[4].image = ImageIO.read(Objects.requireNonNull(TileManager.class.getResourceAsStream("/tiles/tree.png")));
            tile[4].isSolid = true;
            tile[5].image = ImageIO.read(Objects.requireNonNull(TileManager.class.getResourceAsStream("/tiles/sand.png")));

        } catch (IOException e) {
            System.err.println("Failed to load tiles");
        }
    }

    public void draw(Graphics2D g2) {
        int rows = World.ROWS;
        int cols = World.COLS;
        int tileSize = Screen.TILE_SIZE;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int worldX = j * tileSize;
                int worldY = i * tileSize;

                int screenX = worldX - screen.player.worldX + screen.player.screenX;
                int screenY = worldY - screen.player.worldY + screen.player.screenY;

                // Check if the tile is within the screen bounds
                if (screenX + tileSize > 0 && screenX < Screen.WIDTH && screenY + tileSize > 0 && screenY < Screen.HEIGHT) {
                    int tileNum = World.map[i][j];
                    g2.drawImage(tile[tileNum].image, screenX, screenY, tileSize, tileSize, null);
                }
            }
        }
    }
}
