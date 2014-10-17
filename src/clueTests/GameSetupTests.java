package clueTests;

import org.junit.Assert;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameSetupTests {
	
	private static ClueGame game;
	private static Board board;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException{
		game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	
	/**
	 * Test the card configuration. Verify the correct number of cards are present,
	 * the current type, etc.
	 */
	@Test
	public void testLoadCardConfig() {
		// Verify the total number of cards loaded from the configuration file
		Assert.assertEquals(21, game.getCards().size());
		
		// Verify the categories of cards and their count
		Assert.assertEquals(Card.CardType.PERSON, game.getCards().get(0).getType());
		Assert.assertEquals(Card.CardType.WEAPON, game.getCards().get(6).getType());
		Assert.assertEquals(Card.CardType.ROOM, game.getCards().get(12).getType());
	}
	
	@Test
	public void testLoadPlayerConfig() {
		ArrayList <Player> test = game.getPlayers();
		//Test human player
		Assert.assertEquals("John", test.get(0).getName());
		Assert.assertEquals("Blue", test.get(0).getColor());
		Assert.assertEquals(21, test.get(0).getRow());
		Assert.assertEquals(9, test.get(0).getCol());
		
		//Test second to last computer player
		Assert.assertEquals("Dave", test.get(4).getName());
		Assert.assertEquals("Brown", test.get(4).getColor());
		Assert.assertEquals(18, test.get(4).getRow());
		Assert.assertEquals(4, test.get(4).getCol());
		
		//Test last computer player
		Assert.assertEquals("Emily", test.get(5).getName());
		Assert.assertEquals("Purple", test.get(5).getColor());
		Assert.assertEquals(9, test.get(5).getRow());
		Assert.assertEquals(1, test.get(5).getCol());
		
	}

}
