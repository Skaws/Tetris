package entity;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import main.GamePanel;
import tile.TileManager;

public class ShapeHandler{
    GamePanel gp;
    TileManager tileM;
    int mapTileNum[][];
    public Shape mainShape = new Shape(4, 8, 2);
    public ShapeHandler(GamePanel gp, TileManager tileM) {
        this.gp = gp;
        this.tileM = tileM;
        mapTileNum = tileM.mapTileNum;
        // Shape Handler Instantiated call
        System.out.println("Shape Handler Instantiated");
        addShape(mainShape);
    }
    public void addShape(Shape currShape){
        System.out.println("Shape adder called");
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
            //System.out.println("NOW the current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            // if the coordinate in the shape matrix isn't empty
            if(shapeMap[x][y]!=0){
                // get the value of the tile one y coordinate down
                tileBelow = mapTileNum[x+currShape.x][y+currShape.y+1];
                //System.out.println("Looking down,Current element at:("+x + "," + (y+1) + ") is:" + tileBelow);
                // if the tile is anything other than 0, there is a tile below the shape
                if(tileBelow!=0){
                    return true;
                }
            }
            
            x++;
        }
        return false;
    }
    public boolean isTileAdjacentToShape(Shape currShape, String direction){
        int[][] shapeMap = currShape.shapeMatrix;
        // we want to cycle through y elements in the leftmost part of the object
        int x=0;
        int dirShift = 1;
        // if we're checking on the right of the shape
        if("right".equals(direction)){
            x=currShape.colSize-1;
            dirShift=-1;
        }
        int tileLeft = 0;
        for(int y=0;y<currShape.rowSize;y++){
            System.out.println("\n-------------------------------------------\n");
            System.out.println("HEY the current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            // get the value of the tile one y coordinate down
            tileLeft = mapTileNum[x+currShape.x -dirShift][y+currShape.y];
            System.out.println("Looking " + direction + ", Current element at:("+(x-dirShift) + "," + (y) + ") is:" + tileLeft);
            // if the tile is anything other than 0, there is a tile left of the shape
            if(tileLeft!=0){
                return true;
            }
        }
        return false;
    }
    public void moveShape(Shape currShape, String direction){
        if(currShape ==null){
            System.out.println("No shape to move!");
            return;
        }
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
        int globalShapeX = 0;
        int globalShapeY = 0;
        int xShift = 0;
        switch (direction) {
            case "left":
            xShift =1;
                break;
            case "right":
            xShift =-1;
                break;
        }
        if(underShape==false && "down".equals(direction)){
            while(x<currShape.colSize &&y<currShape.rowSize){
                // if we're dealing with a 0, we don't wanna move it down as for objects like the S that will overwrite any blocks in the bottom right corner of the S
                if(shapeMap[x][y]==0){
                    System.out.println("==========Skipping a 0============");
                    x++;
                    if(x==currShape.colSize){
                        x=0;
                        y++;
                    }
                    continue;
                }
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
            tileM.mapTileNum=copyMatrix(tempTileMap);
            currShape.y++;
        }
        else if(adjacentToShape==false && ("left".equals(direction)|| "right".equals(direction))){
            while(x<currShape.colSize &&y<currShape.rowSize){
                // if we're dealing with a 0, we don't wanna move it down as for objects like the S that will overwrite any blocks in the bottom right corner of the S
                if(shapeMap[x][y]==0){
                    System.out.println("==========Skipping a 0============");
                    x++;
                    if(x==currShape.colSize){
                        x=0;
                        y++;
                    }
                    continue;
                }
                System.out.println("---- The current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
                globalShapeX =x+currShape.x;
                globalShapeY=y+currShape.y;
                
                System.out.println("The element left of it at:("+(x-xShift) + "," + (y) + ") is:" + mapTileNum[globalShapeX-xShift][globalShapeY]);
                
                System.out.println("HOWEVER The element right of it at:("+(x+xShift) + "," + (y) + ") is:" + mapTileNum[globalShapeX+xShift][globalShapeY]);
                // set the tile left of the current shape tile equal to it
                tempTileMap[globalShapeX-xShift][globalShapeY] = mapTileNum[globalShapeX][globalShapeY];
                System.out.println("After copying, the new element left at:("+(x-xShift) + "," + (y)  + ") is:" + tempTileMap[globalShapeX-xShift][globalShapeY]);
                // set the  current shape tile equal to the tile
                //tempTileMap[globalShapeX][globalShapeY] = 0;
                // if there is a wall in the area we're trying to move the block to
                if(mapTileNum[globalShapeX+xShift][globalShapeY]==1){
                    tempTileMap[globalShapeX][globalShapeY] = 0;
                }
                else{
                    tempTileMap[globalShapeX][globalShapeY] = mapTileNum[globalShapeX+xShift][globalShapeY];
                }
                
                System.out.println("SECOND half copy, the original element at:("+x + "," + (y) + ") is:" + tempTileMap[globalShapeX][globalShapeY]);
                
                x++;
                if(x==currShape.colSize){
                    x=0;
                    y++;
                }
            }
            tileM.mapTileNum=copyMatrix(tempTileMap);
            // decrease X coordinates to match shape
            currShape.x-= xShift;
        }
        if(underShape==true){
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
}
