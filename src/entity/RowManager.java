package entity;

import java.util.Arrays;

public class RowManager {
	public RowManager() {}
	public int[][] rowDeleter(int[][] matBoard, int rowID){
		int matWidth = matBoard.length;
		int matHeight = matBoard[0].length;
		
		int[] firstDivCol;
		int[] secondDivCol;
		int[] finalCol = new int[matHeight];
		System.out.println("----------- ROW DELETER CALLED FOR ROW:" + rowID + "----------------------");
		//go through every column (as matrices are indexed via column > row)
		for(int x =0; x<matWidth; x++){
			//copy
			int[] currCol = new int[matHeight];
			currCol[0]=0;
			for(int y=0; y<matHeight; y++){
				
				if(y<rowID){
					currCol[y+1]=matBoard[x][y];
				}
				else if(y>rowID){
					currCol[y] = matBoard[x][y];
				}
			}
			matBoard[x] = currCol;
			// currCol = matBoard[x];
			// firstDivCol = Arrays.copyOfRange(currCol, 0, rowID);
			// secondDivCol = Arrays.copyOfRange(currCol, rowID+1, matWidth);
			// finalCol[0] = 0;
			// System.arraycopy(firstDivCol, 0, finalCol, 1, firstDivCol.length);
			// System.arraycopy(secondDivCol, 0, finalCol, firstDivCol.length, matWidth);
			// matBoard[x] = finalCol;
		}
		return matBoard;
	}
	// check if the array has any rows with nonzero elements (contains exclusively non-zero elements)
	public int[] rowChecker(int[][] matBoard){
		int[] fullRows = {};

		int matWidth = matBoard.length;
		int matHeight = matBoard[0].length;
		int x =0;
		int y = matHeight-1;
		int currElement = 0;
		boolean isRowFull = true;
		// loop from bottom to top
		while(x<matWidth && y>0){
			// evaluate an element, if the element is zero set the rowFull variable to false
			currElement = matBoard[x][y];
			if(currElement==0){
				isRowFull=false;
			}
			x++;
			// once at the end of the row reset X and set rowFull to True
			if(x==matWidth){
				if(isRowFull==true){
					// copy the current array of full rows into an array 1 element larger (store this back in fullrows again)
					fullRows= Arrays.copyOf(fullRows, (fullRows.length+1));
					// add the full row's coordinate to the end of the fullrows array
					fullRows[fullRows.length-1]=y;
				}
				x=0;
				y--;
				isRowFull=true;
			}
		}
		
		return fullRows;
	}
}
