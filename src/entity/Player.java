package entity;

import main.Screen;
import mechanics.KeyManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Player extends Entity {
    KeyManager key;
    Screen screen;
    public final int screenX;
    public final int screenY;

    // CONSTRUCTOR
    public Player(KeyManager key, Screen screen) {
        this.key = key;
        this.screen = screen;

        this.screenX = Screen.WIDTH / 2 - Screen.TILE_SIZE / 2;
        this.screenY = Screen.HEIGHT / 2 - Screen.TILE_SIZE / 2;

        this.setDefaultValues();
        this.getPlayerImage();

        hitbox = new Rectangle(10, 16, 28, 26);
    }

    // PUBLIC METHODS
    public void setDefaultValues() {
        worldX = Screen.TILE_SIZE * 23;
        worldY = Screen.TILE_SIZE * 21;
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
        if (!key.upPressed && !key.downPressed && !key.leftPressed && !key.rightPressed) {
            return;
        }
        if (key.upPressed) {
            direction = 'U';
        } else if (key.downPressed) {
            direction = 'D';
        } else if (key.leftPressed) {
            direction = 'L';
        } else {
            direction = 'R';
        }
        isColliding = screen.collisionManager.willCollide(this);
        if (!isColliding) {
            switch (direction) {
                case 'U' -> worldY -= speed;
                case 'D' -> worldY += speed;
                case 'L' -> worldX -= speed;
                case 'R' -> worldX += speed;
            }
        }
        updateSprite();
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case 'U' -> up[spriteIdx];
            case 'D' -> down[spriteIdx];
            case 'L' -> left[spriteIdx];
            case 'R' -> right[spriteIdx];
            default -> null;
        };
        g2.drawImage(image, screenX, screenY, Screen.TILE_SIZE, Screen.TILE_SIZE, null);
        g2.setColor(Color.RED);

        // Calculate the screen position of the hitbox
        int hitboxX = screenX + hitbox.x;
        int hitboxY = screenY + hitbox.y;

        // Draw the hitbox rectangle (outline)
        g2.drawRect(hitboxX, hitboxY, hitbox.width, hitbox.height);
    }

    private void updateSprite() {
        spriteCnt = (spriteCnt + 1) % 20;
        spriteIdx = (spriteCnt < 10) ? 0 : 1;
    }
}
