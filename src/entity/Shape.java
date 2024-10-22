package entity;

import java.awt.image.BufferedImage;

public class Shape {
    // xy coords of the top
	public int x,y;
    public int shapeID;

    public BufferedImage blueR,cyanI,greenS,orangeL,purpleT,redZ,yellowO,dark,trueDark;
    public String direction;

    public int[][] shapeMatrix;
    public int colSize;
    public int rowSize;

    public Shape(int shapeID,int x, int y){
        this.shapeID = shapeID;
        this.x = x;
        this.y = y;
        setupShape();
    }

    public void setupShape(){
        // int[][] l_Matrix = { {2, 2, 2}, {0, 0, 2}};
        // //int[][] l_Matrix = { {2, 0}, {2, 0}, {2, 2} };
        // shapeMatrix=l_Matrix;
        // colSize=2;
        // rowSize=3;
        
        switch(shapeID){
            case 2:
            int[][] blueR_Matrix = {{2, 2}, {0, 2}, {0, 2}};
            shapeMatrix=blueR_Matrix;
            colSize=3;
            rowSize=2;
            break;

            case 3:
            int[][] cyanI_Matrix = {{0, 3}, {0, 3}, {0, 3},{0, 3}};
            shapeMatrix=cyanI_Matrix;
            colSize=4;
            rowSize=2;
            break;

            case 4:
            int[][] greenS_Matrix = {{0, 4}, {4, 4}, {4, 0}};
            shapeMatrix=greenS_Matrix;
            colSize=3;
            rowSize=2;
            break;

            case 5:
            int[][] orangeL_Matrix = {{0, 5}, {0, 5}, {5, 5}};
            shapeMatrix=orangeL_Matrix;
            colSize=3;
            rowSize=2;
            break;

            case 6:
            int[][] purpleT_Matrix = {{0, 6}, {6, 6}, {0, 6}};
            shapeMatrix=purpleT_Matrix;
            colSize=3;
            rowSize=2;
            break;
        }
        
    }
}
