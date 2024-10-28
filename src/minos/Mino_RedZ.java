package minos;

import entity.Shape;

public class Mino_RedZ extends Shape{
    int[][] redZ_Matrix = {{0, 7, 0}, {0, 7, 7}, {0, 0, 7}};
    
    int[][] redZ_rotated = {{0, 0, 7}, {0, 7, 7}, {0, 7, 0}};

    int[][][] rotateCollection = {redZ_Matrix,redZ_rotated};
    public Mino_RedZ(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=redZ_Matrix;
        colSize=3;
        rowSize=3;
    }

}
