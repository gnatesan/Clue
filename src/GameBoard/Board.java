package GameBoard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {
	private static BoardCell[][] cells;
	Map<Character, String> rooms;
	private final int ROWS = 22;
	private final int COLUMNS = 23;
	private static int numRows = 0;
	private static int numColumns = 0;

	public Board(){
		super();
		cells = new BoardCell[ROWS][COLUMNS];
	}
	public static void loadGameBoardConfig(String BoardConfigFile) throws FileNotFoundException{

		FileReader reader = new FileReader(BoardConfigFile);
		Scanner in = new Scanner(reader);
		numRows = 0;
		
		while (in.hasNextLine()){
			String line = in.nextLine();
			List<String> temp = Arrays.asList(line.split(","));
			numColumns = 0;
			for (String s : temp){
				if (!s.isEmpty()){ //Helps parse for unwanted spaces. 
					System.out.println(s.charAt(0));
					if (s != "W" || s != "X"){
						
						cells[numRows][numColumns] = new RoomCell(numRows, numColumns, s.charAt(0));
						if =
					}

					else if(s == "W"){
						cells[numRows][numColumns] = new WalkwayCell(numRows, numColumns, s.charAt(0));
					}
					numColumns++;

				}
			}
			System.out.println('\n'); 
			numRows++;
		}
		
	}
	public static void loadRoomConfig(String BoardRoomConfigFile){

	}
	public BoardCell getBoardCell(int i, int j){
		return cells[i][j];
	}
	public Map<Character, String> getRooms() {
		return rooms;
	}
	public int getNumRows() {
		int x = numRows;
		return x;
	}
	public int getNumColumns() {
		int x = numColumns;
		return x;
	}
	
	//TODO: Need to make other getters to access cells.
	
	/*
	public RoomCell getRoomCellAt(int i, int j) {
		return cells[]
	}
*/

}
