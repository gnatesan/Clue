package game;

import java.util.LinkedList;
import java.util.Set;
import java.util.*;

public class IntBoard {

	private BoardCell[][] cells;
	private final int XCELLMAX =4;
	private final int YCELLMAX = 4;
	private Map<BoardCell, LinkedList<BoardCell>> adjacencyList;
	public IntBoard() {
		super();
		cells = new BoardCell[XCELLMAX][YCELLMAX];
		for (int i =0; i < XCELLMAX; i++){
			for (int j = 0; j < YCELLMAX; j++){
				cells[i][j].setCell(i, j);
			}
			
		}
		// TODO Auto-generated constructor stub
	}
	public void calcAdjacencies(){
		
	}
	public void calcTargets(BoardCell cell, int i){
		
	}
	public Set<BoardCell> getTargets(){
		return null;
		
	}
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return null;
		
	}
	
	public BoardCell getCell(int x, int y){
		
		return cells[x][y];
		
	}
	

}
