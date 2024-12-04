package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackgroundHandler {
    public BufferedImage bgImage;
    public BackgroundHandler(){
        getBGImage();
    }
    public void getBGImage(){
        try {
            bgImage = ImageIO.read(getClass().getResourceAsStream("/background/tetrisFadedBG.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        g2.drawImage(bgImage, 0, 0, 1296, 794, null);
    }
}
