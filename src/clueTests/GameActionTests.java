package clueTests;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class GameActionTests {
	
	private static ClueGame game;
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException{
		game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	
	@Test
	public void testAccusation() {
		//Get correct solution
		Solution correct = game.getSolution();
		
		//Create wrong solutions for an incorrect person, weapon, and room
		Solution w1 = new Solution(correct.getPerson(), correct.getWeapon(), "wrongroom");
		Solution w2 = new Solution(correct.getPerson(), "wrongweapon", correct.getRoom());
		Solution w3 = new Solution("wrongperson", correct.getWeapon(), correct.getRoom());
		
		//Check wrong solutions
		Assert.assertFalse(game.checkAccusation(w1));
		Assert.assertFalse(game.checkAccusation(w2));
		Assert.assertFalse(game.checkAccusation(w3));
		
		//Check correct solutions
		Assert.assertTrue(game.checkAccusation(correct));
	}
	
	@Test
	public void testPickLocationSelectsRoom() {
		ComputerPlayer computer = new ComputerPlayer("Test");
		
		board.calcTargets(14, 16, 1);
		
		int loc_13_16 = 0;
		int loc_15_16 = 0;
		int loc_14_15 = 0;
		
		for (int i = 0; i < 100; i++) {
			BoardCell selected = computer.pickLocation(board.getTargets());
			
			if (selected == board.getCellAt(13, 16)) {
				loc_13_16++;
			} else if (selected == board.getCellAt(15, 16)) {
				loc_15_16++;
			} else if (selected == board.getCellAt(14, 15)) {
				loc_14_15++;
			}
		}
		
		Assert.assertEquals(100, loc_13_16 + loc_15_16 + loc_14_15);
		Assert.assertTrue(loc_13_16 > 10);
		Assert.assertTrue(loc_15_16 > 10);
		Assert.assertTrue(loc_14_15 > 10);
		
	}
	
	@Test
	public void testPickLocationSelectsRandom() {
		ComputerPlayer computer = new ComputerPlayer("Test");
		
		board.calcTargets(9,9,1);
		
		BoardCell selected = computer.pickLocation(board.getTargets());
		
		Assert.assertEquals(9, selected.getRow());
		Assert.assertEquals(8, selected.getRow());
	}
	
	@Test
	public void testPickLocationRandomFromIfVisitedRoomInTargetList() {
		ComputerPlayer computer = new ComputerPlayer("Test");
		computer.setLastRoomVisited('L');
		
		board.calcTargets(9,9,1);
		
		int loc_8_9 = 0;
		int loc_9_8 = 0;
		int loc_10_9 = 0;
		
		for (int i = 0; i < 100; i++) {
			BoardCell selected = computer.pickLocation(board.getTargets());
			
			if (selected == board.getCellAt(8, 9)) {
				loc_8_9++;
			} else if (selected == board.getCellAt(9, 8)) {
				loc_9_8++;
			} else if (selected == board.getCellAt(10, 9)) {
				loc_10_9++;
			}
		}
		
		Assert.assertEquals(100, loc_8_9 + loc_9_8 + loc_10_9);
		Assert.assertTrue(loc_8_9 > 10);
		Assert.assertTrue(loc_9_8 > 10);
		Assert.assertTrue(loc_10_9 > 10);
	}
}
