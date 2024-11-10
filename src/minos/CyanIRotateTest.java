package minos;

import static org.junit.Assert.*;

import org.junit.Test;

public class CyanIRotateTest {

	@SuppressWarnings("deprecation")
	@Test
	public void test_CyanI_Rotate() {

	    int[][] rotatedMino = {{0, 0, 0, 0}, {3, 3, 3, 3}, {0, 0, 0, 0},{0, 0, 0, 0}};
	    int[][] regularMino = {{0, 3, 0, 0}, {0, 3, 0, 0}, {0, 3, 0, 0},{0, 3, 0, 0}};
		Mino_CyanI IMino = new Mino_CyanI(3, 0, 0);
		int[][] result = IMino.getNextRotation();
		assertEquals(rotatedMino,result);
		
	}

}
