package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
    private float xDelta=100, yDelta=100;
    private BufferedImage img,subImg;

    public GamePanel() {

        mouseInputs = new MouseInputs(this);
        keyboardInputs = new KeyboardInputs(this);

        importImg();

        setPanelSize();
        addKeyListener(keyboardInputs);
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void importImg() {
        try {
            FileInputStream fis = new FileInputStream("res/player_sprites.png");
            img = ImageIO.read(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    public void changeXDelta(int xDelta) {
        this.xDelta += xDelta;
    }

    public void changeYDelta(int yDelta) {
        this.yDelta += yDelta;
    }

    // when we click mouse we get x and y pos
//    public void setRectPos(int x, int y) {
    public void setImgPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        subImg = img.getSubimage(1*64,8*40,64,40);
        g.drawImage(subImg,(int) xDelta,(int) yDelta, 128, 80,null);
    }


}
