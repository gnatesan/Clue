package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ClueGame {
	private Board clueBoard;
	private String BoardConfig;
	private String BoardRoomConfig;
	private ArrayList<Player> players;
	private ArrayList<Card> cards;
	
	public ClueGame(String s1, String s2) {
		super();
		BoardConfig = s1;
		BoardRoomConfig = s2;
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
		players = new ArrayList<Player>(6);
		cards = new ArrayList<Card>(6);
	}
	public ClueGame() {
		super();
		BoardConfig = "ClueLayout.csv";
		BoardRoomConfig = "ClueLegend.txt";
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
		players = new ArrayList<Player>(6);
		cards = new ArrayList<Card>(6);
	}
	public Board getBoard() {
		return clueBoard;
	}
	public void loadConfigFiles() {
		try{
			this.loadRoomConfig();
			this.loadBoardConfig();
			this.loadPlayerConfig();
			this.loadCardConfig("CardConfig.csv");
		} catch (FileNotFoundException | BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
		clueBoard.calcAdjacencies();
	}
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		clueBoard.loadRoomConfig();
	}
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		clueBoard.loadBoardConfig();

	}
	
	//GETTER AND SETTER METHODS FOR PLAYER ARRAYLIST, FOR TESTING PURPOSES ONLY
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException{
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
			}
			else {
				test = new ComputerPlayer(line[0]);
			}
			test.setColor(line[1]);
			test.setRow(Integer.parseInt(line[2]));
			test.setCol(Integer.parseInt(line[3]));
			players.add(test);
		}
		in.close();
	}
	
	public void loadCardConfig(String config) throws FileNotFoundException, BadConfigFormatException {
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
				throw new BadConfigFormatException("Invalid card configuration format.");
			}
			
			this.cards.add(nextCard);
		}
		
		in.close();
	}
	
	public ArrayList<Card> getCards() {
		return this.cards;
	}
}


//To reset origin master to old commit:

//git checkout master
//git reset --hard e3f1e37
//git push --force origin master
//# Then to prove it (it won't print any diff)
//git diff master..origin/master
