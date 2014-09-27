package GameBoard;

import java.io.FileNotFoundException;
import java.util.*;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board clueBoard;
	private String BoardConfig;
	private String BoardRoomConfig;
	
	
	public ClueGame(String s1, String s2) {
		super();
		rooms = new HashMap<Character, String>();
		BoardConfig = s1;
		BoardRoomConfig = s2;
				
	}
	public Board getBoard(){
		return clueBoard;
	}
	public void loadConfigFiles(){
		try{
		Board.loadGameBoardConfig(BoardConfig);
		} catch(FileNotFoundException e){
			e.getMessage();
		}
		Board.loadRoomConfig(BoardRoomConfig);
		
	}

	

}
