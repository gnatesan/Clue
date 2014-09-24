package game;

import java.util.LinkedList;
import java.util.Set;

public class IntBoard {

	private BoardCell[][] cells;
	private final int XCELLMAX =4;
	private final int YCELLMAX = 4;
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
	public void calcTargets(){
		
	}
	public Set<BoardCell> getTargets(){
		return null;
		
	}
	public LinkedList<BoardCell> getAdjList(){
		return null;
		
	}
	
	public BoardCell getCell(int x, int y){
		
		return cells[x][y];
		
	}
	

}
