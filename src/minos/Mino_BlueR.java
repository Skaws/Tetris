package minos;

import entity.Shape;

public class Mino_BlueR extends Shape{

    int[][] blueR_Matrix = {{2, 2}, {0, 2}, {0, 2}};
    public Mino_BlueR(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=blueR_Matrix;
        colSize=3;
        rowSize=2;
    }

}
