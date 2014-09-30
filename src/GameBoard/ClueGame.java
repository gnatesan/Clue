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
	/*
	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException{
		clueBoard.loadGameBoardConfig(BoardConfig);
		clueBoard.loadRoomConfig(BoardRoomConfig);
		
	}*/
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
			clueBoard.loadRoomConfig(BoardRoomConfig);

	}

	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		clueBoard.loadGameBoardConfig(BoardConfig);
	
	}


}


//To reset origin master to old commit:

//git checkout master
//git reset --hard e3f1e37
//git push --force origin master
//# Then to prove it (it won't print any diff)
//git diff master..origin/master
