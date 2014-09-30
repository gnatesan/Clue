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
	private static int dontTouchTheseRows = 0;
	private static int dontTouchTheseColumns = 0;

	public Board(){
		super();
		cells = new BoardCell[ROWS][COLUMNS];
	}
	public static void loadGameBoardConfig(String BoardConfigFile) throws FileNotFoundException{

		FileReader reader = new FileReader(BoardConfigFile);
		Scanner in = new Scanner(reader);
		numRows = 0;
		
		while (in.hasNextLine()){
			if (numRows > dontTouchTheseRows){
					dontTouchTheseRows = numRows;
				}
			System.out.println(numRows);
			System.out.println(dontTouchTheseRows);
			String line = in.nextLine();
			List<String> temp = Arrays.asList(line.split(","));
			numColumns = 0;
			for (String s : temp){
			
				if (!s.isEmpty()){ //Helps parse for unwanted spaces. 
					if (s != "W" || s != "X"){
						
						if (numColumns > dontTouchTheseColumns){
							dontTouchTheseColumns = numColumns;
						}
						
						if (s.length() == 1){						
						cells[numRows][numColumns] = new RoomCell(numRows, numColumns, s.charAt(0), 'N');
						System.out.println(s.charAt(0));
						}
						else if (s.length() == 2){
							cells[numRows][numColumns] = new RoomCell(numRows, numColumns, s.charAt(0), s.charAt(1));
							System.out.println("Door going");
							System.out.println(s.charAt(1));
						}
					}

					else if(s == "W"){
						cells[numRows][numColumns] = new WalkwayCell(numRows, numColumns, s.charAt(0));
					}
					numColumns++;

				}
			}
			numRows++;
		}
		
	}
	public static void loadRoomConfig(String BoardRoomConfigFile){

	}
	public BoardCell getCellAt(int i, int j){
		return cells[i][j];
	}
	public Map<Character, String> getRooms() {
		return rooms;
	}
	public int getNumRows() {
		//System.out.println(dontTouchTheseRows);
		return dontTouchTheseRows;
	}
	public int getNumColumns() {
		//System.out.println(dontTouchTheseColumns);
		return dontTouchTheseColumns;
	}
	
	//TODO: Need to make other getters to access cells.
	
	public RoomCell getRoomCellAt(int i, int j) {
		//System.out.println((RoomCell) cells[i][j]);
		return (RoomCell) cells[i][j];
	}

	

}
