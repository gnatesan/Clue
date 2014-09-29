package GameBoard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {
	private static BoardCell[][] cells;
	Map<Character, String> rooms;
	private final int ROWS = 22;
	private final int COLUMNS = 23;

	public Board(){
		super();
		cells = new BoardCell[ROWS][COLUMNS];
	}
	public static void loadGameBoardConfig(String BoardConfigFile) throws FileNotFoundException{

		FileReader reader = new FileReader(BoardConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()){
			String line = in.nextLine();
			List<String> temp = Arrays.asList(line.split(","));
			int row = 0;
			int column = 0;
			for (String s : temp){
				if (!s.isEmpty()){ //Helps parse for unwanted spaces. 
					System.out.println(s.charAt(0));
					if (s != "W" || s != "X"){

						cells[row][column] = new RoomCell(row, column, s.charAt(0));
					}

					else if(s == "W"){
						cells[row][column] = new WalkwayCell(row, column, s.charAt(0));
					}
					column++;

				}
			}
			System.out.println('\n'); 
			row++;
		}
	}
	public static void loadRoomConfig(String BoardRoomConfigFile){

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
