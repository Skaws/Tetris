package entity;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import main.GamePanel;
import minos.Mino_BlueR;
import minos.Mino_CyanI;
import minos.Mino_GreenS;
import minos.Mino_OrangleL;
import minos.Mino_PurpleT;
import minos.Mino_RedZ;
import minos.Mino_YellowO;
import tile.BoxManager;
import tile.TileManager;

public class ShapeHandler{
    GamePanel gp;
    TileManager tileM;
    BoxManager boxM;
    int mapTileNum[][];
    int placedBoard[][];
    int spawnX = 5;
    int spawnY = 1;
    public Shape mainShape = new Mino_RedZ(7, spawnX, spawnY);
    int currShapeID;
    int nextShapeID;
    public ShapeHandler(GamePanel gp, TileManager tileM, BoxManager boxM) {
        this.gp = gp;
        this.tileM = tileM;
        this.boxM = boxM;
        mapTileNum = tileM.mapTileNum;
        // Shape Handler Instantiated call
        System.out.println("Shape Handler Instantiated");
        placedBoard = copyMatrix(mapTileNum);
        currShapeID = 7;
        nextShapeID = ThreadLocalRandom.current().nextInt(2,9);
        dropNextShape();
    }

    public Shape createShape(int newShapeID){
        Shape inputShape = null;
        switch(newShapeID){
            case 2:
            inputShape = new Mino_BlueR(2, spawnX, spawnY);
            break;
            case 3:
            inputShape = new Mino_CyanI(3, spawnX, spawnY);
            break;
            case 4:
            inputShape = new Mino_GreenS(4, spawnX, spawnY);
            break;
            case 5:
            inputShape = new Mino_OrangleL(5, spawnX, spawnY);
            break;
            case 6:
            inputShape = new Mino_PurpleT(6, spawnX, spawnY);
            break;
            case 7:
            inputShape = new Mino_RedZ(7, spawnX, spawnY);
            break;
            case 8:
            inputShape = new Mino_YellowO(8, spawnX, spawnY);
            break;
        }
        return inputShape;
    }
    // add the current shape object to its given coordinates on screen
    public void addShape(Shape currShape){
        System.out.println("Shape adder called");
        int[][] shapeMap = currShape.shapeMatrix;
        int x = 0;
        int y = 0;
        while(x<currShape.colSize &&y<currShape.rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            // if the current coordinate of the tetromino isn't 0 (so that if we add a block in it doesn't overwrite objects around it with 0s)
            if(shapeMap[x][y]!=0){
                tileM.mapTileNum[x+currShape.x][y+currShape.y] = shapeMap[x][y];
            }
            x++;
            if(x==currShape.colSize){
                x=0;
                y++;
            }
        }
    }
    // remove shape from the screen
    public void deleteShape(Shape currShape){
        System.out.println("Shape deleter called");
        int[][] shapeMap = currShape.shapeMatrix;
        int x = 0;
        int y = 0;
        while(x<currShape.colSize &&y<currShape.rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            if(shapeMap[x][y]!=0){
                System.out.println("\n \n Deleting Shape!");
                tileM.mapTileNum[x+currShape.x][y+currShape.y] = 0;
            }
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
    // Currently used as the rotation checker. It takes a shape's matrix + coordinates 
    // and then checks the board of currently placed down shapes. If the shape can be placed
    //  at the coordinates given without causing collisions, the area isn't occupied so it returns false
    public boolean isAreaOccupied(int[][] shapeMat, int shapeX, int shapeY){
        int x = 0;
        int y = 0;
        int evalTile = 0;
        int colSize = shapeMat.length;
        int rowSize = shapeMat[0].length; //this gets one column and 1 column contains X rows, thus the size of the column array is the number of rows
        System.out.println("\n This shape has: " + rowSize + " rows and " + colSize +" columns");
        while(x<colSize && y<rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMat[x][y]);
            if(shapeMat[x][y]!=0){
                // get the tile on the board of placed down shapes at coordinates x,y and store it in evalTile
                evalTile = placedBoard[x + shapeX][y+shapeY];
                System.out.println("\n ----- The current element on the board at:("+x + "," + y + ") is:" + evalTile + "-----");
                if(evalTile!=0){
                    System.out.println("Scouted collision detected");
                    return true;
                }
            }
            x++;
            if(x==colSize){
                x=0;
                y++;
            }
        }
        return false;
    }

    public boolean isTileBelowShape(Shape currShape){
        System.out.println("\n o======== Tile Below has been called ===========o \n");
        int[][] shapeMap = currShape.shapeMatrix;
        // create counters to cycle through the shape's coordinates
        int x = 0;
        int y = 0;
        int tileBelow = 0;
        while(x<currShape.colSize &&y<currShape.rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            // if the current coordinate of the tetromino isn't 0 (so that if we check the actual blocks)
            if(shapeMap[x][y]!=0){
                tileBelow = placedBoard[x+currShape.x][y+currShape.y+1];
                System.out.println("Current element below block at:("+x + "," + y + ") is:" + tileBelow);
                if(tileBelow!=0){
                    System.out.println("Collision detected");
                    return true;
                }
            }
            x++;
            if(x==currShape.colSize){
                x=0;
                y++;
            }
        }
        return false;
    }

    public boolean isTileAdjacentToShape(Shape currShape, String direction){
        int[][] shapeMap = currShape.shapeMatrix;
        // we want to cycle through y elements in the leftmost part of the object
        int x=0;
        int y=0;
        int dirShift = 1;
        // if we're checking on the right of the shape
        if("right".equals(direction)){
            x=currShape.colSize-1;
            dirShift=-1;
        }
        int tileLeft = 0;
        while(x<currShape.colSize &&y<currShape.rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            // if the current coordinate of the tetromino isn't 0 (so that if we check the actual blocks)
            if(shapeMap[x][y]!=0){
                tileLeft = placedBoard[x+currShape.x-dirShift][y+currShape.y];
                System.out.println("Looking " + direction + ", Current element at:("+(x-dirShift) + "," + (y) + ") is:" + tileLeft);
                if(tileLeft!=0){
                    System.out.println("Collision detected");
                    return true;
                }
            }
            x++;
            if(x==currShape.colSize){
                x=0;
                y++;
            }
        }
        return false;
    }

    // So far this is where a lot of the game gets run, as move down is called once per second and most actions in game (moving tiles) is run through this method
    public void moveShape(Shape currShape, String direction){
        if(currShape ==null){
            System.out.println("No shape to move!");
            return;
        }
        System.out.println("n==============================================\n");
        System.out.println("Our shape is currently at coordinates (" + currShape.x + "," + currShape.y + ")");
        System.out.println("n==============================================\n");
        boolean underShape = isTileBelowShape(currShape);
        boolean adjacentToShape = false;
        System.out.println("Is there a tile below our shape? "+underShape);
        // if we're dealing with a horizontal direction move
        if("left".equals(direction)|| "right".equals(direction)){
            adjacentToShape = isTileAdjacentToShape(currShape, direction);
        }
        System.out.println("Is there a tile to the left of our shape? "+adjacentToShape);
        int[][] shapeMap = currShape.shapeMatrix;
        // hold the new map temporarily so that data from the original isn't overwritten
        mapTileNum = tileM.mapTileNum;
        int[][] tempTileMap = copyMatrix(tileM.mapTileNum);
        int x = 0;
        int y = 0;
        boolean xAdvance = x<currShape.colSize;
        int globalShapeX = 0;
        int globalShapeY = 0;
        int xShift = 0;
        switch (direction) {
            case "left":
            xShift =-1;
                break;
            case "right":
            xShift =1;
            x=currShape.colSize-1;
                break;
        }
        
        if(underShape==true){
            System.out.println("00000000000 PUT DOWN THE OBJECT 00000000000000");
            restShape();
        }
        // if the shape doesn't have anything beneath it
        else if(underShape==false && "down".equals(direction)){
            // remove the shape from the map and redraw it at the new location (in this case 1 row below)
            deleteShape(currShape);
            currShape.y++;
            addShape(currShape);
        }
        
        // if the shape doesn't have anything left/right of it
        else if(adjacentToShape==false && ("left".equals(direction) ||("right".equals(direction)))){
            
            // remove the shape from the map and redraw it at the new location 
            // (in this case 1 row left OR right depending on direction input)
            deleteShape(currShape);
            currShape.x+=xShift;
            addShape(currShape);
        }

    }
    public void rotateShape(){
        System.out.println("rotate function called!");
        if(mainShape.shapeID==8){
            System.out.println("can't rotate a square!");
            return;
        }
        boolean canRotate = !(isAreaOccupied(mainShape.getNextRotation(),mainShape.x,mainShape.y));
        
        System.out.println("Can we rotate? " + canRotate);

        if(canRotate==true){
            deleteShape(mainShape);
            mainShape.rotate();
            addShape(mainShape);
        }


    }
    public void dropNextShape(){
        System.out.println("DROP NEXT SHAPE CALLED");
        System.out.println("Current Shape ID is: " + currShapeID + ", next Shape ID is: " + nextShapeID);
        currShapeID = nextShapeID;
        nextShapeID = ThreadLocalRandom.current().nextInt(2,9);
        
        System.out.println("NOWWW Current Shape ID is: " + currShapeID + ", next Shape ID is: " + nextShapeID);
        mainShape = createShape(currShapeID);
        addShape(mainShape);
        // Try creating another shape object to send to the box
        Shape nextShape = createShape(nextShapeID);
        boxM.setShape(nextShape);
        System.out.println("New Shape Created");
    }
    // rest the shape down so that a new one can be spawned
    public boolean restShape(){
        //deleteShape(mainShape);
        
        boxM.clearBox();
        mainShape=null;
        System.out.println("Shape has been reset!");
        placedBoard = copyMatrix(mapTileNum);
        dropNextShape();
        return false;
    }
}
