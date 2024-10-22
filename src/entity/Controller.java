package entity;

import main.GamePanel;
import main.KeyHandler;

public class Controller {
    GamePanel gp;
    KeyHandler keyH;
    int timer;
    public Controller(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        timer=0;
    }
    public void update(){
        timer++;
        if(timer>4){
            if(keyH.leftPressed==true){
            
                gp.sHandler.moveShape(gp.sHandler.mainShape,"left");
            }
            if(keyH.rightPressed==true){
            
                gp.sHandler.moveShape(gp.sHandler.mainShape,"right");
            }
            if(keyH.downPressed==true){
            
                gp.sHandler.moveShape(gp.sHandler.mainShape,"down");
            }
            timer=0;
        }
        
    }
}
