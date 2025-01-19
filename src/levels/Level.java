package levels;

/**
 * The Level class represents a game level, containing the level data
 * in the form of a 2D grid. Each cell in the grid corresponds to a specific
 * tile in the level, and this class provides access to the level's data.
 */
public class Level {

    private int[][] lvlData;

    /**
     * Constructs a new Level object with the provided level data.
     *
     * @param lvlData A 2D array representing the level's tile data.
     *                Each element in the array represents a specific tile type.
     */
    public Level(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    /**
     * Retrieves the index of the sprite at the specified coordinates.
     * This index corresponds to a specific tile in the sprite sheet.
     *
     * @param x The x-coordinate of the tile (horizontal position).
     * @param y The y-coordinate of the tile (vertical position).
     *
     * @return The sprite index corresponding to the tile at the specified coordinates.
     */
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }
}
