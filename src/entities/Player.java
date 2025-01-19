package entities;

import main.Game;
import utilz.HelpMethods;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15; // 120 fps/ 8 animations per second
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private float xDrawOfSet = 21 * Game.SCALE; // player's hitBox start at (21,4)
    private float yDrawOfSet = 4 * Game.SCALE;


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, 20 * Game.SCALE, 28 * Game.SCALE); // player size is 20px * 28px
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitBox.x - xDrawOfSet), (int) (hitBox.y - yDrawOfSet), width, height, null);
        drawHitBox(g);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }

    }

    private void setAnimation() {

        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (attacking) {
            playerAction = ATTACK_1;
        }

        if (startAni != playerAction) {
            resetAniTickAndInd();
        }
    }

    private void resetAniTickAndInd() {
        aniIndex = 0;
        aniTick = 0;
    }

    private void updatePos() {

        moving = false;
        if (!left && !right && !up && !down) {
            return;
        }

        float xSpeed = 0, ySpeed = 0;


        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left) {
            xSpeed = playerSpeed;
        }

        if (up && !down) {
            ySpeed = -playerSpeed;
        } else if (down && !up) {
            ySpeed = playerSpeed;
        }

        if (HelpMethods.canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
            hitBox.y += ySpeed;
            moving = true;
        }
    }

    /// Loads the player animation frames from the sprite atlas.
    ///
    /// We are talking about player_sprites.png here there are total 9 rows and maximum 6 columns for row:2 or rowIdx:1
    ///
    /// This method extracts individual frames for the player's actions (e.g., running, idle) from the sprite sheet
    /// and stores them in a 2D array. The sprite sheet is expected to have 9 rows and 6 columns of frames, where
    /// each frame has a size of 64x40 pixels.
    ///
    /// The frames are then used in the game to animate the player's actions, such as walking, running, and jumping.
    ///
    /// The frames are loaded based on the player's action states and are stored in the 'animations' array.
    private void loadAnimations() {

        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    public void resetDirBooleans() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
}
