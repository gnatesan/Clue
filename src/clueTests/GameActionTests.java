package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Card;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Suggestion;
import clueGame.WalkwayCell;

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
		Player first = new Player("first");
		Player second = new Player("second");
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
		
		Assert.assertEquals(KitchenCard, first.disproveSuggestion(KitchenCard.getName(), w.getName(), p.getName()));
		Assert.assertEquals(WrenchCard, first.disproveSuggestion(r.getName(), WrenchCard.getName(), p.getName()));
		Assert.assertEquals(null, first.disproveSuggestion(r.getName(), w.getName(), p.getName()));
		
		//Test for one player, multiple possible matches
		second.addCard(TomCard);
		second.addCard(WrenchCard);
		second.addCard(KitchenCard);
		for (int i = 0; i < 25; i++) {
			Card answer = second.disproveSuggestion(TomCard.getName(), WrenchCard.getName(), KitchenCard.getName());
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
		
		//Suggestion that no player could disprove
		Assert.assertEquals(null, first.disproveSuggestion("wrong", "wrong", "wrong"));
		//Suggestion that only the human could disprove
		Assert.assertEquals(WrenchCard, first.disproveSuggestion("wrong", "wrong", WrenchCard.getName()));
		//Test if last person who can disprove suggestion can do so
		Assert.assertEquals(KitchenCard, first.disproveSuggestion("wrong", "wrong", KitchenCard.getName()));
		//Test if first person who can disprove suggestion can do so
		Assert.assertEquals(LindaCard, first.disproveSuggestion(LindaCard.getName(), "wrong", "wrong"));
		//Make sure if person who made suggestion was only one who could disprove it, null is returned
		Assert.assertEquals(null, first.disproveSuggestion(TomCard.getName(), "wrong", "wrong"));
	}
	
	@Test
	public void testAllPlayersQueried(){
		//create arrayList of players
		ArrayList<Player> players = new ArrayList<Player>();

		players.add(new HumanPlayer("Col. Mustard"));
		players.add(new ComputerPlayer("Ms. Scarlet"));
		players.add(new ComputerPlayer("Mrs. White"));
		players.add(new ComputerPlayer("Rev. Green"));
		players.add(new ComputerPlayer("Mrs. Peacock"));
		players.add(new ComputerPlayer("Prof. Plum")); //No purple????????
		
		//assign each player cards
		//HOW DO WE WANT TO ASSIGN CARDS? CAN WE JUST CALL DEAL OR DO WE NEED TO INDIVIDUALLY ASSIGN MULTIPLE CARDS?
		Player tempPlayer = players.get(0);
		tempPlayer.addCard(new Card("Mrs. White", CardType.PERSON));
		tempPlayer = players.get(1);
		tempPlayer.addCard(new Card("Ms. Scarlet", CardType.PERSON));
		tempPlayer = players.get(2);
		tempPlayer.addCard(new Card("Mrs. Peacock", CardType.PERSON));
		tempPlayer = players.get(3);
		tempPlayer.addCard(new Card("Knife", CardType.WEAPON));
		tempPlayer = players.get(4);
		tempPlayer.addCard(new Card("Rope", CardType.WEAPON));
		tempPlayer = players.get(5);
		tempPlayer.addCard(new Card("Axe", CardType.WEAPON));

		//test making a suggestion that no players can disprove
		game.handleSuggestion("Prof. Plum", "Study", "Wrench", players.get(3));
		System.out.println(game.getDisproveCard().getName());
		
		assertTrue(game.getDisproveCard() == null);
		//function that returns player and card that disproved suggestion
		//make a suggestion that only one person can disprove
		game.handleSuggestion("Ms. Scarlet", "Study", "Wrench", players.get(4));
		System.out.println(game.getDisproveCard().getName());
		assertTrue(game.getDisproveCard().equals(new Card("Ms. Scarlet", CardType.PERSON)));
		
		
		//ensure that if person who made the suggestion was the only one who could disprove it, then null was returned
		game.handleSuggestion("Mrs. Peacock", "Study", "Wrench", players.get(2));
		assertTrue(game.getDisproveCard() == null);
		
		
		//make sure that if 2 players can disprove something, the first player does the disproving
		game.handleSuggestion("Mrs. Peacock", "Study", "Knife", players.get(1));
		assertTrue(game.getDisproveCard().equals(new Card("Mrs. Peacock", CardType.PERSON)));

	}
	
	@Test
	public void testComputerSuggestion() {
		int Ecount = 0;
		int Scount = 0;
		int randCount = 0;
		ComputerPlayer cp = new ComputerPlayer("computer");
		Suggestion one = new Suggestion("Emily", "Rope", "Ballroom");
		for (int i = 0; i < game.getPlayers().size(); i++) {
			for (int j = 0; j < game.getPlayers().get(i).getCards().size(); j++) {				
				if (game.getPlayers().get(i).getCards().get(j).getName().equals("Emily") ||
						game.getPlayers().get(i).getCards().get(j).getName().equals("Rope") ||
						game.getPlayers().get(i).getCards().get(j).getName().equals("Ballroom")) {
					continue;
				}
				
				cp.updateSeen(game.getPlayers().get(i).getCards().get(j));
			}
		}
		//Test suggestions aren't found in the seen card list
		Suggestion s1 = cp.createSuggestion("Ballroom", game.getCards());
		Assert.assertFalse(cp.getSeen().contains(s1.getPerson()));
		Assert.assertFalse(cp.getSeen().contains(s1.getWeapon()));
		Assert.assertFalse(cp.getSeen().contains(s1.getRoom()));
		
		
		ComputerPlayer cp2 = new ComputerPlayer("computer2");
		Suggestion two = new Suggestion("Susan", "Rope", "Ballroom");
		for (int x = 0; x < game.getPlayers().size(); x++) {
			for (int y = 0; y < game.getPlayers().get(x).getCards().size(); y++) {				
				if (game.getPlayers().get(x).getCards().get(y).getName().equals("Emily") ||
						game.getPlayers().get(x).getCards().get(y).getName().equals("Rope") ||
						game.getPlayers().get(x).getCards().get(y).getName().equals("Ballroom") ||
						game.getPlayers().get(x).getCards().get(y).getName().equals("Susan")) {
					continue;
				}
				
				cp.updateSeen(game.getPlayers().get(x).getCards().get(y));
			}
		}
		
		// Loop to get suggestions, count each suggestion returned
		for (int i = 0; i < 100; i++) {
			Suggestion s = cp2.createSuggestion("Ballroom", game.getCards());
			if (s.equals(one)) {
				Ecount++;
			} else if (s.equals(two)) {
				Scount++;
			} else {
				randCount++;
			}
		}
		
		//Test for 2 possible suggestions
		Assert.assertEquals(100, Ecount + Scount + randCount);
		Assert.assertTrue(Ecount > 0);
		Assert.assertTrue(Scount > 0);
		Assert.assertTrue(randCount > 0);
	}
}
