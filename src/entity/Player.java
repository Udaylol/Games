package entity;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler kh)
    {
        this.gamePanel = gp;
        this.keyHandler = kh;
    }
}
