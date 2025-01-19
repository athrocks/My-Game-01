package utilz;

import main.Game;

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
}
