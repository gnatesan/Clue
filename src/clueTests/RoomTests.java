package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.ClueGame;

public class RoomTests {

	//TODO
	//Make tests to test the Rooms of Board
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException{
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.setVisible(false);
		game.loadRoomConfig();
		game.loadBoardConfig();
		board = game.getBoard();
	}
	
	public static final int NUM_ROOMS = 11;
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
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Lounge", rooms.get('O'));
		assertEquals("Library", rooms.get('L'));
		assertEquals("Study", rooms.get('S'));
	}
	

}
