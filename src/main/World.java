package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static main.Screen.TILE_SIZE;

public class World {

    private World() {
        // Prevent instantiation using private constructor
    }
    // WORLD SETTINGS
    public static final int ROWS = 50;
    public static final int COLS = 50;
    private static final int WIDTH = COLS * TILE_SIZE;
    private static final int HEIGHT = ROWS * TILE_SIZE;
    public static int[][] map;

    public static void loadWorldMap() {
        int rows = ROWS, cols = ROWS;
        map = new int[rows][cols];
        try {
            InputStream is = World.class.getResourceAsStream("/maps/worldMap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            for (int i = 0; i < rows; i++) {
                String line = br.readLine();
                String[] tokens = line.trim().split("\\s+");
                for (int j = 0; j < cols; j++) {
                    map[i][j] = Integer.parseInt(tokens[j]);
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Error while loading map");
        } catch (Exception e) {
            System.err.println("Invalid map format");
        }
    }
}
