package clueTests;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.HumanPlayer;
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
		KitchenCard = new Card("Kitchen", Card.CardType.ROOM);
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
		ArrayList <Player> test = new ArrayList <Player>();
		ArrayList <Player> test2 = new ArrayList <Player>();
		ArrayList <Player> test3 = new ArrayList <Player>();
		Player first = new Player("first");
		Player second = new Player("second");
		Player computer1 = new Player("cp1");
		Player computer2 = new Player("cp2");
		Player computer3 = new Player("cp3");
		Player human = new HumanPlayer("human");
		int roomCount = 0;
		int playerCount = 0;
		int weaponCount = 0;
		int nullCount = 0;
		Card r = new Card("wrong room", Card.CardType.ROOM);
		Card w = new Card("wrong weapon", Card.CardType.WEAPON);
		Card p = new Card("wrong person", Card.CardType.PERSON);
		Card n = new Card(null, Card.CardType.ROOM);
		
		//Test for one player, one correct match
		first.addCard(TomCard);
		first.addCard(LindaCard);
		first.addCard(WrenchCard);
		first.addCard(RopeCard);
		first.addCard(KitchenCard);
		first.addCard(LoungeCard);
		test.add(first);
		//game.setTurn(first);
		
		Assert.assertEquals(KitchenCard, game.disproveSuggestion(KitchenCard.getName(), w.getName(), p.getName(), test));
		Assert.assertEquals(WrenchCard, game.disproveSuggestion(r.getName(), WrenchCard.getName(), p.getName(), test));
		Assert.assertEquals(game.getNullCard(), game.disproveSuggestion(r.getName(), w.getName(), p.getName(), test));
		
		//Test for one player, multiple possible matches
		second.addCard(TomCard);
		second.addCard(WrenchCard);
		second.addCard(KitchenCard);
		test2.add(second);
		for (int i = 0; i < 5; i++) {
			Card answer = game.disproveSuggestion(TomCard.getName(), WrenchCard.getName(), KitchenCard.getName(), test2);
			if (answer.equals(TomCard))
				playerCount++;
			else if (answer.equals(WrenchCard))
				weaponCount++;
			else if (answer.equals(KitchenCard))
				roomCount++;
			else
				nullCount++;
		}
		
		Assert.assertTrue(playerCount > 0);
		Assert.assertTrue(weaponCount > 0);
		Assert.assertTrue(roomCount > 0);
		Assert.assertEquals(nullCount, 0);
		
		//Test that all players are queried
		computer1.addCard(TomCard);
		computer2.addCard(LindaCard);
		computer3.addCard(RopeCard);
		human.addCard(WrenchCard);
		human.addCard(KitchenCard);
		test3.add(computer1);
		test3.add(computer2);
		test3.add(computer3);
		test3.add(human);
		
		//Suggestion that no player could disprove
		Assert.assertEquals(game.getNullCard(), game.disproveSuggestion("wrong", "wrong", "wrong", test3));
		//Suggestion that only the human could disprove
		Assert.assertEquals(human.getCards().get(1), game.disproveSuggestion("wrong", "wrong", KitchenCard.getName(), test3));
		//Make sure if person who made suggestion was only one who could disprove it, null is returned
		Assert.assertEquals(game.getNullCard(), game.disproveSuggestion(LindaCard.getName(), "wrong", "wrong", test3));
		//Test the order that players are queried
		/*for (int i = 0; i < test3.size(); i++) {
			System.out.println(test3.get(i).get);
		}*/
		Assert.assertEquals(computer1.getCards().get(0), game.disproveSuggestion(TomCard.getName(), "wrong", "wrong", test3));
	}
}
