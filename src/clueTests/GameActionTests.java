package clueTests;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Suggestion;

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
		game = new ClueGame("ClueLayoutOurs.csv", "ClueLegend.txt");
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
	
	// Test that the computer player selects a room at random
	@Test
	public void testPickLocationSelectsRandom() {
		ComputerPlayer computer = new ComputerPlayer("Test");
		
		// Calculate the target given one move
		board.calcTargets(14, 16, 1);
		
		// Totals of all adjacent spaces
		int loc_13_16 = 0;
		int loc_15_16 = 0;
		int loc_14_15 = 0;
		
		// Iterate 100 times to get a reasonable distribution
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
		
		// Probabilistically, there should be more than 10 hits on each square for 100 runs
		Assert.assertEquals(100, loc_13_16 + loc_15_16 + loc_14_15);
		Assert.assertTrue(loc_13_16 > 10);
		Assert.assertTrue(loc_15_16 > 10);
		Assert.assertTrue(loc_14_15 > 10);
		
	}
	
	// Test that it picks a room with two steps
	@Test
	public void testPickLocationSelectsRoomTwoSteps() {
		ComputerPlayer computer = new ComputerPlayer("Test");
		
		// Two step target
		board.calcTargets(13, 16, 2);
		
		BoardCell selected = computer.pickLocation(board.getTargets());
		
		// Assert that it picks the correct doorway
		Assert.assertEquals(12, selected.getRow());
		Assert.assertEquals(17, selected.getColumn());
	}
	
	// Test it selects the room/doorway for one move
	@Test
	public void testPickLocationSelectsRoom() {
		ComputerPlayer computer = new ComputerPlayer("Test");
		
		// One move calcTargets
		board.calcTargets(9,9,1);

		BoardCell selected = computer.pickLocation(board.getTargets());
		
		// Assert at doorway location
		Assert.assertEquals(9, selected.getRow());
		Assert.assertEquals(8, selected.getColumn());
	}
	
	// Assert that it the computer player picks a random space in a room it's already visited
	@Test
	public void testPickLocationRandomFromIfVisitedRoomInTargetList() {
		ComputerPlayer computer = new ComputerPlayer("Test");
		
		// CalcTargets for one move
		board.calcTargets(9,9,1);
		
		// Totals for each adjacent square
		int loc_8_9 = 0;
		int loc_9_8 = 0;
		int loc_10_9 = 0;
		
		// Run it 100 times and add up the hits on each of the targets.
		for (int i = 0; i < 100; i++) {
			computer.setLastRoomVisited('L');
			BoardCell selected = computer.pickLocation(board.getTargets());
			
			if (selected == board.getCellAt(8, 9)) {
				loc_8_9++;
			} else if (selected == board.getCellAt(9, 8)) {
				loc_9_8++;
			} else if (selected == board.getCellAt(10, 9)) {
				loc_10_9++;
			}
		}
		
		// Assert final totals
		Assert.assertEquals(100, loc_8_9 + loc_9_8 + loc_10_9);
		Assert.assertTrue(loc_8_9 > 0);
		Assert.assertTrue(loc_9_8 > 0);
		Assert.assertTrue(loc_10_9 > 0);
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
		for (int i = 0; i < 25; i++) {
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
		Assert.assertEquals(human.getCards().get(0), game.disproveSuggestion("wrong", "wrong", WrenchCard.getName(), test3));
		//Test if last person who can disprove suggestion can do so
		Assert.assertEquals(human.getCards().get(1), game.disproveSuggestion("wrong", "wrong", KitchenCard.getName(), test3));
		//Test if first person who can disprove suggestion can do so
		Assert.assertEquals(computer2.getCards().get(0), game.disproveSuggestion(LindaCard.getName(), "wrong", "wrong", test3));
		//Make sure if person who made suggestion was only one who could disprove it, null is returned
		game.setTurn(test3.get(0));
		Assert.assertEquals(null, game.disproveSuggestion(TomCard.getName(), "wrong", "wrong", test3));
		test3.add(test3.get(0));
		test3.remove(0);
	}
	
	@Test
	public void testComputerSuggestion() {
		int Ecount = 0;
		int Scount = 0;
		ComputerPlayer cp = new ComputerPlayer("computer");
		Suggestion one = new Suggestion("Emily", "Rope", "Ballroom");
		for (int i = 0; i < game.getPlayers().size(); i++) {
			for (int j = 0; j < game.getPlayers().get(i).getCards().size(); j++) {
				if (game.getPlayers().get(i).getCards().get(j).getName() != "Emily" && game.getPlayers().get(i).getCards().get(j).getName() != "Rope" && game.getPlayers().get(i).getCards().get(j).getName() != "Ballroom") {
					//System.out.println(game.getPlayers().get(i).getCards().get(j).getName());
					cp.updateSeen(game.getPlayers().get(i).getCards().get(j));
				}
			}
		}
		//Test for only suggestion is possible
		//Assert.assertEquals(cp.createSuggestion("Ballroom"), one);
		
		ComputerPlayer cp2 = new ComputerPlayer("computer2");
		Suggestion two = new Suggestion("Emily", "Rope", "Ballroom");
		Suggestion three = new Suggestion("Susan", "Rope", "Ballroom");
		for (int x = 0; x < game.getPlayers().size(); x++) {
			for (int y = 0; y < game.getPlayers().get(x).getCards().size(); y++) {
				if (game.getPlayers().get(x).getCards().get(y).getName() != "Emily" && game.getPlayers().get(x).getCards().get(y).getName() != "Rope" && game.getPlayers().get(x).getCards().get(y).getName() != "Ballroom" && game.getPlayers().get(x).getCards().get(y).getName() != "Susan") {
					cp2.updateSeen(game.getPlayers().get(x).getCards().get(y));
				}	
			}
		}
		/*for (int a = 0; a < 15; a++) {
			Random r = new Random();
			int index = r.nextInt();
			
		}*/
		Assert.assertTrue(Ecount > 0);
		Assert.assertTrue(Scount > 0);
	}
}
