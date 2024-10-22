package tile;

import entity.Shape;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    public Shape mainShape = new Shape(3, 8, 2);
    
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/maps/map.txt");
        addShape(mainShape);
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
    public void addShape(Shape currShape){
        int[][] shapeMap = currShape.shapeMatrix;
        int x = 0;
        int y = 0;
        while(x<currShape.colSize &&y<currShape.rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            mapTileNum[x+currShape.x][y+currShape.y] = shapeMap[x][y];
            x++;
            if(x==currShape.colSize){
                x=0;
                y++;
            }
        }
    }
    // from https://www.geeksforgeeks.org/arrays-copyof-in-java-with-examples/
    public static int[][] copyMatrix(int[][] original){
        //create a new 2D array with the same number of rows as the original
        int[][] copy = new int[original.length][];
        // Copy each row using Arrays.copyOf() method
        for(int i=0; i<original.length;i++){
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }
    public boolean isTileBelowShape(Shape currShape){
        int[][] shapeMap = currShape.shapeMatrix;
        // we want to cycle through x elements at the bottom of the shape
        int x = 0;
        int y = currShape.rowSize-1;
        // hold the value of the tilebelow (0 is nothing, 1 is wall and everything else is a tetris block)
        int tileBelow = 0;
        while(x<currShape.colSize){
            System.out.println("NOW the current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            // get the value of the tile one y coordinate down
            tileBelow = mapTileNum[x+currShape.x][y+currShape.y+1];
            System.out.println("Looking down,Current element at:("+x + "," + (y+1) + ") is:" + tileBelow);
            // if the tile is anything other than 0, there is a tile below the shape
            if(tileBelow!=0){
                return true;
            }
            x++;
        }
        return false;
    }
    public void moveShape(Shape currShape){
        if(currShape ==null){
            System.out.println("No shape to move!");
            return;
        }
        System.out.println("n==============================================\n");
        boolean underShape = isTileBelowShape(currShape);
        System.out.println("Is there a tile below our shape? "+underShape);
        int[][] shapeMap = currShape.shapeMatrix;
        // hold the new map temporarily so that data from the original isn't overwritten
        int[][] tempTileMap = copyMatrix(mapTileNum);
        int x = 0;
        int y = 0;
        int globalShapeX = 0;
        int globalShapeY = 0;
        if(underShape==false){
            while(x<currShape.colSize &&y<currShape.rowSize){
                System.out.println("---- The current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
                globalShapeX =x+currShape.x;
                globalShapeY=y+currShape.y;
                
                System.out.println("The element below it at:("+x + "," + (y+1) + ") is:" + mapTileNum[globalShapeX][globalShapeY+1]);
                
                System.out.println("HOWEVER The element ABOVE it at:("+x + "," + (y-1) + ") is:" + mapTileNum[globalShapeX][globalShapeY-1]);
                // set the tile below the current shape tile equal to it
                tempTileMap[globalShapeX][globalShapeY+1] = mapTileNum[globalShapeX][globalShapeY];
                System.out.println("After copying, the new element below at:("+x + "," + (y+1) + ") is:" + tempTileMap[globalShapeX][globalShapeY+1]);
                // set the tile current shape tile equal to the tile above it (completing the move downwards)
                if(mapTileNum[globalShapeX][globalShapeY-1]==1){
                    tempTileMap[globalShapeX][globalShapeY] = 0;
                }
                else{
                    tempTileMap[globalShapeX][globalShapeY] = mapTileNum[globalShapeX][globalShapeY-1];
                }
                
                System.out.println("SECOND half copy, the original element at:("+x + "," + (y) + ") is:" + tempTileMap[globalShapeX][globalShapeY]);
                
                x++;
                if(x==currShape.colSize){
                    x=0;
                    y++;
                }
            }
            mapTileNum=copyMatrix(tempTileMap);
            currShape.y++;
        }
        else{
            restShape();
        }
        
    }
    // rest the shape down so that a new one can be spawned
    public boolean restShape(){
        mainShape=null;
        System.out.println("Shape has been reset!");
        int newBlockID = ThreadLocalRandom.current().nextInt(2,7);
        mainShape = new Shape(newBlockID, 8, 1);
        addShape(mainShape);
        System.out.println("New Shape Created");
        return false;
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
