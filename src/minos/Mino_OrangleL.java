package minos;

import entity.Shape;

public class Mino_OrangleL extends Shape{
    
    int[][] orangeL_Matrix = {{0, 5}, {0, 5}, {5, 5}};
    public Mino_OrangleL(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=orangeL_Matrix;
        colSize=3;
        rowSize=2;
    }

}
