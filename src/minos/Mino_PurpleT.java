package minos;

import entity.Shape;

public class Mino_PurpleT extends Shape{
    
    int[][] purpleT_Matrix = {{0, 6}, {6, 6}, {0, 6}};
    public Mino_PurpleT(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=purpleT_Matrix;
        colSize=3;
        rowSize=2;
    }

}
