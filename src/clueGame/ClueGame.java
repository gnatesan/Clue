package clueGame;

import java.io.FileNotFoundException;

public class ClueGame {
	private Board clueBoard;
	private String BoardConfig;
	private String BoardRoomConfig;
	public ClueGame(String s1, String s2) {
		super();
		BoardConfig = s1;
		BoardRoomConfig = s2;
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
	}
	public ClueGame() {
		super();
		BoardConfig = "ClueLayout.csv";
		BoardRoomConfig = "ClueLegend.txt";
		clueBoard = new Board(BoardConfig, BoardRoomConfig);
	}
	public Board getBoard() {
		return clueBoard;
	}
	public void loadConfigFiles() {
		try{
			this.loadRoomConfig();
			this.loadBoardConfig();
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
}


//To reset origin master to old commit:

//git checkout master
//git reset --hard e3f1e37
//git push --force origin master
//# Then to prove it (it won't print any diff)
//git diff master..origin/master
