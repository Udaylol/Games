package tile;

import main.Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class TileManager {
    Screen screen;
    Tile[] tile;
    int[][] map;

    public TileManager(Screen screen) {
        this.screen = screen;
        tile = new Tile[6];
        map = new int[screen.ROWS][screen.COLS];
        getTileImage();
        loadTileMap();
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

    public void loadTileMap() {

        try {
            InputStream is = getClass().getResourceAsStream("/maps/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < screen.ROWS; row++) {
                String line = br.readLine();
                String[] tokens = line.trim().split("\\s+");
                for (int col = 0; col < screen.COLS; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Error while loading map");
        } catch (Exception e) {
            System.err.println("Invalid map format");
        }
    }


    public void draw(Graphics2D g2) {
        int x, y, tileNum;
        for (int i = 0; i < screen.COLS; i++) {
            for (int j = 0; j < screen.ROWS; j++) {
                x = i * screen.TILE_SIZE;
                y = j * screen.TILE_SIZE;
                tileNum = map[j][i];
                g2.drawImage(tile[tileNum].image, x, y, screen.TILE_SIZE, screen.TILE_SIZE, null);
            }
        }
    }
}
