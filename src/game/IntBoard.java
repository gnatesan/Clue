package game;

import java.util.LinkedList;
import java.util.Set;
import java.util.*;

public class IntBoard {

	private BoardCell[][] cells;
	private final int XCELLMAX =4;
	private final int YCELLMAX = 4;
	private Map<BoardCell, LinkedList<BoardCell>> adjacencyList;
	private Set<BoardCell> returnThis;
	private LinkedList<BoardCell> defaultReturn;
	public IntBoard() {
		super();
		cells = new BoardCell[XCELLMAX][YCELLMAX];
		for (int i =0; i < XCELLMAX; i++){
			for (int j = 0; j < YCELLMAX; j++){
				cells[i][j] = new BoardCell(i,j);
			}
			
		}
		// TODO Auto-generated constructor stub
	}
	public void calcAdjacencies(){
		
	}
	public void calcTargets(BoardCell cell, int i){
		
	}
	public Set<BoardCell> getTargets(){
		return returnThis;
		
	}
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		return defaultReturn;
		
	}
	
	public BoardCell getCell(int x, int y){
		
		return cells[x][y];
		
	}
	

}
