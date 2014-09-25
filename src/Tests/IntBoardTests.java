package Tests;

import java.util.LinkedList;
import java.util.Set;
import game.BoardCell;
import game.IntBoard;
import org.junit.*;

public class IntBoardTests {
	private IntBoard testBoard;

	@Before
	public void setUp(){
		//System.out.println("In @Before");
		testBoard = new IntBoard();
	}
	@Test
	public void testTLCAdj() {
		//TODO
		//Test top left corner Adjacency list testBoard[0][0]
		BoardCell cell = testBoard.getCell(0,0);
		LinkedList<BoardCell> testList = testBoard.getAdjList(cell);

		Assert.assertTrue(testList.contains(testBoard.getCell(1, 0)));
		Assert.assertTrue(testList.contains(testBoard.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());

	}

	@Test
	public void testBRCAdj() {
		//TODO
		//Test bottom right corner Adjacency list testBoard[3][3]
		BoardCell cell = testBoard.getCell(3,3);
		LinkedList<BoardCell> testList = testBoard.getAdjList(cell);
		Assert.assertTrue(testList.contains(testBoard.getCell(2, 3)));
		Assert.assertTrue(testList.contains(testBoard.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());

	}

	@Test
	public void testREAdj() {
		//TODO
		//Test right edge Adjacency list testBoard[1][3]
		BoardCell cell = testBoard.getCell(1,3);
		LinkedList<BoardCell> testList = testBoard.getAdjList(cell);
		Assert.assertTrue(testList.contains(testBoard.getCell(1, 2)));
		Assert.assertTrue(testList.contains(testBoard.getCell(0, 3)));
		Assert.assertTrue(testList.contains(testBoard.getCell(2, 3)));
		Assert.assertEquals(3, testList.size());
	}

	@Test
	public void testLEAdj() {
		//TODO
		//Test left edge Adjacency list testBoard[3][0]
		BoardCell cell = testBoard.getCell(3,0);
		LinkedList<BoardCell> testList = new LinkedList<BoardCell>();
		testList = testBoard.getAdjList(cell);
		Assert.assertTrue(testList.contains(testBoard.getCell(2, 0)));
		Assert.assertTrue(testList.contains(testBoard.getCell(3, 1)));
		Assert.assertEquals(2, testList.size());
	}

	@Test
	public void testSCMGAdj() {
		//TODO
		//Test second column middle, of grid Adjacency list testBoard[1][1]
		BoardCell cell = testBoard.getCell(1,1);
		LinkedList<BoardCell> testList = testBoard.getAdjList(cell);
		Assert.assertTrue(testList.contains(testBoard.getCell(1, 2)));
		Assert.assertTrue(testList.contains(testBoard.getCell(1, 0)));
		Assert.assertTrue(testList.contains(testBoard.getCell(2, 1)));
		Assert.assertTrue(testList.contains(testBoard.getCell(0, 1)));
		Assert.assertEquals(4, testList.size());
	}

	@Test
	public void testSLCMGAdj() {
		//TODO
		//Test second to last column, middle of grid Adjacency list testBoard[2][2]
		BoardCell cell = testBoard.getCell(2,2);
		LinkedList<BoardCell> testList = testBoard.getAdjList(cell);
		Assert.assertTrue(testList.contains(testBoard.getCell(1, 2)));
		Assert.assertTrue(testList.contains(testBoard.getCell(3, 2)));
		Assert.assertTrue(testList.contains(testBoard.getCell(2, 1)));
		Assert.assertTrue(testList.contains(testBoard.getCell(2, 3)));
		Assert.assertEquals(4, testList.size());
	}

	//TODO
	//Create at least 6 methods to test path creation (calcTarget tests)
	@Test
	public void testTargets00_3()
	{
		BoardCell cell = testBoard.getCell(0, 0);
		Set<BoardCell> targets = testBoard.getTargets(cell, 3);
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(testBoard.getCell(3, 0)));
		Assert.assertTrue(targets.contains(testBoard.getCell(2, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(0, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 2)));
		Assert.assertTrue(targets.contains(testBoard.getCell(0, 3)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 0)));
	}

	@Test
	public void testTargets11_1()
	{
		BoardCell cell = testBoard.getCell(1, 1);
		Set<BoardCell> targets = testBoard.getTargets(cell, 1);
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(testBoard.getCell(0, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 0)));
		Assert.assertTrue(targets.contains(testBoard.getCell(2, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 2)));
	}

	@Test
	public void testTargets31_2()
	{
		BoardCell cell = testBoard.getCell(3, 1);
		Set<BoardCell> targets = testBoard.getTargets(cell, 2);
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(testBoard.getCell(2, 0)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(2, 2)));
		Assert.assertTrue(targets.contains(testBoard.getCell(3, 3)));
	}

	@Test
	public void testTargets21_4()
	{
		BoardCell cell = testBoard.getCell(2, 1);
		Set<BoardCell> targets = testBoard.getTargets(cell, 4);
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 0)));
		Assert.assertTrue(targets.contains(testBoard.getCell(0, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 2)));
		Assert.assertTrue(targets.contains(testBoard.getCell(0, 3)));
		Assert.assertTrue(targets.contains(testBoard.getCell(2, 3)));
		Assert.assertTrue(targets.contains(testBoard.getCell(3, 2)));
	}

	@Test
	public void testTarget03_3()
	{
		BoardCell cell = testBoard.getCell(0, 3);
		Set<BoardCell> targets = testBoard.getTargets(cell, 3);
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(testBoard.getCell(0, 0)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(2, 2)));
		Assert.assertTrue(targets.contains(testBoard.getCell(3, 3)));
	}

	@Test
	public void testTargets22_2()
	{
		BoardCell cell = testBoard.getCell(2, 2);
		Set<BoardCell> targets = testBoard.getTargets(cell, 2);
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(testBoard.getCell(2, 0)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(0, 2)));
		Assert.assertTrue(targets.contains(testBoard.getCell(3, 1)));
		Assert.assertTrue(targets.contains(testBoard.getCell(1, 3)));
		Assert.assertTrue(targets.contains(testBoard.getCell(3, 3)));
	}

}
