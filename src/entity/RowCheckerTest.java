package entity;

import static org.junit.Assert.*;

import org.junit.Test;

public class RowCheckerTest {

	@Test
	public void test_RowChecker() {
		int[][] sampleMatrix = {{0,1,0,1},{0,1,0,1},{0,1,0,1},{0,1,0,1}};
		int[] expectedOutput = {3,1};
		RowManager rManager = new RowManager();
		int[] fullRows = rManager.rowChecker(sampleMatrix);
		assertArrayEquals(expectedOutput,fullRows);
	}
	// deletes row two of the following matrix
	/*
	 0 0 0 0
	 2 2 2 2
	 0 0 0 0
	 0 2 2 0
	 */
	@Test
	public void test_RowDeleter() {
		int[][] sampleMatrix = {{0,2,0,0},{0,2,0,2},{0,2,0,2},{0,2,0,0}};
		int[][] expectedOutput = {{0,0,0,0},{0,0,0,2},{0,0,0,2},{0,0,0,0}};
		RowManager rManager = new RowManager();
		int[][] newMatrix = rManager.rowDeleter(sampleMatrix, 1);
		assertArrayEquals(expectedOutput,newMatrix);
	}

	// deletes row 4 of the following matrix
	/*
	 0 0 0 0
	 2 2 2 2
	 0 0 0 0
	 0 0 0 0
	 */
	@Test
	public void test_RowDeleterEnd() {
		int[][] sampleMatrixB = {{0,2,0,0},{0,2,0,0},{0,2,0,0},{0,2,0,0}};
		int[][] expectedOutputB = {{0,0,2,0},{0,0,2,0},{0,0,2,0},{0,0,2,0}};
		RowManager rManager = new RowManager();
		int[][] newMatrix = rManager.rowDeleter(sampleMatrixB, 3);
		assertArrayEquals(expectedOutputB,newMatrix);
	}

	// deletes row 3 of the following matrix (modified so we don't have to account for walls anymore)
	/*
	 1 1 1 1
	 1 2 2 1
	 1 0 0 1
	 1 1 1 1
	 */
	@Test
	public void test_RowDeleterWithWalls() {
		int[][] sampleMatrixC = {{1,1,1,1},{1,2,0,1},{1,2,0,1},{1,1,1,1}};
		// int[][] expectedOutputC = {{1,1,1,1},{1,0,2,1},{1,0,2,1},{1,1,1,1}};
		int[][] expectedOutputC = {{0,1,1,1},{0,1,2,1},{0,1,2,1},{0,1,1,1}};
		RowManager rManager = new RowManager();
		int[][] newMatrix = rManager.rowDeleter(sampleMatrixC, 2);
		assertArrayEquals(expectedOutputC,newMatrix);
	}

	// deletes the bottom row of the following matrix
	/*
	 1 1 1 1
	 1 2 2 1
	 1 0 0 1
	 1 1 1 1
	 */
	@Test
	public void test_RowDeleterBottomRow() {
		int[][] sampleMatrixD = {{1,1,1,1},{1,2,0,1},{1,2,0,1},{1,1,1,1}};
		int[][] expectedOutputD = {{0,1,1,1},{0,1,2,0},{0,1,2,0},{0,1,1,1}};
		RowManager rManager = new RowManager();
		int[][] newMatrix = rManager.rowDeleter(sampleMatrixD, 3);
		assertArrayEquals(expectedOutputD,newMatrix);
	}

}
