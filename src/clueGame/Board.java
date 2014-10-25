package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.JPanel;

import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel {
	private BoardCell[][] cells;
	Map<Character, String> rooms;
	List<BoardCell> centers;
	private final int ROWS = 22;
	private final int COLUMNS = 23;
	private int numRows = 0;
	private int numColumns = 0;
	private Map<BoardCell, LinkedList<BoardCell>> adjacencyLists;
	private LinkedList<BoardCell> cellAdjList;
	private LinkedList<BoardCell> visited;
	private Set<BoardCell> targets;
	private Map<BoardCell, LinkedList<BoardCell>> adjacentCells;
	private String BoardConfig;
	private String BoardRoomConfig;
	private ArrayList<Player> players;

	public Board(String boardConfig, String boardRoomConfig) {
		super();
		cells = new BoardCell[ROWS][COLUMNS];
		rooms = new HashMap<Character, String>();
		BoardConfig = boardConfig;
		BoardRoomConfig = boardRoomConfig;
		adjacencyLists = new HashMap<BoardCell, LinkedList<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new LinkedList<BoardCell>();
		centers = new LinkedList<BoardCell>();
	}

	public void loadBoardConfig() throws FileNotFoundException,
			BadConfigFormatException {

		FileReader reader = new FileReader(BoardConfig);
		Scanner in = new Scanner(reader);
		int numRowsTemp = 0;

		while (in.hasNextLine()) {
			String line = in.nextLine();
			List<String> temp = Arrays.asList(line.split(","));
			if (numColumns != 0 && numColumns != temp.size()) {
				throw new BadConfigFormatException();
			}
			int numColumnsTemp = 0;
			for (String s : temp) {

				if (!s.isEmpty()) { // Helps parse for unwanted spaces.
					if (!(rooms.containsKey(s.charAt(0))))
						throw new BadConfigFormatException();

					if (s.charAt(0) == 'W') {
						cells[numRowsTemp][numColumnsTemp] = new WalkwayCell(
								numRowsTemp, numColumnsTemp, s.charAt(0));
					}

					else if (s != "W" && s != "X") {

						if (s.length() == 1) {
							cells[numRowsTemp][numColumnsTemp] = new RoomCell(
									numRowsTemp, numColumnsTemp, s.charAt(0),
									'N');
						} else if (s.length() == 2) {
							cells[numRowsTemp][numColumnsTemp] = new RoomCell(
									numRowsTemp, numColumnsTemp, s.charAt(0),
									s.charAt(1));
							
							if (s.charAt(1) == 'N') {
								centers.add(cells[numRowsTemp][numColumnsTemp]);
							}

						}
					}

					numColumnsTemp++;

				}
			}
			if (numRowsTemp == 0) {
				setNumColumns(numColumnsTemp);
			}
			numRowsTemp++;
		}
		setNumRows(numRowsTemp);
	}

	public void loadRoomConfig() throws FileNotFoundException {
		FileReader reader2 = new FileReader(BoardRoomConfig);
		Scanner in2 = new Scanner(reader2);
		int count = 0;
		while (in2.hasNextLine()) {
			String line = in2.nextLine();
			List<String> temp = Arrays.asList(line.split(","));
			
			if (temp.size() != 2) {
				throw new BadConfigFormatException();
			}
			
			rooms.put(line.charAt(0), line.substring(3));
			count++;
		}

	}

	public List<BoardCell> getCenters() {
		return centers;
	}

	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {

				cellAdjList = new LinkedList<BoardCell>();
				if (cells[i][j].isWalkway()) {

					if (i - 1 >= 0
							&& (cells[i - 1][j].isWalkway() || (cells[i - 1][j]
									.isDoorway() && (((RoomCell) cells[i - 1][j])
									.getDoorDirection() == DoorDirection.DOWN)))) {
						cellAdjList.add(cells[i - 1][j]);
					}
					if (i + 1 < numRows
							&& (cells[i + 1][j].isWalkway() || (cells[i + 1][j]
									.isDoorway() && (((RoomCell) cells[i + 1][j])
									.getDoorDirection() == DoorDirection.UP)))) {
						cellAdjList.add(cells[i + 1][j]);
					}
					if (j - 1 >= 0
							&& (cells[i][j - 1].isWalkway() || (cells[i][j - 1]
									.isDoorway() && (((RoomCell) cells[i][j - 1])
									.getDoorDirection() == DoorDirection.RIGHT)))) {
						cellAdjList.add(cells[i][j - 1]);
					}
					if (j + 1 < numColumns
							&& (cells[i][j + 1].isWalkway() || (cells[i][j + 1]
									.isDoorway() && (((RoomCell) cells[i][j + 1])
									.getDoorDirection() == DoorDirection.LEFT)))) {
						cellAdjList.add(cells[i][j + 1]);
					}

				}

				else if (cells[i][j].isDoorway()) {
					if (i + 1 < numRows
							&& ((RoomCell) cells[i][j]).getDoorDirection() == DoorDirection.DOWN) {
						cellAdjList.add(cells[i + 1][j]);
					} else if (i - 1 >= 0
							&& ((RoomCell) cells[i][j]).getDoorDirection() == DoorDirection.UP) {
						cellAdjList.add(cells[i - 1][j]);
					} else if (j + 1 < numColumns
							&& ((RoomCell) cells[i][j]).getDoorDirection() == DoorDirection.RIGHT) {
						cellAdjList.add(cells[i][j + 1]);
					} else if (j - 1 >= 0
							&& ((RoomCell) cells[i][j]).getDoorDirection() == DoorDirection.LEFT) {
						cellAdjList.add(cells[i][j - 1]);
					}

				}

				adjacencyLists.put(cells[i][j], cellAdjList);

			}
		}

	}

	public void calcTargets(int i, int j, int numSteps) {

		targets = new HashSet<BoardCell>();
		visited = new LinkedList<BoardCell>();
		visited.add(cells[i][j]);
		adjacentCells = new HashMap<BoardCell, LinkedList<BoardCell>>();
		calcTargetsRecursive(cells[i][j], numSteps);
	}

	public void calcTargetsRecursive(BoardCell thisCell, int numSteps) {

		getAdjCell(thisCell);

		for (int i = 0; i < adjacentCells.get(thisCell).size(); i++) {

			visited.add(adjacentCells.get(thisCell).get(i));

			if (numSteps == 1 || adjacentCells.get(thisCell).get(i).isDoorway()) {
				targets.add(adjacentCells.get(thisCell).get(i));

			}

			else {
				calcTargetsRecursive(adjacentCells.get(thisCell).get(i),
						numSteps - 1);
			}

			visited.removeLast();

		}
	}

	public void getAdjCell(BoardCell thisCell) {

		cellAdjList = new LinkedList<BoardCell>();

		for (int i = 0; i < adjacencyLists.get(thisCell).size(); i++) {
			if (!visited.contains(adjacencyLists.get(thisCell).get(i))) {
				cellAdjList.add(adjacencyLists.get(thisCell).get(i));
			}
		}

		adjacentCells.put(thisCell, cellAdjList);

	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public LinkedList<BoardCell> getAdjList(int i, int j) {
		return adjacencyLists.get(cells[i][j]);
	}

	public Set<BoardCell> getTargets() {

		return targets;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int row = 0; row < this.getNumRows(); row++) {
			for (int col = 0; col < this.getNumColumns(); col++) {
				getCellAt(row, col).draw(g);
				getCellAt(row, col).drawName(g, this);
			}
		}

		for (Player p : players) {
			p.draw(g);
		}
	}

	private void setNumColumns(int numColumnsTemp) {
		numColumns = numColumnsTemp;
	}

	private void setNumRows(int numRowsTemp) {
		numRows = numRowsTemp;
	}

	public BoardCell getCellAt(int i, int j) {
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
