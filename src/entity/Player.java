package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler kh)
    {
        this.gamePanel = gp;
        this.keyHandler = kh;
        setDefaultValues();
        getPlayerImage();
    }
    public void getPlayerImage()
    {
        try {
            InputStream is = getClass().getResourceAsStream("/boy_up_1.png");
            if (is == null) {
                System.out.println("Could not load: /boy_up_1.png");
            } else {
                up1 = ImageIO.read(is);
                System.out.println("Image loaded successfully: /boy_up_1.png");
            }
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/boy_right_2.png"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void setDefaultValues() {
        x = y = 100;
        speed = 4;
        direction = "down";
    }
    public void update()
    {
        if (keyHandler.upPressed) {
            direction = "up";
            this.y -= speed;
        } else if (keyHandler.downPressed) {
            direction = "down";
            this.y += speed;
        } else if (keyHandler.leftPressed) {
            direction = "left";
            this.x -= speed;
        } else if (keyHandler.rightPressed) {
            direction = "right";
            this.x += speed;
        }
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image = switch (direction) {
            case "up" -> up1;
            case "down" -> down1;
            case "left" -> left1;
            case "right" -> right1;
            default -> null;
        };
        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
