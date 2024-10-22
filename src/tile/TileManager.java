package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    public int mapTileNum[][];
    //public ShapeHandler sHandler;
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/maps/map.txt");
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

            while(col<gp.maxScreenCol && row < gp.maxScreenRow){
                // read text line
                String line = br.readLine();

                while(col < gp.maxScreenCol){
                    // split the line up into individual array of numbers as strings
                    String numbers[] = line.split(" ");
                    // select an element of the numbers string array and convert it to an int

                    int num = Integer.parseInt(numbers[col]);

                    // put this selected int number in its respective place in the array
                    mapTileNum[col][row] = num;
                    col++;
                    
                }
                if(col==gp.maxScreenCol){
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

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            // get the tileID we need to draw from the mapTileNum array
            int tileNum = mapTileNum[col][row];
            // pass it into the tile array so we can cross reference the tileID to its actual image/type
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x+=gp.tileSize;

            if(col==gp.maxScreenCol){
                col =0;
                x=0;
                row++;
                y+=gp.tileSize;
            }
        }
    }
}
