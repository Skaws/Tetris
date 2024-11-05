package minos;

import entity.Shape;

public class Mino_PurpleT extends Shape{
    
    int[][] purpleT_Matrix = {{0, 6, 0}, {6, 6, 0}, {0, 6, 0}};
    int[][] purpleT_rightRotate = {{0, 0, 0}, {6, 6, 6}, {0, 6, 0}};
    int[][] purpleT_downRotate = {{0, 6, 0}, {0, 6, 6}, {0, 6, 0}};
    int[][] purpleT_leftRotate = {{0, 6, 0}, {6, 6, 6}, {0, 0, 0}};
    public Mino_PurpleT(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=purpleT_Matrix;
        colSize=3;
        rowSize=3;
        
        topLeftX=1;
        topLeftY=0;
    }

    
    @Override
    public int[][] getNextRotation(){
        switch (rotateCount) {
            case 0:
                return purpleT_rightRotate;
            case 1:
                return purpleT_downRotate;
            case 2:
                return purpleT_leftRotate;
            default:
                return purpleT_Matrix;
        }
    }
    @Override
    public void rotate(){
        switch (rotateCount) {
            case 0:
                shapeMatrix=purpleT_rightRotate;
                break;
            case 1:
                shapeMatrix=purpleT_downRotate;
                break;
            case 2:
                shapeMatrix=purpleT_leftRotate;
                break;
            default:
                shapeMatrix=purpleT_Matrix;
                break;
        }
        rotateCount++;
        if(rotateCount>3){rotateCount=0;}
    }
}
