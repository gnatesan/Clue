//Test comment.
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
//Second commit test
public class GameActionTests {

	private static ClueGame game;
	private static Board board;
	private static Card TomCard;
	private static Card LindaCard;
	private static Card WrenchCard;
	private static Card RopeCard;
	private static Card KitchenCard;
	private static Card LoungeCard;
	private static Card GunCard;


	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException{
		game = new ClueGame("ClueLayoutOurs.csv", "ClueLegend.txt");
		TomCard = new Card("Tom", Card.CardType.PERSON);
		LindaCard = new Card("Linda", Card.CardType.PERSON);
		WrenchCard = new Card("Wrench", Card.CardType.WEAPON);
		RopeCard = new Card("Rope", Card.CardType.WEAPON);
		KitchenCard = new Card("Kitchen", Card.CardType.ROOM);
		LoungeCard = new Card("Lounge", Card.CardType.ROOM);
		GunCard = new Card("Gun", Card.CardType.WEAPON);
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
		ArrayList <Player> group = new ArrayList();
		Player first = new Player("first");
		Player second = new Player("second");
		group.add(first);
		group.add(second);
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
		//System.out.println(game.);
		//Assert.assertEquals(null, first.disproveSuggestion(RopeCard.getName(), "wrong", "wrong"));
		game.handleSuggestion(LindaCard.getName(), "wrong", "wrong", first, group);
		Assert.assertEquals(null, game.getDisproveCard());
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
		game.handleSuggestion("Prof. Plum", "Study", "Wrench", players.get(3), players);
		//System.out.println(game.getDisproveCard().getName());

		assertTrue(game.getDisproveCard() == null);
		//function that returns player and card that disproved suggestion
		//make a suggestion that only one person can disprove
		game.handleSuggestion("Ms. Scarlet", "Study", "Wrench", players.get(4), players);
		//System.out.println(game.getDisproveCard().getName());
		assertTrue(game.getDisproveCard().equals(new Card("Ms. Scarlet", CardType.PERSON)));


		//ensure that if person who made the suggestion was the only one who could disprove it, then null was returned
		game.handleSuggestion("Mrs. Peacock", "Study", "Wrench", players.get(2), players);
		assertTrue(game.getDisproveCard() == null);


		//make sure that if 2 players can disprove something, the first player does the disproving
		game.handleSuggestion("Mrs. Peacock", "Study", "Knife", players.get(1), players);
		assertTrue(game.getDisproveCard().equals(new Card("Mrs. Peacock", CardType.PERSON)));

	}

	@Test
	public void testCheckingAComputerSuggestion() {
		ComputerPlayer cp = new ComputerPlayer("John Blue");
		cp.getDeck(game.getCards());
		//adds all the people except Dave and Emily
		cp.updateSeen(new Card("John", CardType.PERSON));
		cp.updateSeen(new Card("Susan", CardType.PERSON));
		cp.updateSeen(new Card("Tom", CardType.PERSON));
		cp.updateSeen(new Card("Linda", CardType.PERSON));
		//adds all the weapons except Candlestick and Wrench
		cp.updateSeen(new Card("Knife", CardType.WEAPON));
		cp.updateSeen(new Card("Study", CardType.ROOM));
		cp.updateSeen(new Card("Revolver", CardType.WEAPON));
		cp.updateSeen(new Card("Rope", CardType.WEAPON));
		cp.updateSeen(new Card("Lead pipe", CardType.WEAPON));

		Suggestion s = cp.createSuggestion("Billiard Room");
		assertEquals(s.getRoom().getName(), "Billiard Room");

		int plum = 0;
		int green = 0;
		int wrench = 0;
		int candlestick = 0;
		// Run the test 100 times
		Suggestion s2 = new Suggestion("hello", "h", "j");
		for (int i=0; i<100; i++) {
			s2 = cp.createSuggestion("Billiard Room");
			//System.out.println(s2.getPerson() + " " + s2.getWeapon());
			//System.out.println("hello");
			if (s2.getPerson().getName().equals("Dave"))
				plum++;
			else if (s2.getPerson().getName().equals("Emily"))
				green++;
			else
				fail("Invalid person selected");
			if (s2.getWeapon().getName().equals("Candlestick"))
				candlestick++;
			else if (s2.getWeapon().getName().equals("Wrench"))
				wrench++;
			else
				fail("Invalid weapon selected");
		}
		// Ensure we have 100 total selections (fail should also ensure)
		assertEquals(100, plum + green);
		assertEquals(100, wrench + candlestick);
		// Ensure each target was selected more than once
		assertTrue(plum > 10);
		assertTrue(green > 10);
		assertTrue(wrench > 10);	
		assertTrue(candlestick > 10);	
		assertTrue(!cp.getCardsSeen().contains(new Card(s2.getPerson().getName(), CardType.PERSON)));
		assertTrue(!cp.getCardsSeen().contains(new Card(s2.getPerson().getName(), CardType.WEAPON)));

		//tests to see that there is only one possible suggestion and it picks it
		cp.updateSeen(new Card("Candlestick", CardType.WEAPON));
		cp.updateSeen(new Card("Emily", CardType.PERSON));

		s = cp.createSuggestion("Billiard Room");
		assertEquals(s.getRoom().getName(), "Billiard Room");
		assertEquals(s.getPerson().getName(), "Dave");
		assertEquals(s.getWeapon().getName(), "Wrench");
	}	 
}