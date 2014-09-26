package game;

public class BoardCell {
	private int row;
	private int column;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}

	public void setCell(int x, int y){
		this.row = x;
		this.column =y;
		
	}
	public boolean isDoorway() {
		return true;
	}
	

}
