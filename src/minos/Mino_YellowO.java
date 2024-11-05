package minos;

import entity.Shape;

public class Mino_YellowO extends Shape{
    
    int[][] yellowO_Matrix = {{8, 8}, {8, 8}};
    public Mino_YellowO(int shapeID, int x, int y) {
        super(shapeID, x, y);
        shapeMatrix=yellowO_Matrix;
        colSize=2;
        rowSize=2;
        
        topLeftX=0;
        topLeftY=0;
    }

}
