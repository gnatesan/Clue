package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import clueGame.Card.CardType;

public class ClueGame extends JFrame{
	private Board clueBoard;
	private String BoardConfig;
	private String BoardRoomConfig;
	private ArrayList<Player> players;
	private ArrayList<Card> cards;
	private Card cannotDisprove;
	private Solution solution;
	private Solution answer;
	private Player turn;
	private JMenuBar bar;
	private JMenu file;
	private JMenuItem notes;
	private JMenuItem exit;
	private DetectiveDialog det;
	private Card disproveCard;
	
	public ClueGame(String s1, String s2) {
		//super();
		BoardConfig = s1;
		BoardRoomConfig = s2;
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
		players = new ArrayList<Player>(6);
		cards = new ArrayList<Card>();
	}
	
	private void setUpGui() {
		add(this.clueBoard, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(600, 600);
		createMenu();
		setVisible(true);
		det = new DetectiveDialog(cards);
	}

	public Player getTurn() {
		return turn;
	}

	public void setTurn(Player turn) {
		this.turn = turn;
	}

	public ClueGame() {
		super();
		BoardConfig = "ClueLayout.csv";
		BoardRoomConfig = "ClueLegend.txt";
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
		players = new ArrayList<Player>(6);
		cards = new ArrayList<Card>();
		cannotDisprove = new Card(null, Card.CardType.ROOM);
	}

	public Board getBoard() {
		return clueBoard;
	}

	public void loadConfigFiles() {
		try {
			this.loadRoomConfig();
			this.loadBoardConfig();
			this.loadPlayerConfig();
			this.loadCardConfig("CardConfig.csv");
			for(Player p: players){
				if(p instanceof ComputerPlayer){
					((ComputerPlayer)p).getDeck(cards);
				}
			}
			this.dealCards();
			
			clueBoard.setPlayers(players);
			this.setUpGui();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		clueBoard.calcAdjacencies();
	}

	public void loadRoomConfig() throws FileNotFoundException,
			BadConfigFormatException {
		clueBoard.loadRoomConfig();
	}

	public void loadBoardConfig() throws FileNotFoundException,
			BadConfigFormatException {
		clueBoard.loadBoardConfig();

	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void loadPlayerConfig() throws FileNotFoundException,
			BadConfigFormatException {
		Player test;
		boolean first = true;
		String[] line;
		FileReader reader = new FileReader("Player.txt");
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			line = in.nextLine().split(" ");
			if (!first) {
				test = new HumanPlayer(line[0]);
				first = false;
			} else {
				test = new ComputerPlayer(line[0]);
			}
			test.setColor(line[1]);
			test.setRow(Integer.parseInt(line[2]));
			test.setCol(Integer.parseInt(line[3]));
			players.add(test);
		}
		in.close();
	}

	public void loadCardConfig(String config) throws FileNotFoundException,
			BadConfigFormatException {
		FileReader reader = new FileReader(config);
		Scanner in = new Scanner(reader);
		String[] line;

		while (in.hasNextLine()) {
			line = in.nextLine().split(",");
			Card nextCard;

			switch (line[0].trim()) {
			case "person":
				nextCard = new Card(line[1].trim(), Card.CardType.PERSON);
				break;
			case "weapon":
				nextCard = new Card(line[1].trim(), Card.CardType.WEAPON);
				break;
			case "room":
				nextCard = new Card(line[1].trim(), Card.CardType.ROOM);
				break;
			default:
				throw new BadConfigFormatException(
						"Invalid card configuration format.");
			}

			this.cards.add(nextCard);
		}
		in.close();
		
	}

	public void dealCards() {
		ArrayList<Card> tempList = new ArrayList<Card>(this.cards);
		// Choose random cards from the list for the solution
		Random rand = new Random();
		Card one = tempList.get(rand.nextInt(6));
		Card two = tempList.get(rand.nextInt(6) + 6);
		Card three = tempList.get(rand.nextInt(9) + 12);

		this.solution = new Solution(one.getName(), two.getName(),
				three.getName());

		tempList.remove(one);
		tempList.remove(two);
		tempList.remove(three);

		int player;
		// Go from 0 to size - 1 of the cards and apply modulus to get player #
		int totalCards = tempList.size();
		for (int i = 0; i < totalCards; i++) {
			player = i % 6;
			Card nextCard = tempList.get(rand.nextInt(tempList.size()));

			this.players.get(player).addCard(nextCard);

			tempList.remove(nextCard);
		}
	}

	public ArrayList<Card> getCards() {
		return this.cards;
	}

	public boolean checkAccusation(Solution sol) {
		if (sol.getPerson().equals(solution.getPerson())) {
			if (sol.getWeapon().equals(solution.getWeapon())) {
				if (sol.getRoom().equals(solution.getRoom())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accuser, ArrayList<Player> group) {
			ArrayList<Card> choices = new ArrayList<Card>();
			for (int i = 0; i < group.size(); i++) {
				if (!group.get(i).equals(accuser)) {
					disproveCard = group.get(i).disproveSuggestion(person, room, weapon);
				if (disproveCard != null)
					break;
				}
			}
			
			//accusingPerson is the person making the suggestion
			int playerPosition = 0;
			int initialPosition = 0;

			//find accusingPerson in arrayList of players, 
			for(int i = 0; i < group.size(); i++) {
				if(group.get(i).equals(accuser)) {

					playerPosition = i;
					initialPosition = i;
				}
			}

			if(playerPosition == group.size()-1) {
				playerPosition = 0;
			}
			else playerPosition++;


			//loop through and ask each player to disprove suggestion

			//for person at that location, call disprove suggestion
			disproveCard = group.get(playerPosition).disproveSuggestion(person, weapon, room);
			while(disproveCard == null && (playerPosition != initialPosition)) {

				if(playerPosition == group.size()-1) {
					playerPosition = 0;
				}
				else playerPosition++;
				if(playerPosition != initialPosition) {
					disproveCard = group.get(playerPosition).disproveSuggestion(person, weapon, room);
				}
			}
			if(disproveCard != null) {
				//update seenCards
				for(Player p: group) {
					if(p instanceof ComputerPlayer){
						p = (ComputerPlayer)p;
						if(((ComputerPlayer)p).getSeen().contains(disproveCard));
						((ComputerPlayer)p).updateSeen(disproveCard);
					}
				}
			}
			//if someone can disprove the suggestion, then disproveSuggestion will return a card instead of null and stop
			
	}

	public Card getDisproveCard() {
		return disproveCard;
	}

	public Solution getSolution() {
		return solution;
	}


	public Card getNullCard() {
		return cannotDisprove;
	}
	
	public static void main(String args[]) {
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
	}

	public void createMenu() {
		bar = new JMenuBar();
		file = new JMenu("File");
		bar.add(file);
		notes = new JMenuItem("Show Detective Notes");
		//ourNotes = new DetectiveNotes();
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		notes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				det.setVisible(true);
			}
		});
		file.add(notes);
		file.add(exit);
		setJMenuBar(bar);
		
	}

}

// To reset origin master to old commit:
// git checkout master
// git reset --hard e3f1e37
// git push --force origin master
// # Then to prove it (it won't print any diff)
// git diff master..origin/master
