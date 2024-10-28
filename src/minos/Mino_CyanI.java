package minos;

import entity.Shape;

public class Mino_CyanI extends Shape{
    
    int[][] cyanI_Matrix = {{0, 3}, {0, 3}, {0, 3},{0, 3}};
    public Mino_CyanI(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=cyanI_Matrix;
        colSize=4;
        rowSize=2;
    }

}
