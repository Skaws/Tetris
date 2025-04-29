package main;

import javax.swing.JFrame;

public class HighScoreManager {
    boolean scoreCalled;
    GamePanel gp;
    JFrame gameWindow;
    String userName = "";
    public HighScoreManager(JFrame window, GamePanel gp){
        this.gameWindow=window;
        this.gp=gp;
        this.scoreCalled=false;
    }
    public void openScoreWindow(){
        String charEntered = gp.keyH.strTyped;
        userName+=charEntered;
        System.out.println("Username is: " + userName);
        if(scoreCalled==false){
            // JTextField txtField;
            // txtField = new JTextField();
            // txtField.setPreferredSize(new Dimension(250,40));
            // this.gameWindow.add(txtField);
            // gameWindow.pack();
            // scoreCalled=true;
        }
        else{
            System.out.println("Window already open");
        }
    }
}
