package minos;

import entity.Shape;

public class Mino_CyanI extends Shape{
    
    int[][] cyanI_Matrix = {{0, 3, 0, 0}, {0, 3, 0, 0}, {0, 3, 0, 0},{0, 3, 0, 0}};
    int[][] cyanI_Rotated = {{0, 0, 0, 0}, {3, 3, 3, 3}, {0, 0, 0, 0},{0, 0, 0, 0}};
    public Mino_CyanI(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=cyanI_Matrix;
        colSize=4;
        rowSize=4;
    }

    @Override
    public int[][] getNextRotation(){
        if(rotateCount==0){
            return cyanI_Rotated;
        }
        else{
            return cyanI_Matrix;
        }
    }

    @Override
    public void rotate(){
        System.out.println("------ AYOOOOO ROTATE COUNT IS: " + rotateCount + " ------");
        if(rotateCount==0){
            shapeMatrix=cyanI_Rotated;
            rotateCount=1;
        }
        else{
            shapeMatrix=cyanI_Matrix;
            rotateCount=0;
        }
        
    }

}
