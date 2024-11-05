package minos;

import entity.Shape;

public class Mino_GreenS extends Shape{
    int[][] greenS_Matrix = {{0, 0, 4}, {0, 4, 4}, {0, 4, 0}};
    
    int[][] greenS_Rotated = {{0, 0, 0}, {4, 4, 0}, {0, 4, 4}};
    public Mino_GreenS(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=greenS_Matrix;
        colSize=3;
        rowSize=3;
        
        topLeftX=1;
        topLeftY=1;
    }
    @Override
    public int[][] getNextRotation(){
        return greenS_Rotated;
    }

    @Override
    public void rotate(){
        System.out.println("------ AYOOOOO ROTATE COUNT IS: " + rotateCount + " ------");
        if(rotateCount==0){
            shapeMatrix=greenS_Rotated;
            rotateCount=1;
        }
        else{
            shapeMatrix=greenS_Matrix;
            rotateCount=0;
        }
        
    }

}
