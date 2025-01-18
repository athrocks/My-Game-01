package main;

import entities.Player;
import levels.LevelManager;

import java.awt.*;

/**
 * The Game class represents the main game logic and execution flow.
 * It initializes the game window, manages the game loop, updates the game state,
 * and renders the game to the screen.
 */
public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private LevelManager levelManager;

    /**
     * Represents the default size of each tile in the game world, in pixels.
     * The size is set to 32 pixels by default.
     * This value is used as the base tile size before applying any scaling.
     */
    public final static int TILES_DEFAULT_SIZE = 32;

    /**
     * Represents the scaling factor applied to the tile size.
     * A value of 2f means that the tile size will be scaled to double its original size.
     * The scaling is used to adjust the size of tiles for visual purposes.
     */
    public final static float SCALE = 2f;

    /**
     * Represents the number of tiles that fit horizontally across the game screen or level.
     * The value is set to 26 tiles in width.
     * This is used to calculate the total width of the game area.
     */
    public final static int TILES_IN_WIDTH = 26;

    /**
     * Represents the number of tiles that fit vertically down the game screen or level.
     * The value is set to 14 tiles in height.
     * This is used to calculate the total height of the game area.
     */
    public final static int TILES_IN_HEIGHT = 14;

    /**
     * Represents the final size of each tile in the game, after applying the scaling factor.
     * The tile size is calculated by multiplying the default tile size (TILES_DEFAULT_SIZE)
     * by the scaling factor (SCALE). In this case, 32 * 2 = 64 pixels per tile.
     */
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);

    /**
     * Represents the total width of the game area in pixels.
     * The width is calculated by multiplying the tile size (TILES_SIZE) by the number of tiles in width (TILES_IN_WIDTH).
     * The total width is the horizontal span of the game world.
     * Calculation: TILES_SIZE * TILES_IN_WIDTH = 64 * 26 = 1664 pixels.
     */
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;

    /**
     * Represents the total height of the game area in pixels.
     * The height is calculated by multiplying the tile size (TILES_SIZE) by the number of tiles in height (TILES_IN_HEIGHT).
     * The total height is the vertical span of the game world.
     * Calculation: TILES_SIZE * TILES_IN_HEIGHT = 64 * 14 = 896 pixels.
     */
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    /**
     * Constructs the Game object, initializes the game components,
     * sets up the game panel, and starts the game loop.
     */
    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    }

    /**
     * Initializes the core game classes such as the player and level manager.
     */
    private void initClasses() {
        player = new Player(200, 200, (int) (64 * SCALE), (int) (40 * SCALE));
        levelManager = new LevelManager(this);
    }

    /**
     * Starts the game loop by initializing the game thread.
     */
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Updates the game state, including the player and level.
     * This method is called on every game update cycle.
     */
    public void update() {
        levelManager.update();
        player.update();
    }

    /**
     * Renders the current game state to the provided Graphics object.
     * This method is called on every game render cycle.
     *
     * @param g The Graphics object used for drawing the game visuals.
     */
    public void render(Graphics g) {
        levelManager.draw(g);
        player.render(g);
    }

    /**
     * The main game loop which runs continuously, calculating updates and rendering frames.
     * It controls the FPS (frames per second) and UPS (updates per second) of the game.
     */
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET; // 1000000000.0 nanoSec => 1 sec
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long prevTime = System.nanoTime();
        long lastCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currTime = System.nanoTime();

            deltaU += (currTime - prevTime) / timePerUpdate;
            deltaF += (currTime - prevTime) / timePerFrame;
            prevTime = currTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    /**
     * Resets the player's movement direction booleans when the window loses focus.
     * This ensures that the player doesn't keep moving when the window is not focused.
     */
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    /**
     * Returns the Player object representing the player in the game.
     *
     * @return The Player object.
     */
    public Player getPlayer() {
        return player;
    }
}
