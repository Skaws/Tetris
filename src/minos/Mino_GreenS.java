package minos;

import entity.Shape;

public class Mino_GreenS extends Shape{
    int[][] greenS_Matrix = {{0, 4}, {4, 4}, {4, 0}};
    public Mino_GreenS(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=greenS_Matrix;
        colSize=3;
        rowSize=2;
    }

}
