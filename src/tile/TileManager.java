package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    public int mapTileNum[][];
    public int boardWidth = 10;
    public int boardHeight = 18;
    //public ShapeHandler sHandler;
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[boardWidth][boardHeight];
        getTileImage();
        for(int[] col:mapTileNum){
            Arrays.fill(col,0);
        }
        //loadMap("/maps/tetrisBoard.txt");
        //sHandler = new ShapeHandler(gp);
        // moveShape(lBlock);
        // moveShape(lBlock);
    }
    public void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/blocks/true_Dark.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/blocks/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/blocks/Blue_R.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/blocks/Cyan_I.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/blocks/Green_S.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/blocks/Orange_L.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/blocks/Purple_T.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/blocks/Red_Z.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/blocks/Yellow_O.png"));

            


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filepath){

        try {
            // import text file
            InputStream is = getClass().getResourceAsStream(filepath);
            // use the br to read the text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while(col<boardWidth && row < boardHeight){
                // read text line
                String line = br.readLine();

                while(col < boardWidth){
                    // split the line up into individual array of numbers as strings
                    String numbers[] = line.split(" ");
                    // select an element of the numbers string array and convert it to an int

                    int num = Integer.parseInt(numbers[col]);

                    // put this selected int number in its respective place in the array
                    mapTileNum[col][row] = num;
                    col++;
                    
                }
                if(col==boardWidth){
                    col =0;
                    row++;
                }
            }
            br.close();
        } 
        catch (Exception e) {
            System.out.println("Error in TileManager!");
            e.printStackTrace();
        }

    }
    
    public void draw(Graphics2D g2){
        // g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
        
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        int[] ceiling = new int[11];
        int xOffset = 13 * gp.tileSize;
        int yOffset = 2 * gp.tileSize;
        
        int borderXOffSet = 12 * gp.tileSize;
        int borderYOffSet = gp.tileSize;
        Arrays.fill(ceiling, 1);

        
        while(col < boardWidth && row < boardHeight){
            // get the tileID we need to draw from the mapTileNum array
            int tileNum = mapTileNum[col][row];
            // pass it into the tile array so we can cross reference the tileID to its actual image/type
            g2.drawImage(tile[tileNum].image, x +xOffset, y +yOffset, gp.tileSize, gp.tileSize, null);
            col++;
            x+=gp.tileSize;

            if(col==boardWidth){
                col =0;
                x=0;
                row++;
                y+=gp.tileSize;
            }
        }

        int wallIndex = 1;
        // draw ceiling
        for(int i =0; i <12; i++){
            int ceilX = i * gp.tileSize;
            g2.drawImage(tile[wallIndex].image, ceilX +borderXOffSet, borderYOffSet, gp.tileSize, gp.tileSize, null);
        }
        // draw floor
        int floorY = 19 * gp.tileSize;
        for(int j =0; j <12; j++){
            int ceilX = j * gp.tileSize;
            g2.drawImage(tile[wallIndex].image, ceilX +borderXOffSet, floorY + borderYOffSet, gp.tileSize, gp.tileSize, null);
        }
        //draw left wall
        for(int l =0; l <20; l++){
            int wall_L_y = l * gp.tileSize;
            g2.drawImage(tile[wallIndex].image, borderXOffSet, wall_L_y + borderYOffSet, gp.tileSize, gp.tileSize, null);
        }

        //draw right wall
        int rWallX = 11 * gp.tileSize;
        for(int r =0; r <20; r++){
            int wall_L_y = r * gp.tileSize;
            g2.drawImage(tile[wallIndex].image, rWallX + borderXOffSet, wall_L_y + borderYOffSet, gp.tileSize, gp.tileSize, null);
        }
    }
}
