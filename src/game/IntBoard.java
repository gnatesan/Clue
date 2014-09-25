package game;

import java.util.LinkedList;
import java.util.Set;
import java.util.*;

public class IntBoard {

	private final int ROWMAX =4;
	private final int COLMAX = 4;
	private BoardCell[][] cells = new BoardCell[ROWMAX][COLMAX];
	private Map<BoardCell, LinkedList<BoardCell>> adjacencyLists;
	//private Set<BoardCell> returnThis;
	private LinkedList<BoardCell> cellAdjList;
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
		//System.out.println("Function Called");
		for(int i=0; i < ROWMAX; i++){
			for(int j=0; j < COLMAX; j++){
				//System.out.println("Current cell: [row=" + cells[i][j].getRow() + ", column=" + cells[i][j].getColumn() + "]");
				cellAdjList = new LinkedList<BoardCell>();
				
				try{
					cellAdjList.add(cells[i+1][j]);
				}
				catch(ArrayIndexOutOfBoundsException e){
					//
				}
				
				try{
					cellAdjList.add(cells[i][j+1]);
				}
				catch(ArrayIndexOutOfBoundsException e){
					//
				}
				
				try{
					cellAdjList.add(cells[i-1][j]);
				}
				catch(ArrayIndexOutOfBoundsException e){
					//
				}
				
				try{
					cellAdjList.add(cells[i][j-1]);
				}
				catch(ArrayIndexOutOfBoundsException e){
					//
				}
				
				adjacencyLists.put(cells[i][j], cellAdjList);
				/*if( i==0 ){
					if ( j==0 ){
						System.out.println("Here, j=0, i=0");
						cellAdjList.add(cells[i+1][j]);
						cellAdjList.add(cells[i][j+1]);
						adjacencyLists.put(cells[i][j], cellAdjList);
					}
					else if(j == COLMAX - 1){
						System.out.println("Here, j=3, i=0");
						cellAdjList.add(cells[i+1][j]);
						cellAdjList.add(cells[i][j-1]);
						adjacencyLists.put(cells[i][j], cellAdjList);
					}
					else{
						System.out.println("Here, j!=0, j!=3 i=0");
						cellAdjList.add(cells[i+1][j]);
						cellAdjList.add(cells[i][j-1]);
						cellAdjList.add(cells[i][j+1]);
						adjacencyLists.put(cells[i][j], cellAdjList);
						
					}
				}
				else if( i==ROWMAX - 1 ){
					if ( j==0 ){
						cellAdjList.add(cells[i-1][j]);
						cellAdjList.add(cells[i][j+1]);
						adjacencyLists.put(cells[i][j], cellAdjList);
					}
					else if(j == COLMAX - 1){
						cellAdjList.add(cells[i-1][j]);
						cellAdjList.add(cells[i][j-1]);
						adjacencyLists.put(cells[i][j], cellAdjList);
					}
					else{
						cellAdjList.add(cells[i-1][j]);
						cellAdjList.add(cells[i][j-1]);
						cellAdjList.add(cells[i][j+1]);
						adjacencyLists.put(cells[i][j], cellAdjList);
						
					}
				}
				else if (j == 0){
					cellAdjList.add(cells[i+1][j]);
					cellAdjList.add(cells[i-1][j]);
					cellAdjList.add(cells[i][j+1]);
					adjacencyLists.put(cells[i][j], cellAdjList);
				}
				else if (j == COLMAX - 1){
					cellAdjList.add(cells[i+1][j]);
					cellAdjList.add(cells[i-1][j]);
					cellAdjList.add(cells[i][j-1]);
					adjacencyLists.put(cells[i][j], cellAdjList);
				}
				else {
					cellAdjList.add(cells[i+1][j]);
					cellAdjList.add(cells[i-1][j]);
					cellAdjList.add(cells[i][j-1]);
					cellAdjList.add(cells[i][j+1]);
					adjacencyLists.put(cells[i][j], cellAdjList);
				}*/
			}
		}

	}
	public void calcTargets(BoardCell cell, int i){
		

	}
	public Set<BoardCell> getTargets(){
		return null;

	}
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		//System.out.println(adjacencyLists.get(cell));
		return adjacencyLists.get(cell);

	}

	public BoardCell getCell(int x, int y){

		return cells[x][y];

	}


}
