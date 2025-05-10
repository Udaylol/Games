package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage[] up;
    public BufferedImage[] down;
    public BufferedImage[] left;
    public BufferedImage[] right;
    public char direction;

    public int spriteCnt = 0;
    public int spriteIdx = 0;

    public Rectangle hitbox;
    public boolean isColliding = false;
}
