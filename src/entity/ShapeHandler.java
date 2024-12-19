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
    RowManager rowM;
    int mapTileNum[][];
    public int placedBoard[][];
    int spawnX = 4;
    int spawnY = 0;
    int restCounter = 0;
    public int slideCounter;
    public boolean shapeSliding;
    public boolean underShape;
    public boolean belowNewPos;
    int boardHeight, boardWidth;
    boolean restPause = false;
    public Shape mainShape = new Mino_RedZ(7, spawnX, spawnY);
    int currShapeID;
    int nextShapeID;
    public ShapeHandler(GamePanel gp, TileManager tileM, BoxManager boxM) {
        this.gp = gp;
        this.tileM = tileM;
        this.boxM = boxM;
        rowM = new RowManager();
        mapTileNum = tileM.mapTileNum;
        boardHeight = tileM.boardHeight;
        boardWidth = tileM.boardWidth;
        // Shape Handler Instantiated call
        System.out.println("Shape Handler Instantiated");
        placedBoard = copyMatrix(mapTileNum);
        currShapeID = 7;
        nextShapeID = ThreadLocalRandom.current().nextInt(2,9);
        dropNextShape();
    }

    public void clearBoard(){
        // erase the board by going through every column in the board and filling it with 0s
        for(int[] col:tileM.mapTileNum){
            Arrays.fill(col,0);
        }
        // set local mtn = object counterpart
        mapTileNum=tileM.mapTileNum;
        // erase the placedBoard tiles too
        placedBoard = copyMatrix(mapTileNum);
        // clear the next tile box too
        boxM.clearBox();
        // start the game again
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
    // Currently used as the rotation checker. It takes a shape's matrix + the coordinates we want to put the shape at 
    // and then checks the board of currently placed down shapes. If the shape can be placed
    //  at the coordinates given without causing collisions, the area isn't occupied so it returns false

    // check if any of the shape's coordinates would be out of bounds (if x = left wallborder OR right wall border OR y = floor OR y = ceiling)
    public boolean isAreaOccupied(int[][] shapeMat, int shapeX, int shapeY){
        int x = 0;
        int y = 0;
        int leftWallBorder = -1;
        int ceilingBorder = -1;
        int floorBorder = boardHeight;
        int rightWallBorder = boardWidth;
        int globalTileX,globalTileY;
        int evalTile = 0;
        int colSize = shapeMat.length;
        int rowSize = shapeMat[0].length; //this gets one column and 1 column contains X rows, thus the size of the column array is the number of rows
        System.out.println("\n This shape has: " + rowSize + " rows and " + colSize +" columns");
        while(x<colSize && y<rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMat[x][y]);
            if(shapeMat[x][y]!=0){
                globalTileX = x + shapeX;
                globalTileY = y+shapeY;
                System.out.println("\n ----- The current Coordinate we're looking at is: ("+globalTileX + "," + globalTileY + ")");
                // if the spot we're checking is OUT OF BOUNDS
                if((globalTileX==leftWallBorder||globalTileX==rightWallBorder)|| (globalTileY==ceilingBorder||globalTileY==floorBorder)){
                    return true;
                }
                //if(y+shapeY>boardHeight -1 OR x + shapeX = 0 OR x + shape X =boardWidth-1)
                // get the tile on the board of placed down shapes at coordinates x,y and store it in evalTile
                evalTile = placedBoard[globalTileX][globalTileY];
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
                // if the shape is currently at the bottom of the board, return true for collision
                if((y+currShape.y+1) == boardHeight){
                    return true;
                }
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
        int wallBorderX = -1;
        // if we're checking on the right of the shape
        if("right".equals(direction)){
            x=currShape.colSize-1;
            dirShift=-1;
            wallBorderX=boardWidth;
        }
        int tileLeft = 0;
        while(x<currShape.colSize &&y<currShape.rowSize){
            System.out.println("Current element at:("+x + "," + y + ") is:" + shapeMap[x][y]);
            // if the current coordinate of the tetromino isn't 0 (so that if we check the actual blocks)
            if(shapeMap[x][y]!=0){
                // if the shape is currently at the wall border, return true for collision
                if((x+currShape.x-dirShift) == wallBorderX){
                    return true;
                }
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
        underShape = isTileBelowShape(currShape);
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
            
            shapeSliding=true;
            System.out.println("TIME TO SLIDE");
            // if("auto down".equals(direction)){
            //     shapeSliding=true;
            //     System.out.println("TIME TO SLIDE");
            // }
            
            // if(restCounter==2){
            //     System.out.println("00000000000 PUT DOWN THE OBJECT 00000000000000");
            //     restShape();
            //     restCounter=0;
            // }
            // System.out.println("00000000000 PUT DOWN THE OBJECT 00000000000000");
            // restShape();
            // return;
            // System.out.println("---------- TIME TO SLIDE! ------------");
            // shapeSliding=true;
            // if(slideCounter>=60){
            //     System.out.println("00000000000 PUT DOWN THE OBJECT 00000000000000");
            //     restShape();
            //     shapeSliding=false;
            //     slideCounter=0;
            // }
        }
        // if the shape doesn't have anything beneath it
        else if(underShape==false && ("input down".equals(direction)|| "auto down".equals(direction))){
            slideCounter =0;
            restCounter=0;
            shapeSliding=false;
            // remove the shape from the map and redraw it at the new location (in this case 1 row below)
            deleteShape(currShape);
            currShape.y++;
            addShape(currShape);
            if("input down".equals(direction)){
                gp.score+=1;
            }

        }
        
        // if the shape doesn't have anything left/right of it
        if(adjacentToShape==false && ("left".equals(direction) ||("right".equals(direction)))){
            
            // remove the shape from the map and redraw it at the new location 
            // (in this case 1 row left OR right depending on direction input)
            deleteShape(currShape);
            currShape.x+=xShift;
            addShape(currShape);
        }
        
        belowNewPos = isTileBelowShape(currShape);
        if(belowNewPos==true){
            if(shapeSliding==false){
                gp.sfx.play(4, false);
            }
            shapeSliding=true;
            System.out.println("TIME TO SLIDE");
        }
        else{
            System.out.println("---------------- SLIDING OVER----------- \n ------------- OBJECT MOVED TO DESCENDABLE POSITION -------------");
            //slideCounter=0;
            shapeSliding=false;
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
            gp.sfx.play(3, false);
        }
        // when sliding if the shape can be rotated to keep moving, then the program needs to check for that
        underShape = isTileBelowShape(mainShape);
        if(underShape==false){
            slideCounter =0;
            restCounter=0;
            shapeSliding=false;
        }


    }
    // sets the current shape to the next one, generates a new shape for the NEXT next shape and renders both in their respective places.
    public void dropNextShape(){
        System.out.println("DROP NEXT SHAPE CALLED");
        System.out.println("Current Shape ID is: " + currShapeID + ", next Shape ID is: " + nextShapeID);
        // set the current shapeID to the next shape (which we've had loading and displayed in game), then generate the ID for the future next shape
        currShapeID = nextShapeID;
        nextShapeID = ThreadLocalRandom.current().nextInt(2,9);
        // int randFactor = ThreadLocalRandom.current().nextInt(0,2);
        // nextShapeID = 3 + (4*randFactor);
        
        //create the next shape object to be added
        System.out.println("NOWWW Current Shape ID is: " + currShapeID + ", next Shape ID is: " + nextShapeID);
        mainShape = createShape(currShapeID);
        mainShape.y-=mainShape.topLeftY;
        // evaluate if this new shape can be added to the board OR if the board is full
        boolean isGameOver = isAreaOccupied(mainShape.shapeMatrix, spawnX, spawnY);
        if(isGameOver==true){
            gp.gameOver=true;
            System.out.println("And that's all!! Game OVER!!");
            gp.sfx.play(2, false);
        }
        // if the game isn't over add the next shape to the board and then add the next generated shape to the box
        else{
            addShape(mainShape);
            // Try creating another shape object to send to the box
            Shape nextShape = createShape(nextShapeID);
            boxM.setShape(nextShape);
            System.out.println("New Shape Created");
        }
    }
    // rest the shape down so that a new one can be spawned
    public boolean restShape(){
        //deleteShape(mainShape);
        boxM.clearBox();
        mainShape=null;
        System.out.println("Shape has been reset!");
        // set the current board with the shape rested down to the placed down board matrix
        placedBoard = copyMatrix(mapTileNum);
        // get the list of rows that are full of tetromino blocks
        int[] completeRows = rowM.rowChecker(placedBoard);
        int fullRowNum = completeRows.length;
        // if there are rows that are full
        if(fullRowNum>0){
            // for each complete row
            for(int i = 0; i<completeRows.length;i++){
                System.out.println("Currently on row: " + completeRows[i]);
                // get the complete row location (from the list of complete rows) and delete them
                placedBoard=rowM.rowDeleter(placedBoard, (completeRows[i])+i);
                // it is worth noting that the intended row coordinates (lets say rows 17, 14 and 12 need deleting) will be shifted once a deletion has been made
                // however as this deletes from the bottom up, the row coordinate shift will always be by the current number of deletions that have occured
                // e.g. if row 17 is deleted that's a 1 coordinate shift and thus row 14 would be row 15 in the new board
                // if row 14 *(now 15) is deleted that's another 1 coord shift, now 2 coord shifts total, meaning row 12 would be NEW row 14
            }
            // if the player gets a tetris (clears 4 lines at once)
            if(fullRowNum==4){
                gp.score+=(800*gp.level);
            }
            else{
                gp.score+=((fullRowNum*2)-1) * (100*gp.level);
            }
            gp.lines+=fullRowNum;
            if((gp.lines/10) ==gp.level){
                gp.level++;
                if(gp.playM.dropInterval>10){
                    gp.playM.dropInterval-=10;
                }
                else if(gp.playM.dropInterval>1){
                    gp.playM.dropInterval-=1;
                }
            }
            
            gp.sfx.play(1, false);
        }
        else{
            gp.sfx.play(4, false);
        }
        tileM.mapTileNum=copyMatrix(placedBoard);
        // drop the next shape
        dropNextShape();
        return false;
    }
}
