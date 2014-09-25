package game;

import java.util.*;

public class IntBoard {
	private final int ROWMAX =4;
	private final int COLMAX = 4;
	private BoardCell[][] cells = new BoardCell[ROWMAX][COLMAX];
	private Map<BoardCell, LinkedList<BoardCell>> adjacencyLists;
	private LinkedList<BoardCell> cellAdjList;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	public IntBoard() {
		super();
		for (int i =0; i < ROWMAX; i++){
			for (int j = 0; j < COLMAX; j++){
				cells[i][j] = new BoardCell(i,j);
			}
		}
		adjacencyLists = new HashMap<BoardCell, LinkedList<BoardCell>>();
		calcAdjacencies();

	}
	public void calcAdjacencies(){
		for(int i=0; i < ROWMAX; i++){
			for(int j=0; j < COLMAX; j++){
				cellAdjList = new LinkedList<BoardCell>();
				if (i-1 >= 0){
					cellAdjList.add(cells[i-1][j]);
				}
				if (i+1 < ROWMAX){
					cellAdjList.add(cells[i+1][j]);
				}
				if (j-1 >= 0){
					cellAdjList.add(cells[i][j-1]);
				}
				if (j+1 < COLMAX){
					cellAdjList.add(cells[i][j+1]);
				}
				adjacencyLists.put(cells[i][j], cellAdjList);
			}
		}
	}
	public void calcTargets(BoardCell thisCell, int numSteps){


		for (int i = 0; i < adjacencyLists.get(thisCell).size(); i++){

			visited.add(adjacencyLists.get(thisCell).get(i));

			if (numSteps == 1){
				targets.add(adjacencyLists.get(thisCell).get(i));
			}

			else {
				calcTargets(adjacencyLists.get(thisCell).get(i), numSteps--);
			}


			

		}
	}


	public Set<BoardCell> getTargets(){
		return null;

	}
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return adjacencyLists.get(cell);

	}

	public BoardCell getCell(int x, int y){
		return cells[x][y];
	}
	public Set<BoardCell> findAllTargets(BoardCell cell, int i){

		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		calcTargets(cell, i);

		return targets;
	}
}
