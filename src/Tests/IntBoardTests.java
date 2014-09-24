package Tests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import game.BoardCell;
import game.IntBoard;

import org.junit.*;

public class IntBoardTests {
	//Not sure these are in the right place. Look at web requirements and let me know. . . 
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private IntBoard testBoard;
	private LinkedList compareArray;
	
	@Before
	public void setUp(){
		System.out.println("In @Before");
		testBoard = new IntBoard();
		compareArray = new LinkedList<BoardCell>();
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
		Assert.assertTrue(testList.contains(testBoard.getCell(2, 0)));
		Assert.assertTrue(testList.contains(testBoard.getCell(0, 2)));
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
		LinkedList<BoardCell> testList = testBoard.getAdjList(cell);
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
}
