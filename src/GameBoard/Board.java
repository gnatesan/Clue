package GameBoard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {
	private static BoardCell[][] cells;
	Map<Character, String> rooms;
	private final int ROWS = 22;
	private final int COLUMNS = 23;
	private int numRows = 0;
	private int numColumns = 0;

	public Board(){
		super();
		cells = new BoardCell[ROWS][COLUMNS];
		rooms = new HashMap<Character, String>();
	}
	public void loadGameBoardConfig(String BoardConfigFile) throws FileNotFoundException, BadConfigFormatException{

		FileReader reader = new FileReader(BoardConfigFile);
		Scanner in = new Scanner(reader);
		int numRowsTemp = 0;

		while (in.hasNextLine()){
			String line = in.nextLine();
			List<String> temp = Arrays.asList(line.split(","));
			if (numColumns != 0 && numColumns != temp.size() ){
				throw new BadConfigFormatException();
			}
			int numColumnsTemp = 0;
			for (String s : temp){
				
				if (!s.isEmpty()){ //Helps parse for unwanted spaces. 
					if (!(rooms.containsKey(s.charAt(0))))
						throw new BadConfigFormatException();
					
					if (s != "W" || s != "X"){

						if (s.length() == 1){						
							cells[numRowsTemp][numColumnsTemp] = new RoomCell(numRowsTemp, numColumnsTemp, s.charAt(0), 'N');
						}
						else if (s.length() == 2){
							cells[numRowsTemp][numColumnsTemp] = new RoomCell(numRowsTemp, numColumnsTemp, s.charAt(0), s.charAt(1));

						}
					}

					else if(s == "W"){
						cells[numRowsTemp][numColumnsTemp] = new WalkwayCell(numRowsTemp, numColumnsTemp, s.charAt(0));
					}
					numColumnsTemp++;

				}
			}
			if (numRowsTemp == 0){
				setNumColumns(numColumnsTemp);
			}
			numRowsTemp++;
		}
		setNumRows(numRowsTemp);
	}

	public void loadRoomConfig(String BoardRoomConfigFile) throws FileNotFoundException{
		FileReader reader2 = new FileReader(BoardRoomConfigFile);
		Scanner in2 = new Scanner(reader2);
		while (in2.hasNextLine()){
			String line = in2.nextLine();
			List<String> temp = Arrays.asList(line.split(","));
			if (temp.size() != 2){
				throw new BadConfigFormatException();
			}
			rooms.put(line.charAt(0), line.substring(3));

		}

	}		
	private void setNumColumns(int numColumnsTemp) {
		numColumns = numColumnsTemp;		
	}
	private void setNumRows(int numRowsTemp) {
		numRows = numRowsTemp;		
	}
	public BoardCell getCellAt(int i, int j){
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
	public RoomCell getRoomCellAt(int i, int j) {
		return (RoomCell) cells[i][j];
	}



}
