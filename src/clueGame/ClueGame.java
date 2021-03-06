package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
	private final int MAX_NUM_ON_DIE = 6;
	private int randomNumber;
	private boolean humanMustFinish;
	private Suggestion suggest;
	private boolean madeSuggestion;
	private ControlGui control;

	public ClueGame(String s1, String s2) {
		//super();
		BoardConfig = s1;
		BoardRoomConfig = s2;
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
		clueBoard.getGame(this);
		players = new ArrayList<Player>(6);
		cards = new ArrayList<Card>();
		clueBoard.calcAdjacencies();
		this.addMouseListener(clueBoard);
		madeSuggestion = false;
	}

	private void setUpGui() {
		add(this.clueBoard, BorderLayout.CENTER);
		control = new ControlGui(this);
		clueBoard.setGui(control);
		add(control, BorderLayout.SOUTH);
		add(new CardPanel(getHuman().getCards()), BorderLayout.EAST);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Game");
		setSize(720, 800);
		createMenu();
		setVisible(true);
		JOptionPane.showMessageDialog(this, "You are " + getName() + ", press Next Player to begin playing", 
				"Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		det = new DetectiveDialog(cards);
	}

	public HumanPlayer getHuman(){
		//returns the human player
		return ((HumanPlayer)players.get(0));
	}
	
	public Suggestion getSuggestion(){
		return suggest;
	}
	
	public boolean getMadeSuggestion(){
		return madeSuggestion;
	}
	
	public void setMadeSuggestion(boolean tf){
		madeSuggestion = tf;
	}

	public String getName(){
		//gets and returns the name of the human player
		return players.get(0).getName();
	}

	public int getRandomNumber(){
		return randomNumber;
	}

	public int roll(){
		Random rand = new Random();
		randomNumber = rand.nextInt(MAX_NUM_ON_DIE-1) + 1;
		return randomNumber;
	}

	public void makeMove(ComputerPlayer p, int roll){
		madeSuggestion = false;

		//see if we need to make an accusation
		if(p.getMakeAccusation()){
			if(checkAccusation(p.createAccusation())){
				Solution s = p.createAccusation();
				JOptionPane.showMessageDialog(this,p.getName() + " wins!", p.getName() + " guessed correctly. The answer was " + s.getPerson() + " in the "+ s.getRoom() + " with the " + s.getWeapon() , JOptionPane.INFORMATION_MESSAGE);
				//stop the game if they win
				System.exit(0);
			}
			p.setMakeAccusation(false);
		}
		//choose the target		
		//calculate the targets and pick one
		clueBoard.calcTargets(p.getRow(), p.getCol(), randomNumber);
		BoardCell newLoc = p.pickLocation(clueBoard.getTargets());
		
		//check to see if selected location is a room
		//if it is, then call makeSuggestion
		if(newLoc instanceof RoomCell){
			Map<Character, String> rooms = getBoard().getRooms();
			suggest = p.createSuggestion(rooms.get(((RoomCell) newLoc).getInitial()));
			handleSuggestion(suggest.getPerson().getName(), suggest.getRoom().getName(), suggest.getWeapon().getName(), p, players);
			//need to update GUI with guess and result
			madeSuggestion = true;
		}

		//update players row and column
		p.setRow(newLoc.getRow());
		p.setCol(newLoc.getColumn());

		//repaint board
		getBoard().repaint();

		//handle suggestion and accusation(next part)
	}

	public Player getTurn() {
		return turn;
	}

	public void setTurn() {
		int index = players.indexOf(turn);
		if(index != players.size()-1){
			turn = players.get(index+1);
		}
		else{
			turn = players.get(0);
			turn = (HumanPlayer)turn;
			
			humanMustFinish = true;
			clueBoard.setHumanMustFinish(true);
			//have to change setAccuse to true at some point
		}
		clueBoard.setTurn(turn);
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

	public boolean isHumanMustFinish() {
		return humanMustFinish;
	}

	public void setHumanMustFinish(boolean humanMustFinish) {
		this.humanMustFinish = humanMustFinish;
	}

	public void handleSuggestion(String person, String room, String weapon, Player accuser, ArrayList<Player> group) {
		suggest = new Suggestion(person, weapon, room);
		
		ArrayList<Card> choices = new ArrayList<Card>();
		for (int i = 0; i < group.size(); i++) {
			if(group.get(i).getName().equals(person)){
				Player p = group.get(i);
				p.setRow(accuser.getRow());
				p.setCol(accuser.getCol());
				getBoard().repaint();
			}
			if (!group.get(i).equals(accuser)) {
				disproveCard = group.get(i).disproveSuggestion(person, room, weapon);
				if (disproveCard != null)
					break;
			}
		}
		//if the suggestion was not disproved, player should make an accusation next turn
		if(disproveCard==null && accuser instanceof ComputerPlayer){
			((ComputerPlayer)accuser).setMakeAccusation(true);
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
			if (first) {
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
		//set the starting player
		turn = players.get(players.size()-1);
		clueBoard.setTurn(turn);
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

}

// To reset origin master to old commit:
// git checkout master
// git reset --hard e3f1e37
// git push --force origin master
// # Then to prove it (it won't print any diff)
// git diff master..origin/master
