package minos;

import entity.Shape;

public class Mino_OrangleL extends Shape{
    
    int[][] orangeL_Matrix = {{0, 5, 0}, {0, 5, 0}, {5, 5, 0}};
    
    int[][] orangeL_rightRotate = {{0, 0, 0}, {5, 5, 5}, {0, 0, 5}};
    int[][] orangeL_downRotate = {{0, 5, 5}, {0, 5, 0}, {0, 5, 0}};
    int[][] orangeL_leftRotate = {{5, 0, 0}, {5, 5, 5}, {0, 0, 0}};
    public Mino_OrangleL(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=orangeL_Matrix;
        colSize=3;
        rowSize=3;

        
        topLeftX=2;
        topLeftY=0;
    }

    @Override
    // Get the matrix for what the next rotation would be
    public int[][] getNextRotation(){
        switch (rotateCount) {
            case 0:
                return orangeL_rightRotate;
            case 1:
                return orangeL_downRotate;
            case 2:
                return orangeL_leftRotate;
            default:
                return orangeL_Matrix;
        }
    }
    @Override
    public void rotate(){

        switch (rotateCount) {
            case 0:
                shapeMatrix=orangeL_rightRotate;
                break;
            case 1:
                shapeMatrix=orangeL_downRotate;
                break;
            case 2:
                shapeMatrix=orangeL_leftRotate;
                break;
            default:
                shapeMatrix=orangeL_Matrix;
                break;
        }
        rotateCount++;
        if(rotateCount>3){rotateCount=0;}
    }
}
