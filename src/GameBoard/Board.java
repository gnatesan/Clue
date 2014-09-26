package GameBoard;

import java.util.*;

public class Board {
	private BoardCell[][] cells;
	Map<Character, String> rooms;
	int numRows;
	int numColumns;
	
	public static void loadBoardConfig(){
		
	}
	public BoardCell getBoardCell(int j, int i){
		return cells[i][j];
	}
	

}
