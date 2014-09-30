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
	private Map<BoardCell, LinkedList<BoardCell>> adjacencyLists;
	private LinkedList<BoardCell> cellAdjList;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;

	public Board(){
		super();
		cells = new BoardCell[ROWS][COLUMNS];
		rooms = new HashMap<Character, String>();
		adjacencyLists = new HashMap<BoardCell, LinkedList<BoardCell>>();
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
	
	public void calcAdjacencies(){
		for(int i=0; i < numRows; i++){
			for(int j=0; j < numColumns; j++){
				cellAdjList = new LinkedList<BoardCell>();
				if (i-1 >= 0){
					cellAdjList.add(cells[i-1][j]);
				}
				if (i+1 < numRows){
					cellAdjList.add(cells[i+1][j]);
				}
				if (j-1 >= 0){
					cellAdjList.add(cells[i][j-1]);
				}
				if (j+1 < numColumns){
					cellAdjList.add(cells[i][j+1]);
				}
				adjacencyLists.put(cells[i][j], cellAdjList);
			}
		}
	}
	
	//DON'T MESS WITH THIS YET
	public void calcTargets(BoardCell thisCell, int numSteps){
		for (int i = 0; i < adjacencyLists.get(thisCell).size(); i++){
			visited.add(adjacencyLists.get(thisCell).get(i));
			if (numSteps == 1 ){
				targets.add(adjacencyLists.get(thisCell).get(i));
			}

			else {	
				calcTargets(adjacencyLists.get(thisCell).get(i), numSteps-1);
				visited.remove(adjacencyLists.get(thisCell).get(i));
			}			
		}
	}
	
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adjacencyLists.get(cell);
	}
	
	public Set<BoardCell> getTargets(BoardCell cell, int i){
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(cell);
		calcTargets(cell, i);
		targets.remove(cell);
		return targets;
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


//Test Comment
	
}
