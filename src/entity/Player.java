package entity;

import main.Screen;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Player extends Entity {
    Screen screen;
    KeyHandler key;

    public final int screenX;
    public final int screenY;

    // CONSTRUCTOR
    public Player(Screen screen, KeyHandler key) {
        this.screen = screen;
        this.key = key;

//        this.screenX = screen.WIDTH/2 - screen.TILE_SIZE/2;
//        this.screenY = screen.HEIGHT/2 - screen.TILE_SIZE/2;

        this.screenX = screen.WIDTH/2 - screen.TILE_SIZE/2;
        this.screenY = screen.HEIGHT/2 - screen.TILE_SIZE/2;

        this.setDefaultValues();
        this.getPlayerImage();
    }

    // PUBLIC METHODS
    public void setDefaultValues() {
        worldX = screen.TILE_SIZE * 23;
        worldY = screen.TILE_SIZE * 21;
        speed = 4;
        direction = 'D';
        up = new BufferedImage[2];
        down = new BufferedImage[2];
        left = new BufferedImage[2];
        right = new BufferedImage[2];
    }

    public void getPlayerImage() {
        try {
            up[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
            up[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
            down[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
            down[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
            left[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
            left[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
            right[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
            right[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));
        } catch (Exception e) {
            System.err.println("Failed to load player sprites");
        }
    }

    public void update() {
        if (key.upPressed) {
            moveUp();
            updateSprite();
        } else if (key.downPressed) {
            moveDown();
            updateSprite();
        } else if (key.leftPressed) {
            moveLeft();
            updateSprite();
        } else if (key.rightPressed) {
            moveRight();
            updateSprite();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case 'U' -> up[spriteIdx];
            case 'D' -> down[spriteIdx];
            case 'L' -> left[spriteIdx];
            case 'R' -> right[spriteIdx];
            default -> null;
        };
        g2.drawImage(image, screenX, screenY, screen.TILE_SIZE, screen.TILE_SIZE, null);
    }

    private void moveUp() {
        direction = 'U';
        worldY -= speed;
    }
    private void moveDown() {
        direction = 'D';
        worldY += speed;
    }
    private void moveLeft() {
        direction = 'L';
        worldX -= speed;
    }
    private void moveRight() {
        direction = 'R';
        worldX += speed;
    }
    private void updateSprite() {
        spriteCnt = (spriteCnt + 1) % 20;
        spriteIdx = (spriteCnt < 10) ? 0 : 1;
    }
}
