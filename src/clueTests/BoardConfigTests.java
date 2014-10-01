package clueTests;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.FileNotFoundException;

import org.junit.*;

import clueGame.*;

public class BoardConfigTests {

	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;

	@Before
	public void setUp() throws FileNotFoundException, BadConfigFormatException {

		ClueGame game = new ClueGame("ClueLayoutOurs.csv", "ClueLegendours.txt");
		game.loadRoomConfig();
		game.loadBoardConfig();
		board = game.getBoard();
	}


	@Test
	public void testBoardSize() {

		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());

	}

	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(506, totalCells);
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(23, numDoors);
	}

	@Test
	public void testDoorDirections(){

		RoomCell room = board.getRoomCellAt(4, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(2, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(15, 18);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(19, 10);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());

		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(4, 20);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(8, 12);
		assertFalse(cell.isDoorway());	
	}

	@Test
	public void testRoomLegend() {
		assertEquals('C', board.getRoomCellAt(21, 0).getInitial());
		assertEquals('R', board.getRoomCellAt(17, 4).getInitial());
		assertEquals('B', board.getRoomCellAt(19, 12).getInitial());
		assertEquals('O', board.getRoomCellAt(1, 20).getInitial());
		assertEquals('K', board.getRoomCellAt(20, 20).getInitial());
		assertEquals('L', board.getRoomCellAt(10, 4).getInitial());
		//assertEquals('W', board.getRoomCellAt(12, 16).getInitial());
		assertEquals('H', board.getRoomCellAt(3, 11).getInitial());
		assertEquals('D', board.getRoomCellAt(11, 20).getInitial());

	}

	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		ClueGame game = new ClueGame("ClueLayoutBadRoom.csv", "ClueLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
	}
}
