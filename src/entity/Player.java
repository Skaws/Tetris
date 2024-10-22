package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try {
            blueR = ImageIO.read(getClass().getResourceAsStream("/blocks/Blue_R.png"));
            cyanI = ImageIO.read(getClass().getResourceAsStream("/blocks/Cyan_I.png"));
            greenS = ImageIO.read(getClass().getResourceAsStream("/blocks/Green_S.png"));
            orangeL = ImageIO.read(getClass().getResourceAsStream("/blocks/Orange_L.png"));
            purpleT = ImageIO.read(getClass().getResourceAsStream("/blocks/Purple_T.png"));
            redZ = ImageIO.read(getClass().getResourceAsStream("/blocks/Red_Z.png"));
            yellowO = ImageIO.read(getClass().getResourceAsStream("/blocks/Yellow_O.png"));
            dark = ImageIO.read(getClass().getResourceAsStream("/blocks/dark.png"));
            trueDark = ImageIO.read(getClass().getResourceAsStream("/blocks/true_Dark.png"));
        } 
        catch (IOException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.upPressed==true){
            y-=speed;
        }
        else if(keyH.downPressed==true){
            y+=speed;
        }
        else if(keyH.leftPressed==true){
            x-=speed;
        }
        else if(keyH.rightPressed==true){
            x+=speed;
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = blueR;
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        
        g2.drawImage(image, x +gp.tileSize, y, gp.tileSize, gp.tileSize, null);

        g2.drawImage(image, x +(2*(gp.tileSize)), y, gp.tileSize, gp.tileSize, null);

        g2.drawImage(image, x +(2*(gp.tileSize)), y+gp.tileSize, gp.tileSize, gp.tileSize, null);
    }
}
