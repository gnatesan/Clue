package GameBoard;

import game.BoardCell;

import java.util.*;

public class Board {
	private BoardCell[][] cells;
	Map<Character, String> rooms;
	int numRows;
	int numColumns;
	
	public static void loadGameBoardConfig(){
		
	}
	public static void loadRoomConfig(){
		
	}
	public BoardCell getBoardCell(int j, int i){
		return cells[i][j];
	}
	public Map<Character, String> getRooms() {
		return rooms;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public BoardCell getCellAt(int row, int col) {
		return cells[row][col];
	}
	public RoomCell getRoomCellAt(int i, int j) {
		return new RoomCell();
	}
	

}
