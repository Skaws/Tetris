package minos;

import entity.Shape;

public class Mino_RedZ extends Shape{
    int[][] redZ_Matrix = {{0, 7, 0}, {0, 7, 7}, {0, 0, 7}};
    
    int[][] redZ_rotated = {{0, 0, 0}, {0, 7, 7}, {7, 7, 0}};

    int[][][] rotateCollection = {redZ_Matrix,redZ_rotated};
    public Mino_RedZ(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=redZ_Matrix;
        colSize=3;
        rowSize=3;
        
        topLeftX=0;
        topLeftY=1;
    }

    @Override
    public int[][] getNextRotation(){
        return redZ_rotated;
    }

    public void rotate(){
        System.out.println("------ AYOOOOO ROTATE COUNT IS: " + rotateCount + " ------");
        // switch case for the rotate value
        
        // if(rotateCount==0){
        //     shapeMatrix=redZ_rotated;
        //     rotateCount=1;
        //     System.out.println("------ AAAA ROTATE COUNT IS NOW: " + rotateCount + " ------");
        // }
        // else{
            
        //     System.out.println("------ ROTATING BACK TO ORIGINAL NOW ------");
        //     shapeMatrix=redZ_Matrix;
        //     rotateCount=0;
            
        //     System.out.println("------ AAAA ROTATE COUNT IS NOW: " + rotateCount + " ------");
        // }
        switch (rotateCount) {
            // if the shape is in default rotation
            case 0:
            // make it the next rotation and update rotateID to match it
                shapeMatrix=redZ_rotated;
                rotateCount++;
                
                System.out.println("------ AAAA ROTATE COUNT IS NOW: " + rotateCount + " ------");
                break;
            // if it's not in default rotation, turn it back
            case 1:
                System.out.println("------ ROTATING BACK TO ORIGINAL NOW ------");
                shapeMatrix=redZ_Matrix;
                rotateCount=0;
                System.out.println("------ AAAA ROTATE COUNT IS NOW: " + rotateCount + " ------");
                break;
        }
        
    }

}
