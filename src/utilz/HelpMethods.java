package utilz;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HelpMethods {

    public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {

        // 4 corners of rectangle
        if (!isSolid(x, y, lvlData)) {
            if (!isSolid(x + width, y + height, lvlData)) {
                if (!isSolid(x + width, y, lvlData)) {
                    if (!isSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // if isSolid Dont go here
    private static boolean isSolid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= Game.GAME_WIDTH) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        // 12th sprite in outside_sprites.png is invisible and can be pass through
        if (value >= 48 || value < 0 || value != 11) {
            return true;
        }

        return false;
    }

    public static float getEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {

        int currTile = (int) (hitBox.x / Game.TILES_SIZE);

        if (xSpeed > 0) {
            // Player Tile Colliding with Right Tile
            int tileXPos = currTile * Game.TILES_SIZE;
            int xOffSet = (int) (Game.TILES_SIZE - hitBox.width);
            return tileXPos + xOffSet - 1;
        } else {
            // Left Collision
            return currTile * Game.TILES_SIZE;
        }
    }

    public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currTile = (int) (hitBox.y / Game.TILES_SIZE);

        if (airSpeed > 0) {
            // Falling - touching Floor
            int tileYPos = currTile * Game.TILES_SIZE;
            int yOffSet = (int) (Game.TILES_SIZE - hitBox.height);
            return tileYPos + yOffSet - 1;
        } else {
            // Jumping - Touching Roof
            return currTile * Game.TILES_SIZE;
        }
    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
        // Check pixel below bottomLeft and bottomRight
        if (!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData)) {
            if (!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData)) {
                return false;
            }
        }

        return true;
    }
}
