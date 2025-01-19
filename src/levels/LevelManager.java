package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.getLevelData());
    }

    /// Imports the sprites for the level by extracting individual tiles from the level sprite sheet.
    /// The sprite sheet contains 48 tiles in a 12x4 grid, with each tile being 32x32 pixels in size.
    /// A sprite is typically a 2D image or a sequence of images (a sprite sheet) used in a game to represent objects,
    /// characters, or other elements within the game world. For more information on sprites,
    /// see [Sprite Definition](https://en.wikipedia.org/wiki/Sprite_(computer_graphics)).
    /// The sprite sheet is divided into a grid layout where:
    /// - Columns = 12 (calculated as the width of the sprite sheet / tile width = 384 / 32 = 12)
    /// - Rows = 4 (calculated as the height of the sprite sheet / tile height = 128 / 32 = 4)
    /// The tiles are stored in the `levelSprite` array, with each index representing a specific tile.
    /// Mathematical Calculation for extracting tiles:
    /// - Each row contains 12 tiles, so to extract a specific tile:
    ///   - The x coordinate (horizontal position) of the tile is calculated as: `x = i * 32` (where `i` is the column index).
    ///   - The y coordinate (vertical position) of the tile is calculated as: `y = j * 32` (where `j` is the row index).
    /// Each 32x32 tile is extracted using the `getSubimage(x, y, 32, 32)` method from the sprite sheet.
    ///
    /// @see LoadSave#getSpriteAtlas(String)
    private void importOutsideSprites() {
        levelSprite = new BufferedImage[48]; // 12 tiles wide 4 tiles height
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    /// Draws the level by rendering the appropriate tiles on the screen.
    /// The method iterates through the entire level grid, which is defined by the number of tiles
    /// in height (`Game.TILES_IN_HEIGHT`) and width (`Game.TILES_IN_WIDTH`). For each tile,
    /// it determines the sprite index based on the level data and draws the corresponding tile
    /// from the `levelSprite` array onto the screen.
    /// Each tile is rendered at the correct position on the screen by calculating its
    /// pixel coordinates using the tile size (`Game.TILES_SIZE`) and the tile's grid position.
    /// Key Calculations:
    /// - **Tile Index**: The `getSpriteIndex(j, i)` method retrieves the index of the sprite
    ///   to be drawn for the tile at column `j` and row `i` based on the level data.
    /// - **Pixel Coordinates**:
    ///   - **x-coordinate**: `Game.TILES_SIZE * j` (horizontal position in pixels, based on the column index).
    ///   - **y-coordinate**: `Game.TILES_SIZE * i` (vertical position in pixels, based on the row index).
    /// Drawing Logic:
    /// - The `g.drawImage` method is used to render the tile at the calculated coordinates.
    /// - The size of each tile is scaled to `Game.TILES_SIZE x Game.TILES_SIZE`.
    ///
    /// @param g the `Graphics` object used to draw the tiles on the screen
    /// @see Game#TILES_IN_HEIGHT
    /// @see Game#TILES_IN_WIDTH
    /// @see Game#TILES_SIZE
    /// @see Level#getSpriteIndex(int, int)
    public void draw(Graphics g) {
        for (int i = 0; i < Game.TILES_IN_HEIGHT; i++) {
            for (int j = 0; j < Game.TILES_IN_WIDTH; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * j,
                        Game.TILES_SIZE * i, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Level getCurrLevel() {
        return levelOne;
    }

}
