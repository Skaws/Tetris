package minos;

import entity.Shape;

public class Mino_BlueR extends Shape{

    int[][] blueR_Matrix = {{2, 2, 0}, {0, 2, 0}, {0, 2, 0}};
    int[][] blueR_rightRotate = {{0, 0, 0}, {2, 2, 2}, {2, 0, 0}};
    int[][] blueR_downRotate = {{0, 2, 0}, {0, 2, 0}, {0, 2, 2}};
    int[][] blueR_leftRotate = {{0, 0, 2}, {2, 2, 2}, {0, 0, 0}};
    public Mino_BlueR(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=blueR_Matrix;
        colSize=3;
        rowSize=3;
    }
    @Override
    public int[][] getNextRotation(){
        switch (rotateCount) {
            case 0:
                return blueR_rightRotate;
            case 1:
                return blueR_downRotate;
            case 2:
                return blueR_leftRotate;
            default:
                return blueR_Matrix;
        }
    }
    @Override
    public void rotate(){
        switch (rotateCount) {
            case 0:
                shapeMatrix=blueR_rightRotate;
                break;
            case 1:
                shapeMatrix=blueR_downRotate;
                break;
            case 2:
                shapeMatrix=blueR_leftRotate;
                break;
            default:
                shapeMatrix=blueR_Matrix;
                break;
        }
        rotateCount++;
        if(rotateCount>3){rotateCount=0;}
    }
}
