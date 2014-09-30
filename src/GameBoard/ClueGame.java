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
		clueBoard = new Board();
		BoardConfig = s1;
		BoardRoomConfig = s2;
				
	}
	public Board getBoard(){
		return clueBoard;
	}
	public void loadConfigFiles(){
		try{
		clueBoard.loadGameBoardConfig(BoardConfig);
		} catch(FileNotFoundException e){
			e.getMessage();
		}
		try {
			clueBoard.loadRoomConfig(BoardRoomConfig);
		} catch (FileNotFoundException e) {
			e.getMessage();
		}
		
	}

	

}
