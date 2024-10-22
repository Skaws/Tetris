package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        // create a window using JFrame
        JFrame window = new JFrame();
        // make window closeable
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // make the window  afixed size
        window.setResizable(false);
        window.setTitle("Tetris");

        // instantiate our gamePanel and add it to the window.
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        // size the window to fit the preferred size and layouts of its subcomponents
        window.pack();

        // set window to be in the default location, screen centre
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // get the gameThread running
        gamePanel.startGameThread();
    }
}
