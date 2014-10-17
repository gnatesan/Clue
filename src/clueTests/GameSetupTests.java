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
	
	/**
	 * Test the card dealing results. Test that the cards are dealt as evenly as possible and no duplicates are dealt.
	 */
	@Test
	public void testCardDealing() {
		ArrayList<Player> players = game.getPlayers();
		// Check that each player has the correct number of cards
		// Three cards should be taken out of play as the solution
		// Leaving three for each of the six players
		Assert.assertEquals(3, players.get(0).getCards().size());
		Assert.assertEquals(3, players.get(1).getCards().size());
		Assert.assertEquals(3, players.get(2).getCards().size());
		Assert.assertEquals(3, players.get(3).getCards().size());
		Assert.assertEquals(3, players.get(4).getCards().size());
		Assert.assertEquals(3, players.get(5).getCards().size());
		
		// Check all cards have been dealt
		int dealtCards = 0;
		for (int i = 0; i < game.getPlayers().size(); i++) {
			dealtCards += game.getPlayers().get(i).getCards().size();
		}
		
		Assert.assertEquals(18, dealtCards);
		
		// Three tests to check that players don't have identical cards
		Assert.assertNotEquals(players.get(0).getCards().get(0), players.get(1).getCards().get(1));
		Assert.assertNotEquals(players.get(1).getCards().get(1), players.get(2).getCards().get(2));
		Assert.assertNotEquals(players.get(2).getCards().get(2), players.get(3).getCards().get(0));
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

}
