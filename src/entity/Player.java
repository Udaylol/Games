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

    // CONSTRUCTOR
    public Player(Screen screen, KeyHandler key) {
        this.screen = screen;
        this.key = key;
        this.setDefaultValues();
        this.getPlayerImage();
    }

    // PUBLIC METHODS
    public void setDefaultValues() {
        x = y = 100;
        speed = 2;
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
            direction = 'U';
            y -= speed;
        } else if (key.downPressed) {
            direction = 'D';
            y += speed;
        } else if (key.leftPressed) {
            direction = 'L';
            x -= speed;
        } else if (key.rightPressed) {
            direction = 'R';
            x += speed;
        }
        if (key.upPressed || key.downPressed || key.leftPressed || key.rightPressed) {
            spriteCnt = (spriteCnt + 1) % 20;
            spriteIdx = (spriteCnt < 10)? 0: 1;
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
        g2.drawImage(image, x, y, screen.TILE_SIZE, screen.TILE_SIZE, null);
    }
}
