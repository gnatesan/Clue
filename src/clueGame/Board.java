package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import clueGame.RoomCell.DoorDirection;

public class Board {
	private BoardCell[][] cells;
	Map<Character, String> rooms;
	private final int ROWS = 22;
	private final int COLUMNS = 23;
	private int numRows = 0;
	private int numColumns = 0;
	private Map<BoardCell, LinkedList<BoardCell>> adjacencyLists;
	private LinkedList<BoardCell> cellAdjList;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String BoardConfig;
	private String BoardRoomConfig;

	public Board(String boardConfig, String boardRoomConfig){
		super();
		cells = new BoardCell[ROWS][COLUMNS];
		rooms = new HashMap<Character, String>();
		BoardConfig = boardConfig;
		BoardRoomConfig = boardRoomConfig;
		adjacencyLists = new HashMap<BoardCell, LinkedList<BoardCell>>();
	}
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{

		FileReader reader = new FileReader(BoardConfig);
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

					if(s.charAt(0) == 'W'){
						cells[numRowsTemp][numColumnsTemp] = new WalkwayCell(numRowsTemp, numColumnsTemp, s.charAt(0));
					}

					else if (s != "W" && s != "X"){

						if (s.length() == 1){						
							cells[numRowsTemp][numColumnsTemp] = new RoomCell(numRowsTemp, numColumnsTemp, s.charAt(0), 'N');
						}
						else if (s.length() == 2){
							cells[numRowsTemp][numColumnsTemp] = new RoomCell(numRowsTemp, numColumnsTemp, s.charAt(0), s.charAt(1));

						}
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

	public void loadRoomConfig() throws FileNotFoundException{
		FileReader reader2 = new FileReader(BoardRoomConfig);
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

				if (i == 0 && j == 4){
					//System.out.println(cells[i][j].isWalkway());
					//System.out.println(cells[i][j].isDoorway());
					//System.out.println(cells[i][j].isRoom());
					//if((cells[i][j].isRoom()))
					//System.out.println(((RoomCell) cells[i][j]).getInitial());
				}

				if(cells[i][j].isWalkway()){

					if (i-1 >= 0 && (cells[i-1][j].isWalkway() || (cells[i-1][j].isDoorway() && (((RoomCell) cells[i-1][j]).getDoorDirection() == DoorDirection.DOWN)))){
						cellAdjList.add(cells[i-1][j]);
					}
					if (i+1 < numRows && (cells[i+1][j].isWalkway() || (cells[i+1][j].isDoorway() && (((RoomCell) cells[i+1][j]).getDoorDirection() == DoorDirection.UP)))){
						cellAdjList.add(cells[i+1][j]);
					}
					if (j-1 >= 0 && (cells[i][j-1].isWalkway() || (cells[i][j-1].isDoorway() && (((RoomCell) cells[i][j-1]).getDoorDirection() == DoorDirection.RIGHT)))){
						cellAdjList.add(cells[i][j-1]);
					}
					if (j+1 < numColumns && (cells[i][j+1].isWalkway() || (cells[i][j+1].isDoorway() && (((RoomCell) cells[i][j+1]).getDoorDirection() == DoorDirection.LEFT)))){
						//System.out.println("Trig");
						cellAdjList.add(cells[i][j+1]);
					}

				}

				else if(cells[i][j].isDoorway()){
					if (((RoomCell) cells[i][j]).getDoorDirection() == DoorDirection.DOWN){
						cellAdjList.add(cells[i+1][j]);
					}
					else if (((RoomCell) cells[i][j]).getDoorDirection() == DoorDirection.UP){
						cellAdjList.add(cells[i-1][j]);
					}
					else if (((RoomCell) cells[i][j]).getDoorDirection() == DoorDirection.RIGHT){
						cellAdjList.add(cells[i][j+1]);
					}
					else if (((RoomCell) cells[i][j]).getDoorDirection() == DoorDirection.LEFT){
						cellAdjList.add(cells[i][j-1]);
					}

				}

				adjacencyLists.put(cells[i][j], cellAdjList);



			}
		}
	}

	//DON'T MESS WITH THIS YET
	public void calcTargets(int i, int j, int numSteps){
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

	public LinkedList<BoardCell> getAdjList(int i, int j){
		return adjacencyLists.get(cells[i][j]);
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

}
