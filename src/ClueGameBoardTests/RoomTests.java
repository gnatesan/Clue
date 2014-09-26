package ClueGameBoardTests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import GameBoard.Board;
import GameBoard.ClueGame;

public class RoomTests {

	//TODO
	//Make tests to test the Rooms of Board
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	
	public static final int NUM_ROOMS = 9;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	
	@Test
	public void testRoomsSize() {
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
	}
	
	@Test
	public void testRoomsAssoc(){
		
		Map<Character, String> rooms = board.getRooms();
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Billiard room", rooms.get('R'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Lounge", rooms.get('O'));
		assertEquals("Library", rooms.get('L'));
		assertEquals("Study", rooms.get('S'));
	}
	

}
