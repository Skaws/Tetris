package tile;

import entity.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class BoxManager{
    GamePanel gp;
    Tile[] tile;
    public int boxMatrix[][];
    public int boxWidth = 5;
    public int boxHeight = 5;
    public Shape heldShape;
    public BoxManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        boxMatrix = new int[gp.maxScreenCol][gp.maxScreenRow];
        getTileImage();
        loadMap("/maps/noborderBlockBox.txt");
    }
    // could modify tilemanager to take an array input and return array to make this redundant
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
    // put a shape in the box matrix
    public void setShape(Shape currShape){
        System.out.println("Shape adder called");
        int[][] shapeMap = currShape.shapeMatrix;
        int x = 0;
        int y = 0;
        while(x<currShape.colSize &&y<currShape.rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            // if the current coordinate of the tetromino isn't 0 (so that if we add a block in it doesn't overwrite objects around it with 0s)
            if(shapeMap[x][y]!=0){
                boxMatrix[x+1][y+(1-currShape.topLeftY)] = shapeMap[x][y];
            }
            x++;
            if(x==currShape.colSize){
                x=0;
                y++;
            }
        }
    }
    // clear the box matrix
    public void clearBox(){
        System.out.println("Box Clear called");
        int x = 0;
        int y = 0;
        while(x<boxWidth &&y<boxHeight){
            boxMatrix[x][y] = 0;
            x++;
            if(x==boxWidth){
                x=0;
                y++;
            }
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
            while(col<boxWidth && row < boxHeight){
                // read text line
                String line = br.readLine();

                while(col < boxWidth){
                    // split the line up into individual array of numbers as strings
                    String numbers[] = line.split(" ");
                    // select an element of the numbers string array and convert it to an int

                    int num = Integer.parseInt(numbers[col]);

                    // put this selected int number in its respective place in the array
                    boxMatrix[col][row] = num;
                    col++;
                    
                }
                if(col==boxWidth){
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

        int xOffset = 25 * gp.tileSize;
        int yOffset = 13 * gp.tileSize;

        int boxOutlineWidth = boxWidth * gp.tileSize;
        int boxOutlineHeight = boxHeight * gp.tileSize;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(10f));
        //g2.fillRoundRect(xOffset-6, yOffset-6, boxOutlineWidth+12, boxOutlineHeight+12, 0, 0);
        g2.drawRoundRect(xOffset-6, yOffset-6, boxOutlineWidth+12, boxOutlineHeight+12, 0, 0);



        while(col < boxWidth && row < boxHeight){
            // get the tileID we need to draw from the mapTileNum array
            int tileNum = boxMatrix[col][row];
            // pass it into the tile array so we can cross reference the tileID t6o its actual image/type
            g2.drawImage(tile[tileNum].image, x +xOffset, y +yOffset, gp.tileSize, gp.tileSize, null);
            col++;
            x+=gp.tileSize;

            if(col==boxWidth){
                col =0;
                x=0;
                row++;
                y+=gp.tileSize;
            }
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", xOffset + gp.tileSize + 12, yOffset - 20);
    }
}
