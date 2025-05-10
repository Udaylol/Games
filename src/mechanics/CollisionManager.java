package mechanics;

import entity.Entity;
import main.Screen;
import main.World;
import tile.TileManager;

public class CollisionManager {

    public boolean willCollide(Entity entity) {
        int hitboxLeftX = entity.worldX + entity.hitbox.x;
        int hitboxRightX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int hitboxTopY = entity.worldY + entity.hitbox.y;
        int hitboxBottomY = entity.worldY + entity.hitbox.y + entity.hitbox.height;

        int leftCol = hitboxLeftX / Screen.TILE_SIZE;
        int rightCol = hitboxRightX / Screen.TILE_SIZE;
        int topRow = hitboxTopY / Screen.TILE_SIZE;
        int bottomRow = hitboxBottomY / Screen.TILE_SIZE;

        int tile1, tile2;

        switch (entity.direction) {
            case 'U' -> {
                int newTopRow = (hitboxTopY - entity.speed) / Screen.TILE_SIZE;
                tile1 = World.map[newTopRow][leftCol];
                tile2 = World.map[newTopRow][rightCol];
            }
            case 'D' -> {
                int newBottomRow = (hitboxBottomY + entity.speed) / Screen.TILE_SIZE;
                tile1 = World.map[newBottomRow][leftCol];
                tile2 = World.map[newBottomRow][rightCol];
            }
            case 'L' -> {
                int newLeftCol = (hitboxLeftX - entity.speed) / Screen.TILE_SIZE;
                tile1 = World.map[topRow][newLeftCol];
                tile2 = World.map[bottomRow][newLeftCol];
            }
            case 'R' -> {
                int newRightCol = (hitboxRightX + entity.speed) / Screen.TILE_SIZE;
                tile1 = World.map[topRow][newRightCol];
                tile2 = World.map[bottomRow][newRightCol];
            }
            default -> { return false; }
        }

        return TileManager.tile[tile1].isSolid || TileManager.tile[tile2].isSolid;
    }
}
