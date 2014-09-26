package GameBoard;

import java.util.*;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board clueBoard;
	
	
	public ClueGame(String s1, String s2) {
		super();
		rooms = new HashMap<Character, String>();
		// TODO Auto-generated constructor stub
	}
	public Board getBoard(){
		return clueBoard;
	}
	public void loadConfigFiles(){
		Board.loadGameBoardConfig();
		Board.loadRoomConfig();
		
	}

	

}
