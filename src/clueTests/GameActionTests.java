package clueTests;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	
	private static ClueGame game;
	private static Board board;
	private static Card TomCard;
	private static Card LindaCard;
	private static Card WrenchCard;
	private static Card RopeCard;
	private static Card KitchenCard;
	private static Card LoungeCard;
	
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException{
		game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		TomCard = new Card("Tom", Card.CardType.PERSON);
		LindaCard = new Card("Linda", Card.CardType.PERSON);
		WrenchCard = new Card("Wrench", Card.CardType.WEAPON);
		RopeCard = new Card("Rope", Card.CardType.WEAPON);
		KitchenCard = new Card("Kitcheh", Card.CardType.ROOM);
		LoungeCard = new Card("Lounge", Card.CardType.ROOM);
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
	public void testDisprovingSuggestion() {
		Player first = new Player("first");
		Card r = new Card("wrong room", Card.CardType.ROOM);
		Card w = new Card("wrong weapon", Card.CardType.WEAPON);
		Card p = new Card("wrong person", Card.CardType.PERSON);
		Card n = new Card(null, Card.CardType.ROOM);
		//Test for one player, one match
		first.addCard(TomCard);
		first.addCard(LindaCard);
		first.addCard(WrenchCard);
		first.addCard(RopeCard);
		first.addCard(KitchenCard);
		first.addCard(LoungeCard);
		Assert.assertEquals(TomCard, game.disproveSuggestion(r, w, TomCard));
		Assert.assertEquals(KitchenCard, game.disproveSuggestion(KitchenCard, w, p));
		Assert.assertEquals(WrenchCard, game.disproveSuggestion(r, WrenchCard, p));
		Assert.assertEquals(n, game.disproveSuggestion(r, w, p));
	}
}
