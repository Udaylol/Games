package tile;

import main.Screen;
import main.World;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class TileManager {
    Screen screen;
    Tile[] tile;

    public TileManager(Screen screen) {
        this.screen = screen;
        tile = new Tile[6];
        getTileImage();
        World.loadWorldMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));

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
