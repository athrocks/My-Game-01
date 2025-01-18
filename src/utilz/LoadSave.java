package utilz;

import main.Game;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class LoadSave {

    public static final String PLAYER_ATLAS = "res/player_sprites.png";
    public static final String LEVEL_ATLAS = "res/outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "res/level_one_data.png";

    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            img = ImageIO.read(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return img;
    }

    /// Retrieves the level data by reading the pixel values from the level sprite sheet.
    /// The method processes the level image and converts the pixel colors into tile values.
    /// These values are stored in a 2D array representing the level layout.
    /// The level_one_data.png is 26*14 in dimension hence,
    /// The image is assumed to contain color-coded information for different tile types.
    /// The level sprite sheet is 26 tiles wide and 14 tiles high, corresponding to the
    /// layout defined by `Game.TILES_IN_WIDTH` and `Game.TILES_IN_HEIGHT`.
    /// For each pixel in the sprite sheet:
    /// - The red component of the pixel color is used to determine the tile value.
    /// - If the red component is greater than or equal to 48, the tile is considered empty (value = 0).
    /// - Otherwise, the value remains the same.
    /// The method then returns a 2D array of integers where each value corresponds to a tile in the level layout.
    ///
    /// @return A 2D array of integers representing the level layout, where each value corresponds to a specific tile.
    public static int[][] getLevelData() {
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;

    }

}
