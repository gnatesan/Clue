package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class ClueGame {
	private Board clueBoard;
	private String BoardConfig;
	private String BoardRoomConfig;
	private ArrayList <Player> players;
	
	public ClueGame(String s1, String s2) {
		super();
		BoardConfig = s1;
		BoardRoomConfig = s2;
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
		players = new ArrayList <Player> (6);
	}
	public ClueGame() {
		super();
		BoardConfig = "ClueLayout.csv";
		BoardRoomConfig = "ClueLegend.txt";
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
		players = new ArrayList <Player> (6);
	}
	public Board getBoard() {
		return clueBoard;
	}
	public void loadConfigFiles() {
		try{
			this.loadRoomConfig();
			this.loadBoardConfig();
			this.loadPlayerConfig();
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
		//Player test = new Player("wrong name");
		Player test = new Player("wrong name");
		test.setColor("wrong color");
		test.setRow(-1);
		test.setCol(-1);
		players.add(test);
	}
}


//To reset origin master to old commit:

//git checkout master
//git reset --hard e3f1e37
//git push --force origin master
//# Then to prove it (it won't print any diff)
//git diff master..origin/master
