package main;
import entity.Controller;
import entity.Player;
import entity.ShapeHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.BoxManager;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS

    final int originalTileSize = 24; //16x16 tile
    final int scale = 2; 
    public final int tileSize = originalTileSize + (originalTileSize/2); //32 x 32 tile
    public final int maxScreenCol = 36;
    public final int maxScreenRow = 22;
    // get screen width by multiplying the number of screen columns by the pixel size of each column element (tiles)
    public final int screenWidth = maxScreenCol * tileSize; //960 pixels
    public final int screenHeight = maxScreenRow * tileSize; //720 pixels

    // FPS
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    public BoxManager boxM = new BoxManager(this);
    public ShapeHandler sHandler  = new ShapeHandler(this,tileM, boxM);
    // instantiate keyHandler
    KeyHandler keyH = new KeyHandler();
    // keep it simple as single threaded
    Thread gameThread;
    Player player = new Player(this,keyH);
    Controller controller = new Controller(this, keyH);

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    public GamePanel(){
        // all these methods are from JPanel as this class extends it
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        // all drawing from this component is done in an offscreen painting buffer. Improves rendering performance
        this.setDoubleBuffered(true);
        // add our instantiated keyHandler object as the keyListener
        this.addKeyListener(keyH);
        // focus GamePanel to receive key input
        this.setFocusable(true);
    }
    
    public void startGameThread(){
        // 
        gameThread = new Thread(this);
        gameThread.start();
    }

    // starting the thread will call the run method here
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while(gameThread!=null){
            currentTime = System.nanoTime();
            // get the difference in time between the previous and current while loop call, then convert that to (fraction of) frames by dividing by drawinterval
            // after that add it to delta
            delta+= (currentTime - lastTime) / drawInterval;
            timer+=(currentTime - lastTime);
            // set the lastTime to current time to set up for the next loop call

            lastTime=currentTime;
            // if a frame's worth of time has passed (as all the time passed since the last frame has been summed as a fraction of a frame in delta)
            if(delta>=1){
                // 1 UPDATE: update information e.g. tetris block positions
                update();
                // 2 DRAW: draw the screen with updated info
                repaint();
                delta--;
                drawCount++;
            }
            // once a second has passed output the number of frames that our program has tracked within a second
            if(timer >= 1000000000){
                System.out.println("FPS:"+drawCount);
                drawCount=0;
                timer=0;
                sHandler.moveShape(sHandler.mainShape,"down");
            }
            
        }
    }
 
    
    public void update(){
        controller.update();
    }
    // this method gets called by the repaint method. Graphics g is our pencil/paintbrush
    public void paintComponent(Graphics g){
        // call the jpanel paintcomponent method.
        super.paintComponent(g);

        
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        boxM.draw(g2);
        //player.draw(g2);
        g2.dispose();
    }


}
