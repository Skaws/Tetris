package entity;

import main.GamePanel;
import main.KeyHandler;

public class Controller {
    GamePanel gp;
    KeyHandler keyH;
    int xpos,ypos;
    int leftCounter;
    int rightCounter;
    int rotateCounter;
    public Controller(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        this.leftCounter=0;
        this.rotateCounter=0;
        this.xpos=0;
        this.ypos=0;
    }
    public void update(){
        if(keyH.leftPressed==true){
            
            if(leftCounter==0){
                gp.sHandler.moveShape(gp.sHandler.mainShape,"left");
            }
            else if(leftCounter ==5){
                leftCounter=-1;
            }
            leftCounter++;
        }
        else if(keyH.leftPressed==false){
            leftCounter=0;
        }
        if(keyH.rightPressed==true){
            if(rightCounter==0){
                gp.sHandler.moveShape(gp.sHandler.mainShape,"right");
            }
            else if(rightCounter ==5){
                rightCounter=-1;
            }
            rightCounter++;
        }
        else if(keyH.rightPressed==false){
            rightCounter=0;
        }
        if(keyH.downPressed==true){
        
            gp.sHandler.moveShape(gp.sHandler.mainShape,"down");
        }
        if(keyH.rotatePressed==true){
            if(rotateCounter==0){
                gp.sHandler.rotateShape();
            }
            else if(rotateCounter==12){
                rotateCounter=0;
            }
            rotateCounter++;
        }
        else if(keyH.rotatePressed==false){
            rotateCounter=0;
        }
    
    
        
    }
}
